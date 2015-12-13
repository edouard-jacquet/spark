package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.Document;
import spark.model.bean.Query;
import spark.model.bean.Session;

public class SessionDAO extends Dao<Session> {
	
	public Session getByKey(String key) {
		try {
			return (Session) entityManager.createQuery("SELECT s FROM Session s WHERE s.key = :key")
					.setParameter("key", key)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}
	
	public boolean isExistByKeyAndQuery(String key, Query query) {
		try {
			entityManager.createQuery("SELECT s FROM Session s WHERE s.key = :key AND :query MEMBER OF s.querys")
					.setParameter("key", key)
					.setParameter("query", query)
					.getSingleResult();
			
			return true;
		}
		catch (NoResultException noResultException) {
			return false;
		}
	}
	
	public boolean isExistByKeyAndDocument(String key, Document document) {
		try {
			entityManager.createQuery("SELECT s FROM Session s WHERE s.key = :key AND :document MEMBER OF s.documents")
					.setParameter("key", key)
					.setParameter("document", document)
					.getSingleResult();
			
			return true;
		}
		catch (NoResultException noResultException) {
			return false;
		}
	}

}

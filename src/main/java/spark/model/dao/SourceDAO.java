package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.Source;

public class SourceDAO extends Dao<Source> {
	
	public Source getByName(String name) {
		try {
			return (Source) entityManager.createQuery("SELECT s FROM Source s WHERE s.name = :name")
					.setParameter("name", name)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}
	
}

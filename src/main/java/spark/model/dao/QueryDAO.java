package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.Query;

public class QueryDAO extends Dao<Query> {
	
	public Query getByValue(String value) {
		try {
			return (Query) entityManager.createQuery("SELECT q FROM Query q WHERE q.value = :value")
					.setParameter("value", value)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}

}

package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.Configuration;

public class ConfigurationDAO extends Dao<Configuration> {
	
	public Configuration getByKey(String key) {
		try {
			return (Configuration) entityManager.createQuery("SELECT c FROM Configuration c WHERE c.key = :key")
					.setParameter("key", key)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}

}

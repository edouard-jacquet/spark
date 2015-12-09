package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.Author;

public class AuthorDAO extends Dao<Author> {
	
	public Author getByName(String name) {
		try {
			return (Author) entityManager.createQuery("SELECT a FROM Author a WHERE a.name = :name")
					.setParameter("name", name)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}

}

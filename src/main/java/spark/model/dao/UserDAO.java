package spark.model.dao;

import javax.persistence.NoResultException;

import spark.model.bean.User;

public class UserDAO extends Dao<User> {
	
	public User getByLoginAndPassword(String login, String password) {
		try {
			return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
					.setParameter("login", login)
					.setParameter("password", password)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}
	
	public boolean isExist(String login) {
		try {
			entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login")
					.setParameter("login", login)
					.getSingleResult();
			
			return true;
		}
		catch(NoResultException noResultException) {
			return false;
		}
	}
}

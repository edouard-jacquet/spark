package spark.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import spark.model.bean.User;

public class UserDAO {
	
	private EntityManager entityManager = Connection.getInstance().getConnection();
	
	public boolean add(User user) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			
			return true;
		}
		catch(Exception exception) {
			entityManager.getTransaction().begin();
			entityManager.getTransaction().rollback();
			
			throw exception;
		}
	}
	
	public User getById(long id) {
		try {
			return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}
	
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

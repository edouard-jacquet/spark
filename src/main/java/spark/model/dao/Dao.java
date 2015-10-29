package spark.model.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@SuppressWarnings("unchecked")
public abstract class Dao<T> implements IDao<T> {
	
	private final Class<T> persistentClass;
	protected EntityManager entityManager;
	
	
	public Dao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.entityManager = Connection.getInstance().getConnection();
	}
	
	public T create(T object) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(object);
			entityManager.getTransaction().commit();
			return object;
		}
		catch(Exception exception) {
			entityManager.getTransaction().begin();
			entityManager.getTransaction().rollback();
			return null;
		}
	}
	
	public T update(T object) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(object);
			entityManager.getTransaction().commit();
			return object;
		}
		catch(Exception exception) {
			entityManager.getTransaction().begin();
			entityManager.getTransaction().rollback();
			return null;
		}
	}
	
	public T delete(T object) {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(object);
			entityManager.getTransaction().commit();
			return null;
		}
		catch(Exception exception) {
			entityManager.getTransaction().begin();
			entityManager.getTransaction().rollback();
			return object;
		}
	}
	
	public List<T> getAll() {
		try {
			return (List<T>) entityManager.createQuery("SELECT t FROM "+ this.persistentClass.getSimpleName() +" t")
					.getResultList();
		}
		catch (NoResultException exception) {
			return null;
		}
	}
	
	public T getById(Long id) {
		try {
			return (T) entityManager.createQuery("SELECT t FROM "+ this.persistentClass.getSimpleName() +" t WHERE t.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch (NoResultException | NonUniqueResultException exception) {
			return null;
		}
	}

}

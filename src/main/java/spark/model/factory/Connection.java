package spark.model.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class Connection {

	private static final Connection CONNECTION = new Connection();
	private EntityManager entityManager;
	private FullTextEntityManager fullTextEntityManager;
	
	private Connection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");
		entityManager = entityManagerFactory.createEntityManager();
		fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
	}
	
	public static Connection getInstance() {
		return CONNECTION;
	}
	
	public EntityManager getConnection() {
		return entityManager;
	}
	
	public FullTextEntityManager getFullTextConnection() {
		return fullTextEntityManager;
	}
	
}

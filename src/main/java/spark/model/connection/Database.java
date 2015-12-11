package spark.model.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class Database {

	private static final Database DATABASE = new Database();
	private EntityManager entityManager;
	private FullTextEntityManager fullTextEntityManager;
	
	private Database() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");
		entityManager = entityManagerFactory.createEntityManager();
		fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
	}
	
	public static Database getInstance() {
		return DATABASE;
	}
	
	public EntityManager getConnection() {
		return entityManager;
	}
	
	public FullTextEntityManager getFullTextConnection() {
		return fullTextEntityManager;
	}
	
}

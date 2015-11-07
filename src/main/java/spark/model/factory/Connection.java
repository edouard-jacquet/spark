package spark.model.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {

	private static final Connection CONNECTION = new Connection();
	private EntityManager entityManager;
	
	private Connection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static Connection getInstance() {
		return CONNECTION;
	}
	
	public EntityManager getConnection() {
		return entityManager;
	}
	
}

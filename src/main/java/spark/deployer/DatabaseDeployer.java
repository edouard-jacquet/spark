package spark.deployer;

import org.jboss.logging.Logger;

import spark.model.bean.Source;
import spark.model.bean.User;
import spark.model.dao.SourceDAO;
import spark.model.dao.UserDAO;

public class DatabaseDeployer extends Deployer {
	
	private static final DatabaseDeployer DATABASEDEPLOYER = new DatabaseDeployer();
	
	
	private DatabaseDeployer() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public static DatabaseDeployer getInstance() {
		return DATABASEDEPLOYER;
	}
	
	public void execute() {
		System.out.println("----------");
		System.out.println("--------------- DATABASE DEPLOY START ----------");
		System.out.println("----------");
		
		UserDAO userDAO = new UserDAO();
		if(userDAO.getAll().size() == 0) {
			String[][] users = {
				{"admin", "admin", "####"}
			};
			
			for(String[] entry : users) {
				User user = new User();
				user.setLogin(entry[0]);
				user.setPassword(entry[1]);
				user.setKey(entry[2]);
				userDAO.create(user);
			}
			
			logger.info(users.length +" users have been added.");
		}

		SourceDAO sourceDAO = new SourceDAO();
		if(sourceDAO.getAll().size() == 0) {
			String[][] sources = {
				{"personal", ""}
			};
			
			for(String[] entry : sources) {
				Source source = new Source();
				source.setName(entry[0]);
				source.setLocation(entry[1]);
				sourceDAO.create(source);
			}
			
			logger.info(sources.length +" sources have been added.");
		}
		
		System.out.println("----------");
		System.out.println("--------------- DATABASE DEPLOY END ----------");
		System.out.println("----------");
	}

}

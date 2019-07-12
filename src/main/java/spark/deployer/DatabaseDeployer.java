package spark.deployer;

import org.jboss.logging.Logger;

import spark.model.bean.Configuration;
import spark.model.bean.Source;
import spark.model.bean.User;
import spark.model.dao.ConfigurationDAO;
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
				{"personal", "", "1"},
				{"aclweb", "", "1"},
				{"arxiv", "", "1"}
			};
			
			for(String[] entry : sources) {
				Source source = new Source();
				source.setName(entry[0]);
				source.setLocation(entry[1]);
				source.setActive(Integer.parseInt(entry[2]));
				sourceDAO.create(source);
			}
			
			logger.info(sources.length +" sources have been added.");
		}
		
		ConfigurationDAO configurationDAO = new ConfigurationDAO();
		if(configurationDAO.getAll().size() == 0) {
			String[][] configurations = {
				{"schedule", "{active:true, trigger:'0 0 12 ? * 1L', sources: []}"}
			};
			
			for(String[] entry : configurations) {
				Configuration configuration = new Configuration();
				configuration.setKey(entry[0]);
				configuration.setValue(entry[1]);
				configurationDAO.create(configuration);
			}
			
			logger.info(configurations.length +" configurations have been added.");
		}
		
		System.out.println("----------");
		System.out.println("--------------- DATABASE DEPLOY END ----------");
		System.out.println("----------");
	}

}

package spark.deployer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jboss.logging.Logger;
import org.quartz.SchedulerException;

import spark.model.manager.scheduler.*;

import spark.Constant;

public class ApplicationDeployer extends Deployer {
	
	private static final ApplicationDeployer APPLICATIONDEPLOYER = new ApplicationDeployer();
	
	
	private ApplicationDeployer() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public static ApplicationDeployer getInstance() {
		return APPLICATIONDEPLOYER;
	}
	
	public void execute() {
		System.out.println("----------");
		System.out.println("--------------- APPLICATION DEPLOY START ----------");
		System.out.println("----------");
		
		createDirectory(Constant.STORAGE_ROOT_DIRECTORY, Constant.STORAGE_ROOT_FOLDER);
		createDirectory(Constant.STORAGE_TEMPORARY_DIRECTORY, Constant.STORAGE_TEMPORARY_FOLDER);
		createDirectory(Constant.STORAGE_INDEX_DIRECTORY, Constant.STORAGE_INDEX_FOLDER);
		createDirectory(Constant.STORAGE_DOCUMENT_DIRECTORY, Constant.STORAGE_DOCUMENT_FOLDER);
		/*try {
			ManageScheduler.initScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		System.out.println("----------");
		System.out.println("--------------- APPLICATION DEPLOY END ----------");
		System.out.println("----------");
	}
	
	private void createDirectory(String name, String path) {
		if(!Files.exists(Paths.get(path))) {
			try {
				Files.createDirectory(Paths.get(path));
				logger.info(name +" directory has been created.");
			}
			catch(IOException exception) {
				logger.error(name +" directory has not been created.");
			}
		}
	}

}

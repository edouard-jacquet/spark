package spark.model.manager.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.CronTrigger;
import static org.quartz.CronScheduleBuilder.*;

import spark.model.dao.ConfigurationDAO;
import spark.model.manager.scheduler.job.UpdateLibraryJob;

public class ManageScheduler {
	
	public static void initScheduler() throws SchedulerException, JSONException{
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();
		sched.start();
		JobDetail job1 = newJob(UpdateLibraryJob.class).withIdentity("updateJob", "group1").build(); 
		
		ConfigurationDAO config_dao = new ConfigurationDAO();
		JSONObject config = new JSONObject(config_dao.getByKey("schedule").getValue());
		String configTrigger = config.getString("trigger");
		
		CronTrigger trigger = newTrigger()
				.withIdentity("myTrigger", "group1")
				.withSchedule(cronSchedule(configTrigger))
				.forJob(job1)
				.build();
		
		System.out.println("--------------- INITIATION DU SCHEDULER ----------");
	}
	
}

package spark.model.manager.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import spark.model.manager.scheduler.job.UpdateLibraryJob;

public class ManageScheduler {
	
	public static void initScheduler() throws SchedulerException{
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();
		sched.start();
		
		JobDetail job1 = newJob(UpdateLibraryJob.class).withIdentity("updateJob", "group1").build(); 
		Trigger trigger = newTrigger()
				.withIdentity("myTrigger", "group1")
				.startNow()
				.withSchedule(simpleSchedule()
				.withIntervalInSeconds(40)
				.repeatForever())
				.build(); 
		
		sched.scheduleJob(job1, trigger);
		
		System.out.println("--------------- INITIATION DU SCHEDULER ----------");
	}
	
}

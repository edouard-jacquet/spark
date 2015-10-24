package spark.controller.service.time;

import java.util.Date;

public class TimeStamp {
	
	public static int get() {
		return (int) (new Date().getTime() / 1000);
	}

}

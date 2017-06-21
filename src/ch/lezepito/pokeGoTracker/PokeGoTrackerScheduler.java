package ch.lezepito.pokeGoTracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.lezepito.pokeGoTracker.log.LogConfig;
import ch.lezepito.pokeGoTracker.notification.AlertEmailNotifier;
import ch.lezepito.pokeGoTracker.notification.EmailNotifier;
import ch.lezepito.pokeGoTracker.notification.Notifier;
import ch.lezepito.pokeGoTracker.util.Constants;

public class PokeGoTrackerScheduler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(PokeGoTrackerScheduler.class.getName());

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void main(String[] args) {
		
		LogConfig.initLogConfig();
		
		int schedulerPeriod = Constants.DEFAULT_SCHEDULER_PERIOD;
		if (args.length > 0 && args[0] != null) {
			schedulerPeriod = Integer.parseInt(args[0]);
		}
		PokeGoTrackerScheduler pokeGoTrackerRunnable = new PokeGoTrackerScheduler();
		
		Calendar calendar = GregorianCalendar.getInstance();
		int currentMinutes = calendar.get(Calendar.MINUTE);
		int currentSeconds = calendar.get(Calendar.SECOND);
		int remainingMinutesTillNextFullSchedulerInterval = schedulerPeriod - (currentMinutes % schedulerPeriod);
		int remainingSecondsTillNextFullMinute = Constants.SECS_IN_MIN - currentSeconds;
		
		long secsTillNextInterval =  (remainingMinutesTillNextFullSchedulerInterval -1) * Constants.SECS_IN_MIN + remainingSecondsTillNextFullMinute;
		
		calendar.add(Calendar.MINUTE, remainingMinutesTillNextFullSchedulerInterval -1);
		calendar.add(Calendar.SECOND, remainingSecondsTillNextFullMinute);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		LOGGER.info("Running the scan once right now and then scheduling at " + dateFormat.format(calendar.getTime()) + " and then reccuring every '" + schedulerPeriod + "' minutes.");
		
		//run once
		pokeGoTrackerRunnable.run();

		//schedule
		scheduler.scheduleAtFixedRate(pokeGoTrackerRunnable, secsTillNextInterval, schedulerPeriod * 60 , TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		try {
			LOGGER.info("####################################################");
			LOGGER.info("Starting one run!");
			LOGGER.info("####################################################");

			PokeGoTrackerMain main = new PokeGoTrackerMain();
			main.init();

			ArrayList<Notifier> infoNotifiers = new ArrayList<Notifier>();
			infoNotifiers.add(new EmailNotifier());
			
			ArrayList<Notifier> alertNotifiers = new ArrayList<Notifier>();
			alertNotifiers.add(new AlertEmailNotifier());

			main.start(1, infoNotifiers, alertNotifiers);

			LOGGER.info("####################################################");
			LOGGER.info("Run finshed!");
			LOGGER.info("####################################################");
		} catch (Throwable t) {
			LOGGER.log(Level.SEVERE, "Something went horribly wrong!!!! ", t);
		}
	}
}

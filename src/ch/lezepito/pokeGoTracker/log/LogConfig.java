package ch.lezepito.pokeGoTracker.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogConfig {

	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger LOGGER = Logger.getLogger(LogConfig.class.getName());

	public static void initLogConfig() {
		try {
			logManager.readConfiguration(new FileInputStream("./conf/log.properties"));
		} catch (IOException exception) {
			LOGGER.log(Level.SEVERE, "Error in loading configuration",	exception);
		}
	}
}

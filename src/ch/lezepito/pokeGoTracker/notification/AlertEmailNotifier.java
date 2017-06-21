package ch.lezepito.pokeGoTracker.notification;

import ch.lezepito.pokeGoTracker.util.PrivateConstants;

public class AlertEmailNotifier extends EmailNotifier {
	public AlertEmailNotifier() {
		recepient = PrivateConstants.ALERT_TO_EMAIL;
		sendEmptyMail = false;
	}
}

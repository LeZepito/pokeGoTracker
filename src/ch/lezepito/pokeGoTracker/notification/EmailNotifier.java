package ch.lezepito.pokeGoTracker.notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ch.lezepito.pokeGoTracker.Pokemon;
import ch.lezepito.pokeGoTracker.util.Constants;
import ch.lezepito.pokeGoTracker.util.PrivateConstants;
import ch.lezepito.pokeGoTracker.util.Utils;

public class EmailNotifier implements Notifier {
	
	private static final Logger LOGGER = Logger.getLogger(EmailNotifier.class.getName());
	protected String recepient = PrivateConstants.INFO_TO_EMAIL;
	protected boolean sendEmptyMail = true;
	
	public EmailNotifier() {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", PrivateConstants.SMTP_HOST);
	}
	
	private String composeText(List<Pokemon> filteredPokemons) {
		StringBuilder resultString = new StringBuilder();
		
		resultString.append("<table>");
		resultString.append("<tr><th>Name</th><th>IV</th><th>CP</th><th>Moves</th><th>DAT</th><th>DFH</th></tr>");
		for(Pokemon onePokemon : filteredPokemons) {
			resultString.append(getAsHTMLTableRow(onePokemon));
		}
		resultString.append("</table>");
		resultString.append("Filtered amount: " + filteredPokemons.size());
		return resultString.toString();
	}
	
	protected String getSubject() {
		String subject = Constants.EMAIL_SUBJECT;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date now = new Date();
		String formattedDate = dateFormat.format(now);
		subject += "(" + formattedDate + ")";
		return subject;
	}
	private boolean sendEmail(String text) {
		Properties properties = System.getProperties();
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PrivateConstants.FROM_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			
			String subject = getSubject();
			
			message.setSubject(subject);
			message.setContent(text, "text/html; charset=utf-8");

			// Send message
			Transport.send(message);
			LOGGER.info("message sent successfully....");

		} catch (MessagingException mex) {
			LOGGER.log(Level.SEVERE, "Could not send Email", mex);
		}
		return true;
	}

	@Override
	public boolean notify(List<Pokemon> filteredPokemons) {
		// Send email

		String text = "<b>NOTHING RIGHT NOW</b>";
		if (filteredPokemons.size() > 0) {
			text = composeText(filteredPokemons);
		}
		else if (!sendEmptyMail) {
			//abort if we don't want to send empty mails
			return true;
		}
		
		sendEmail(text);

		return true;
	}
	
	public String getAsHTMLTableRow(Pokemon onePokemon) {
		StringBuilder resultString = new StringBuilder();
		resultString.append("<tr>");
		resultString.append("<td>" + onePokemon.getName() + "(" + onePokemon.getId() + ")</td>");
		resultString.append("<td>" + onePokemon.getIv() + "</td>");
		resultString.append("<td>" + onePokemon.getCp() + "</td>");
		resultString.append("<td nowrap>" + onePokemon.getMove1AsString() + "/" + onePokemon.getMove2AsString() + "</td>");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		long disappearTimeInMilis = onePokemon.getDisappearTime() * 1000L;
		String formattedDisapearTime = dateFormat.format(new Date(disappearTimeInMilis));
		resultString.append("<td nowrap>" + formattedDisapearTime + "</td>");
		double distanceFromHome = Utils.calculateDistanceFromHome(onePokemon.getLatitude(), onePokemon.getLongitude());
		resultString.append("<td><a href=\"" + "https://www.google.com/maps/?daddr=" + onePokemon.getLatitude() + "," + onePokemon.getLongitude() + "\">" + distanceFromHome + "</a></td>");
		resultString.append("<tr>");
		return resultString.toString();
	}
	
	public static void main(String[] args) {
		String testText = 
				"<table>"
				+ "<tr><th>Firstname</th><th>Lastname</th><th>Age</th></tr>"
				+ "<tr><td>Jill</td><td>Smith</td><td>50</td></tr>"
				+ "<tr><td>Eve</td><td>Jackson</td><td>94</td></tr>"
				+ "</table>";
		EmailNotifier notifier = new EmailNotifier();
		notifier.sendEmail(testText);
	}

}

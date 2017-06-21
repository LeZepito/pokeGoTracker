package ch.lezepito.pokeGoTracker;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ch.lezepito.pokeGoTracker.api.PmApi;
import ch.lezepito.pokeGoTracker.config.Configuration;
import ch.lezepito.pokeGoTracker.config.PokemonConfig;
import ch.lezepito.pokeGoTracker.notification.AlertEmailNotifier;
import ch.lezepito.pokeGoTracker.notification.ConsoleNotifier;
import ch.lezepito.pokeGoTracker.notification.EmailNotifier;
import ch.lezepito.pokeGoTracker.notification.Notifier;
import ch.lezepito.pokeGoTracker.util.Constants;
import ch.lezepito.pokeGoTracker.util.PrivateConstants;

public class PokeGoTrackerMain {
	
	private static final Logger LOGGER = Logger.getLogger(PokeGoTrackerMain.class.getName());
	private HashMap<Integer, PokemonConfig> pokemonConfig;
	private boolean[] unownConfig;
	private String ignoredIndexes;
	PmApi pmApi;
	String overallMaxEid = "0";
	
	private void oneRun(ArrayList<Notifier> infoNotifiers, ArrayList<Notifier> alertNotifiers) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		String responseString = pmApi.executeRequest(overallMaxEid, ignoredIndexes);
		
		List<Pokemon> pokemons = Pokemon.parsePokemonList(responseString);
		LOGGER.info("Received amount: " + pokemons.size());
		int overallMaxEidInt = Integer.parseInt(overallMaxEid);
		for(Pokemon onePokemon : pokemons) {
			if (onePokemon.getEid() > overallMaxEidInt) {
				overallMaxEid = String.valueOf(onePokemon.getEid());
			}
		}
		
		List<Pokemon> filteredPokemons = Pokemon.filterListForGeneral(pokemons, pokemonConfig);
		Pokemon.sort(filteredPokemons);
		LOGGER.info("Filtered amount for info: " + filteredPokemons.size());
		for(Notifier oneNotifier : infoNotifiers) {
			oneNotifier.notify(filteredPokemons);
		}
		
		filteredPokemons = Pokemon.filterListForAlert(pokemons, pokemonConfig, unownConfig);
		Pokemon.sort(filteredPokemons);
		LOGGER.info("Filtered amount for alert: " + filteredPokemons.size());
		for(Notifier oneNotifier : alertNotifiers) {
			oneNotifier.notify(filteredPokemons);
		}
		
		//System.out.println("is now: " + overallMaxEid);
	}
	
	public void start(int repetitions, ArrayList<Notifier> infoNotifiers, ArrayList<Notifier> alertNotifiers) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		for (int i = 0; i < repetitions; i ++) {
			oneRun(infoNotifiers, alertNotifiers);
			if (i + 1 != repetitions) {
				Thread.sleep(Constants.SLEEP_TIME_SEC * 1000);
			}
		}
		//System.out.println(paramString);
	}
	
	public void init() {
		
		System.setProperty("https.proxyHost", PrivateConstants.PROXY);
		System.setProperty("https.proxyPort", PrivateConstants.PROXY_PORT);
		System.setProperty("http.agent", "");
		//System.setProperty("javax.net.debug", "all");
		
		ignoredIndexes = Configuration.getIngoredListAsString();
		pokemonConfig = Configuration.fetchPokemonConfig();
		unownConfig = Configuration.getUnownConfig();
		
		pmApi = new PmApi();
	}
	

	@SuppressWarnings("unused")
	private void dumpConfig() {
		System.out.println("Dump Pokemon Config:");
		System.out.println("--------------------");
		Set<Integer> keySet = pokemonConfig.keySet();
		for (Integer oneKey : keySet) {
			int id = pokemonConfig.get(oneKey).getId();
			int threshold = pokemonConfig.get(oneKey).getGeneralThreshold();
			int cpThreshold = pokemonConfig.get(oneKey).getGeneralCpThreshold();
			int alertThreshold = pokemonConfig.get(oneKey).getUrgentThreshold();
			System.out.println(Constants.POKEMON_NAMES[id] + "(" + id + "): " + threshold + ", " + cpThreshold + ", " + alertThreshold);
		}	
		
		System.out.println();
		System.out.println("Dump IgnoredList");
		System.out.println("----------------");
		System.out.println(ignoredIndexes);
	}
	
	public static void main (String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		int repetitions = Constants.MAX_RUNS;
		if (args.length > 0 && args[0] != null) {
			try {
				repetitions = Integer.parseInt(args[0]);
			}
			catch(Throwable t) {
				System.out.println("Couldn't parse input argument. Defaulting to " + Constants.MAX_RUNS);
			}
		}
		
		boolean sendEmail = false;
		if (args.length > 1 && args[1] != null) {
			if ("email".equalsIgnoreCase(args[1])) {
				sendEmail = true;
			}
		}
		
		ArrayList<Notifier> infoNotifiers = new ArrayList<Notifier>();
		infoNotifiers.add(new ConsoleNotifier());
		if (sendEmail) {
			infoNotifiers.add(new EmailNotifier());
		}
		ArrayList<Notifier> alertNotifiers = new ArrayList<Notifier>();
		alertNotifiers.add(new ConsoleNotifier());
		if (sendEmail) {
			alertNotifiers.add(new AlertEmailNotifier());
		}
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
		
		PokeGoTrackerMain main = new PokeGoTrackerMain();
		main.init();
		//main.dumpConfig();
		main.start(repetitions, infoNotifiers, alertNotifiers);
	}
}

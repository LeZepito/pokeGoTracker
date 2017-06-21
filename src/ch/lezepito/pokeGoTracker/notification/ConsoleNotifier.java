package ch.lezepito.pokeGoTracker.notification;

import java.util.List;
import java.util.logging.Logger;

import ch.lezepito.pokeGoTracker.Pokemon;

public class ConsoleNotifier implements Notifier {

	private static final Logger LOGGER = Logger.getLogger(ConsoleNotifier.class.getName());
	
	@Override
	public boolean notify(List<Pokemon> filteredPokemons) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dumping found Pokemon: \n");
		for(Pokemon onePokemon : filteredPokemons) {
			stringBuilder.append(onePokemon.toString() + "\n");
		}
		LOGGER.info(stringBuilder.toString());
		return true;
	}

}

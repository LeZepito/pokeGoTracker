package ch.lezepito.pokeGoTracker.notification;

import java.util.List;

import ch.lezepito.pokeGoTracker.Pokemon;

public interface Notifier {
	public boolean notify(List<Pokemon> filteredPokemons);
}

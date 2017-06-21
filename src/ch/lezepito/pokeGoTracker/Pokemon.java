package ch.lezepito.pokeGoTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.lezepito.pokeGoTracker.config.PokemonConfig;
import ch.lezepito.pokeGoTracker.util.Constants;
import ch.lezepito.pokeGoTracker.util.Utils;

public class Pokemon {
	
	private static final Logger LOGGER = Logger.getLogger(Pokemon.class.getName());
	
	private int move1;
	private int move2;
	private double longitude;
	private double latitude;
	private int eid;
	private int id;
	private int iv;
	private int cp;
	private int disappearTime;
	private String name;
	
	public int getMove1() {
		return move1;
	}
	public void setMove1(int move1) {
		this.move1 = move1;
	}
	public int getMove2() {
		return move2;
	}
	public void setMove2(int move2) {
		this.move2 = move2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIv() {
		return iv;
	}
	public void setIv(int iv) {
		this.iv = iv;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDisappearTime() {
		return disappearTime;
	}
	public void setDisappearTime(int disappearTime) {
		this.disappearTime = disappearTime;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	private Pokemon(int id, int iv, int cp, int disappearTime, int eid, double latitude, double longitude, int move1, int move2) {
		this.id = id;
		this.iv = iv;
		this.cp = cp;
		this.disappearTime = disappearTime;
		this.move1 = move1;
		this.move2 = move2;
		this.longitude = longitude;
		this.latitude = latitude;
		this.eid = eid;
		
		this.name = Constants.POKEMON_NAMES[this.id];
		//some magic (reverse engineered from original script) to detect large magikarps
		if (this.id == 129 && this.move1 > 0 && this.move1 == this.move2) {
			this.name = "XL" + this.name;
		}
		//some magic (reverse engineered from original script) to detect the form of the unown
		if (this.id == 201 && this.move1 > 0 && this.move1 == this.move2) {
			this.name += "(" + Constants.UNOWN_FORM[this.move1] + ")";
		}
	}
	
	private static Pokemon parseFromJSON(JSONObject pokemonJSON) {
		//{"disappear_time":1496511062,"eid":841695529,"latitude":47.543983,"longitude":7.635577,"pokemon_id":129,"iv":15,"move1":231,"move2":133,"cp":71}
		int disappear_time = (Integer)pokemonJSON.get("disappear_time");
		int eid = (Integer)pokemonJSON.get("eid");
		double latitude = (Double)pokemonJSON.get("latitude");
		double longitude = (Double)pokemonJSON.get("longitude");
		int pokemon_id = (Integer)pokemonJSON.get("pokemon_id");
		int iv = -1;
		if (pokemonJSON.has("iv")) {
			Integer originalIV = (Integer)pokemonJSON.get("iv");
			iv = Math.round(originalIV*100/45);
		}
		int move1 = -1;
		if (pokemonJSON.has("move1")) {
			move1 = (Integer)pokemonJSON.get("move1");
		}
		int move2 = -1;
		if (pokemonJSON.has("move2")) {
			move2 = (Integer)pokemonJSON.get("move2");
		}
		int cp = -1;
		if (pokemonJSON.has("cp")) {
			cp = (Integer)pokemonJSON.get("cp");
		}
		return new Pokemon(pokemon_id, iv, cp, disappear_time, eid, latitude, longitude, move1, move2);
	}
	
	public static List<Pokemon> parsePokemonList(String jsonString) {
		List<Pokemon> result = new ArrayList<>();
		JSONObject json = new JSONObject(jsonString);
		JSONArray pokemons = (JSONArray)json.get("pokemons");
		for (int i = 0; i < pokemons.length(); i++) {
			JSONObject onePokemonJSON = (JSONObject)pokemons.get(i);
			Pokemon onePokemon = Pokemon.parseFromJSON(onePokemonJSON);
			result.add(onePokemon);
		}
		return result;
	}
	
	public String getMove1AsString() {
		//Magicarp and Unown don't have Moves
		if (getId() == 129 || getId() == 201) {
			return "-";
		}
		return getMoveAsString(getMove1());
	}
	public String getMove2AsString() {
		//Magicarp and Unown don't have Moves
		if (getId() == 129 || getId() == 201) {
			return "-";
		}
		return getMoveAsString(getMove2());
	}
	private String getMoveAsString(int moveId){
		if(moveId > 137){
			return Constants.POKEMON_MOVES[moveId-62];
		}

		if (moveId >= 0) {
			return Constants.POKEMON_MOVES[moveId];
		}
		else {
			return "-";
		}
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("name: " + getName() + "(" + getId() + "), ");
		builder.append("iv: " + getIv());
		builder.append(", ");
		builder.append("cp: " + getCp());
		builder.append(", ");
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		long disappearTimeInMilis = getDisappearTime() * 1000L;
		String formattedDisapearTime = dateFormat.format(new Date(disappearTimeInMilis));
		builder.append("disappearTime: " + formattedDisapearTime);
		builder.append(", ");
		builder.append("GoogleURL: https://www.google.com/maps/?daddr="+getLatitude()+","+getLongitude());
		builder.append(", ");
		double distanceFromHome = Utils.calculateDistanceFromHome(getLatitude(), getLongitude());
		builder.append("DFH: " + distanceFromHome);
		if (getMove1() > 0) {
			builder.append(", moves: (");
			builder.append(getMove1AsString());
			builder.append("/");
			builder.append(getMove2AsString());
			builder.append(")");
		}
		/*builder.append(", ");
		builder.append("longitude: " + longitude);
		builder.append(", ");
		builder.append("latitude: " + latitude);
		builder.append(", ");
		builder.append("eid: " + eid);*/
		return builder.toString();
	}
	
	public static abstract class Filter {
		protected Map<Integer, PokemonConfig> pokemonConfig;
		public Filter(Map<Integer, PokemonConfig> pokemonConfig) {
			this.pokemonConfig = pokemonConfig;
		}
		public boolean shouldAdd(Pokemon onePokemon) {
			if (getThresholdValue(onePokemon) != -1 && getComparisonValue(onePokemon) >= getThresholdValue(onePokemon)) {
				return true;
			}
			else if (getThresholdValue(onePokemon) == 0) {
				//sometimes they don't have a value set. For the ones that we have threshold 0, we add them anyway
				return true;
			}
			return false;
		}
		protected PokemonConfig getConfig(Pokemon onePokemon) {
			Integer index = new Integer(onePokemon.getId());
			if (pokemonConfig.get(index) == null) {
				throw new RuntimeException("Configuration missing for id " + index + " with name " + onePokemon.getName());
			}
			return pokemonConfig.get(index);
		}
		public abstract int getComparisonValue(Pokemon onePokemon);
		public abstract int getThresholdValue(Pokemon onePokemon);
	}
	public static class IVFilter extends Filter {
		public IVFilter(Map<Integer, PokemonConfig> pokemonConfig) {
			super(pokemonConfig);
		}
		@Override
		public int getComparisonValue(Pokemon onePokemon) {
			return onePokemon.getIv();
		}
		@Override
		public int getThresholdValue(Pokemon onePokemon) {
			return getConfig(onePokemon).getGeneralThreshold();
		}
	}
	public static class UrgentIVFilter extends IVFilter {
		public UrgentIVFilter(Map<Integer, PokemonConfig> pokemonConfig) {
			super(pokemonConfig);
		}
		@Override
		public int getThresholdValue(Pokemon onePokemon) {
			return getConfig(onePokemon).getUrgentThreshold();
		}
	}
	public static class CPFilter extends Filter {
		public CPFilter(Map<Integer, PokemonConfig> pokemonConfig) {
			super(pokemonConfig);
		}
		@Override
		public int getComparisonValue(Pokemon onePokemon) {
			return onePokemon.getCp();
		}
		@Override
		public int getThresholdValue(Pokemon onePokemon) {
			return getConfig(onePokemon).getGeneralCpThreshold();
		}
	}
	
	private static List<Pokemon> filterListWithCriteria(List<Pokemon> inputPokemons, Filter filter) {
		List<Pokemon> filteredPokemons = new ArrayList<Pokemon>();
		for (Pokemon onePokemon : inputPokemons) {
			if (filter.shouldAdd(onePokemon)) {
				LOGGER.fine("Adding " + onePokemon.toString());
				filteredPokemons.add(onePokemon);
			}
			else {
				LOGGER.fine("Skipping " + onePokemon.toString());
			}
		}
		return filteredPokemons;
	}
	
	public static List<Pokemon> filterListForGeneral(List<Pokemon> pokemons, Map<Integer, PokemonConfig> pokemonConfig) {
		List<Pokemon> filteredPokemons = new ArrayList<Pokemon>();
		
		Filter ivFilter = new IVFilter(pokemonConfig);
		filteredPokemons = filterListWithCriteria(pokemons, ivFilter);
		LOGGER.info("Filtered amount for info based on IV: " + filteredPokemons.size());
		
		Filter cpFilter = new CPFilter(pokemonConfig);
		filteredPokemons = filterListWithCriteria(filteredPokemons, cpFilter);
		LOGGER.info("Filtered amount for info based on CP: " + filteredPokemons.size());
		
		return filteredPokemons;
	}
	
	private static List<Pokemon> applyUnownFilter(List<Pokemon> pokemons, boolean[] unownConfig) {
		return pokemons.stream()
				.filter(p -> p.getId() != 201 || (p.getId() == 201 && unownConfig[p.getMove1()]))
				.collect(Collectors.toList());
	}
	
	public static List<Pokemon> filterListForAlert(List<Pokemon> pokemons, HashMap<Integer, PokemonConfig> pokemonConfig, boolean[] unownConfig) {
		Filter urgentIvFilter = new UrgentIVFilter(pokemonConfig);
		List<Pokemon> filteredPokemons = filterListWithCriteria(pokemons, urgentIvFilter);
		return applyUnownFilter(filteredPokemons, unownConfig);
	}
	private static class PokemonComparator implements Comparator<Pokemon> {
		@Override
		public int compare(Pokemon one, Pokemon other) {
			return one.getDisappearTime() - other.getDisappearTime();
		}
		
	}
	public static void sort(List<Pokemon> pokemons) {
		Comparator<Pokemon> pokemonComparater = new PokemonComparator();
		Collections.sort(pokemons, pokemonComparater);
	}
}

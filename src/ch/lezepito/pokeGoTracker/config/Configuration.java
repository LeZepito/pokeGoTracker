package ch.lezepito.pokeGoTracker.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Configuration {
	
	private static boolean configurationValidated = false;
	
	//TODO: move this to a file
	private static int[][] CONFIGURATION = new int[][]{
		/*Bulbasaur*/    {1, 100, 200, -1},
		/*Ivysaur*/    {2, 100, 200, -1},
		/*Venusaur*/    {3, 100, 200, -1},
		/*Charmander*/    {4, 100, 500, -1},
		/*Charmeleon*/    {5, 100, 200, -1},
		/*Charizard*/    {6, 100, 200, -1},
		/*Squirtle*/    {7, 100, 200, -1},
		/*Wartortle*/    {8, 100, 200, -1},
		/*Blastoise*/    {9, 100, 200, -1},
		/*Caterpie*/    {10, -1, -1, -1},
		/*Metapod*/    {11, -1, -1, -1},
		/*Butterfree*/    {12, -1, -1, -1},
		/*Weedle*/    {13, -1, -1, -1},
		/*Kakuna*/    {14, -1, -1, -1},
		/*Beedrill*/    {15, -1, -1, -1},
		/*Pidgey*/    {16, -1, -1, -1},
		/*Pidgeotto*/    {17, -1, -1, -1},
		/*Pidgeot*/     {18, -1, -1, -1},
		/*Rattata*/    {19, -1, -1, -1},
		/*Raticate*/    {20, -1, -1, -1},
		/*Spearow*/    {21, -1, -1, -1},
		/*Fearow*/    {22, -1, -1, -1},
		/*Ekans*/    {23, 100, 200, -1},
		/*Arbok*/    {24, 100, 200, -1},
		/*Pikachu*/    {25, 100, 200, -1},
		/*Raichu*/    {26, 100, 200, -1},
		/*Sandshrew*/    {27, 100, 200, -1},
		/*Sandslash*/    {28, 100, 200, -1},
		/*Nidoran ♀*/    {29, 100, 200, -1},
		/*Nidorina*/    {30, 100, 200, -1},
		/*Nidoqueen*/    {31, 100, 200, -1},
		/*Nidoran ♂*/    {32, 100, 200, -1},
		/*Nidorino*/    {33, 100, 200, -1},
		/*Nidoking*/    {34, 100, 200, -1},
		/*Clefairy*/    {35, -1, -1, -1},
		/*Clefable*/    {36, 100, 200, -1},
		/*Vulpix*/    {37, 100, 200, -1},
		/*Ninetales*/    {38, 100, 200, -1},
		/*Jigglypuff*/    {39, -1, -1, -1},
		/*Wigglytuff*/    {40, 100, 200, -1},
		/*Zubat*/    {41, -1, -1, -1},
		/*Golbat*/    {42, -1, -1, -1},
		/*Oddish*/    {43, 100, 200, -1},
		/*Gloom*/    {44, 100, 200, -1},
		/*Vileplume*/    {45, 100, 200, -1},
		/*Paras*/    {46, 100, 200, -1},
		/*Parasect*/    {47, 100, 200, -1},
		/*Venonat*/    {48, 100, 200, -1},
		/*Venomoth*/    {49, 100, 200, -1},
		/*Diglett*/    {50, 100, 200, -1},
		/*Dugtrio*/    {51, 100, 200, -1},
		/*Meowth*/    {52, 100, 200, -1},
		/*Persian*/    {53, 100, 200, -1},
		/*Psyduck*/    {54, -1, -1, -1},
		/*Golduck*/    {55, 100, 200, -1},
		/*Mankey*/    {56, 100, 200, -1},
		/*Primeape*/    {57, 100, 200, -1},
		/*Growlithe*/    {58, 100, 850, -1},
		/*Arcanine*/    {59, 100, 200, -1},
		/*Poliwag*/    {60, -1, -1, -1},
		/*Poliwhirl*/    {61, -1, -1, -1},
		/*Poliwrath*/    {62, 100, 200, -1},
		/*Abra*/    {63, 100, 200, -1},
		/*Kadabra*/    {64, 100, 200, -1},
		/*Alakazam*/    {65, 100, 200, -1},
		/*Machop*/    {66, -1, -1, -1},
		/*Machoke*/    {67, -1, -1, -1},
		/*Machamp*/    {68, 100, 200, -1},
		/*Bellsprout*/    {69, 100, 200, -1},
		/*Weepinbell*/    {70, 100, 200, -1},
		/*Victreebel*/    {71, 100, 200, -1},
		/*Tentacool*/    {72, 100, 200, -1},
		/*Tentacruel*/    {73, 100, 200, -1},
		/*Geodude*/    {74, 100, 200, -1},
		/*Graveler*/    {75, 100, 200, -1},
		/*Golem*/    {76, 100, 200, -1},
		/*Ponyta*/    {77, 100, 200, -1},
		/*Rapidash*/    {78, 100, 200, -1},
		/*Slowpoke*/    {79, 100, 200, -1},
		/*Slowbro*/    {80, 100, 200, -1},
		/*Magnemite*/    {81, 100, 200, -1},
		/*Magneton*/    {82, 100, 200, -1},
		/*Farfetchd*/    {83, 0,  0, 0},
		/*Doduo*/    {84, 100, 200, -1},
		/*Dodrio*/    {85, 100, 200, -1},
		/*Seel*/    {86, 100, 200, -1},
		/*Dewgong*/    {87, 100, 200, -1},
		/*Grimer*/    {88, 100, 200, -1},
		/*Muk*/    {89, 100, 200, -1},
		/*Shellder*/    {90, 100, 200, -1},
		/*Cloyster*/    {91, 100, 200, -1},
		/*Gastly*/    {92, -1, -1, -1},
		/*Haunter*/    {93, -1, -1, -1},
		/*Gengar*/    {94, 100, 200, -1},
		/*Onix*/    {95, 100, 200, -1},
		/*Drowzee*/    {96, 100, 200, -1},
		/*Hypno*/    {97, 100, 200, -1},
		/*Krabby*/    {98, 100, 200, -1},
		/*Kingler*/    {99, 100, 200, -1},
		/*Voltorb*/    {100, 100, 200, -1},
		/*Electrode*/    {101, 100, 200, -1},
		/*Exeggcute*/    {102, 100, 200, -1},
		/*Exeggutor*/    {103, 90, 200, -1},
		/*Cubone*/    {104, 100, 200, -1},
		/*Marowak*/    {105, 100, 200, -1},
		/*Hitmonlee*/    {106, 100, 200, -1},
		/*Hitmonchan*/    {107, 100, 200, -1},
		/*Lickitung*/    {108, 100, 200, -1},
		/*Koffing*/    {109, 100, 200, -1},
		/*Weezing*/    {110, 100, 200, -1},
		/*Rhyhorn*/    {111, 100, 200, -1},
		/*Rhydon*/    {112, 90, 200, -1},
		/*Chansey*/    {113, 90, 200, 100},
		/*Tangela*/    {114, 90, 200, -1},
		/*Kangaskhan*/    {115, 90,  200, -1},
		/*Horsea*/    {116, 100, 200, -1},
		/*Seadra*/    {117, 100, 200, -1},
		/*Goldeen*/    {118, 100, 200, -1},
		/*Seaking*/    {119, 100, 200, -1},
		/*Staryu*/    {120, 100, 200, -1},
		/*Starmie*/    {121, 100, 200, -1},
		/*Mr Mime*/    {122, 100, 200, -1},
		/*Scyther*/    {123, 100, 200, -1},
		/*Jynx*/    {124, 100, 200, -1},
		/*Electabuzz*/    {125, 100, 200, -1},
		/*Magmar*/    {126, 100, 200, -1},
		/*Pinsir*/    {127, 100, 200, -1},
		/*Tauros*/    {128, 0,  0, 0},
		/*Magikarp*/    {129, -1, -1, -1},
		/*Gyarados*/    {130, 90, 200, 100},
		/*Lapras*/    {131, 90, 1500, 100},
		/*Ditto*/    {132, 100, 200, -1},
		/*Eevee*/    {133, 100, 700, -1},
		/*Vaporeon*/    {134, 100, 200, 100},
		/*Jolteon*/    {135, 100, 200, 100},
		/*Flareon*/    {136, 100, 200, 100},
		/*Porygon*/    {137, 100, 200, -1},
		/*Omanyte*/    {138, 100, 200, -1},
		/*Omastar*/    {139, 100, 200, -1},
		/*Kabuto*/    {140, 100, 200, -1},
		/*Kabutops*/    {141, 100, 200, -1},
		/*Aerodactyl*/    {142, 100, 200, -1},
		/*Snorlax*/    {143, 90, 200, 100},
		/*Articuno*/    {144, 0,  0, 0},
		/*Zapdos*/    {145, 0,  0, 0},
		/*Moltres*/    {146, 0,  0, 0},
		/*Dratini*/    {147, 100, 200, -1},
		/*Dragonair*/    {148, 100, 200, -1},
		/*Dragonite*/    {149, 90, 2000, 100},
		/*Mewtwo*/    {150, 0,  0, 0},
		/*Mew*/    {151, 0,  0, 0},
		/*Chikorita*/    {152, 100, 200, -1},
		/*Bayleef*/    {153, 100, 200, -1},
		/*Meganium*/    {154, 100, 200, -1},
		/*Cyndaquil*/    {155, 100, 500, -1},
		/*Quilava*/    {156, 100, 200, -1},
		/*Typhlosion*/    {157, 100, 200, -1},
		/*Totodile*/    {158, 100, 200, -1},
		/*Croconaw*/    {159, 100, 200, -1},
		/*Feraligatr*/    {160, 100, 200, -1},
		/*Sentret*/    {161, 100, 200, -1},
		/*Furret*/    {162, 100, 200, -1},
		/*Hoothoot*/    {163, 100, 200, -1},
		/*Noctowl*/    {164, 100, 200, -1},
		/*Ledyba*/    {165, 100, 200, -1},
		/*Ledian*/    {166, 100, 200, -1},
		/*Spinarak*/    {167, 100, 200, -1},
		/*Ariados*/    {168, 100, 200, -1},
		/*Crobat*/    {169, 100, 200, -1},
		/*Chinchou*/    {170, 100, 200, -1},
		/*Lanturn*/    {171, 100, 200, -1},
		/*Pichu*/    {172, 100, 200, -1},
		/*Cleffa*/    {173, 100, 200, -1},
		/*Igglybuff*/    {174, 100, 200, -1},
		/*Togepi*/    {175, 100, 200, -1},
		/*Togetic*/    {176, 100, 200, -1},
		/*Natu*/    {177, 100, 200, -1},
		/*Xatu*/    {178, 100, 200, -1},
		/*Mareep*/    {179, 90, 200, -1},
		/*Flaaffy*/    {180, 100, 200, -1},
		/*Ampharos*/    {181, 100, 200, -1},
		/*Bellossom*/    {182, 100, 200, -1},
		/*Marill*/    {183, 100, 200, -1},
		/*Azumarill*/    {184, 100, 200, -1},
		/*Sudowoodo*/    {185, 100, 200, -1},
		/*Politoed*/    {186, 100, 200, -1},
		/*Hoppip*/    {187, 100, 200, -1},
		/*Skiploom*/    {188, 100, 200, -1},
		/*Jumpluff*/    {189, 100, 200, -1},
		/*Aipom*/    {190, 100, 200, -1},
		/*Sunkern*/    {191, 100, 200, -1},
		/*Sunflora*/    {192, 100, 200, -1},
		/*Yanma*/    {193, 100, 200, -1},
		/*Wooper*/    {194, 100, 200, -1},
		/*Quagsire*/    {195, 100, 200, -1},
		/*Espeon*/    {196, 95, 200, 100},
		/*Umbreon*/    {197, 95, 200, 100},
		/*Murkrow*/    {198, 100, 200, -1},
		/*Slowking*/    {199, 100, 200, -1},
		/*Misdreavus*/    {200, 100, 200, -1},
		/*Unown*/    {201, 0,  0, 0},
		/*Wobbuffet*/    {202, 100, 200, -1},
		/*Girafarig*/    {203, 100, 200, -1},
		/*Pineco*/    {204, 100, 200, -1},
		/*Forretress*/    {205, 100, 200, -1},
		/*Dunsparce*/    {206, 100, 200, -1},
		/*Gligar*/    {207, 100, 200, -1},
		/*Steelix*/    {208, 100, 200, -1},
		/*Snubbull*/    {209, 100, 200, -1},
		/*Granbull*/    {210, 100, 200, -1},
		/*Qwilfish*/    {211, 100, 200, -1},
		/*Scizor*/    {212, 100, 200, -1},
		/*Shuckle*/    {213, 100, 200, -1},
		/*Heracross*/    {214, 100, 200, -1},
		/*Sneasel*/    {215, 100, 200, -1},
		/*Teddiursa*/    {216, -1, -1, -1},
		/*Ursaring*/    {217, 100, 200, -1},
		/*Slugma*/    {218, 100, 200, -1},
		/*Magcargo*/    {219, 100, 200, -1},
		/*Swinub*/    {220, 100, 400, -1},
		/*Piloswine*/    {221, 100, 400, -1},
		/*Corsola*/    {222, 0,  0, 0},
		/*Remoraid*/    {223, 100, 200, -1},
		/*Octillery*/    {224, 100, 200, -1},
		/*Delibird*/    {225, 100, 200, -1},
		/*Mantine*/    {226, 100, 800, -1},
		/*Skarmory*/    {227, 100, 750, -1},
		/*Houndour*/    {228, 100, 600, -1},
		/*Houndoom*/    {229, 100, 200, -1},
		/*Kingdra*/    {230, 100, 200, -1},
		/*Phanpy*/    {231, 100, 200, -1},
		/*Donphan*/    {232, 100, 200, -1},
		/*Porygon2*/    {233, 90, 200, -1},
		/*Stantler*/    {234, 100, 200, -1},
		/*Smeargle*/    {235, 100, 200, -1},
		/*Tyrogue*/    {236, 100, 200, -1},
		/*Hitmontop*/    {237, 100, 200, -1},
		/*Smoochum*/    {238, 100, 200, -1},
		/*Elekid*/    {239, 100, 200, -1},
		/*Magby*/    {240, 100, 200, -1},
		/*Miltank*/    {241, 90, 200, -1},
		/*Blissey*/    {242, 80, 200, 100},
		/*Raikou*/    {243, 0,  0, 0},
		/*Entei*/    {244, 0,  0, 0},
		/*Suicune*/    {245, 0,  0, 0},
		/*Larvitar*/    {246, 100, 200, -1},
		/*Pupitar*/    {247, 100, 200, -1},
		/*Tyranitar*/    {248, 100, 200, 100},
		/*Lugia*/    {249, 0,  0, 0},
		/*Celebi*/    {251, 0,  0, 0}
	};
	
	private static boolean[] UNOWN_CONFIG = new boolean[] {
		/*unset*/ true,
		/*A*/ false,
		/*B*/ false,
		/*C*/ false,
		/*D*/ true,
		/*E*/ false,
		/*F*/ false,
		/*G*/ false,
		/*H*/ true,
		/*I*/ false,
		/*J*/ false,
		/*K*/ true,
		/*L*/ false,
		/*M*/ true,
		/*N*/ false,
		/*O*/ false,
		/*P*/ false,
		/*Q*/ false,
		/*R*/ false,
		/*S*/ false,
		/*T*/ false,
		/*U*/ false,
		/*V*/ true,
		/*W*/ false,
		/*X*/ true,
		/*Y*/ true,
		/*Z*/ true,
		/*!*/ true,
		/*?*/ true
	};
	
	public static class InvalidConfigruationException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public InvalidConfigruationException(String string) {
			super(string);
		}
	}
	
	private static void assertValidConfig() {
		if (!configurationValidated) {
			for (int i = 0; i < CONFIGURATION.length; i++) {
				int[] oneConfig = CONFIGURATION[i];
				if (oneConfig.length != 4) {
					throw new InvalidConfigruationException("Config item should " + i + " contain 4 elements but it is: " + oneConfig.length);
				}
				if (oneConfig[1] < -1 || oneConfig[1] > 100) {
					throw new InvalidConfigruationException("Config value " + i + " out of range (-1:100): " + oneConfig[1]);
				}				
				if (oneConfig[3] < -1 || oneConfig[3] > 100) {
					throw new InvalidConfigruationException("Config value " + i + " out of range (-1:100): " + oneConfig[2]);
				}
				if (oneConfig[3] != -1 && oneConfig[1] > oneConfig[3]) {
					throw new InvalidConfigruationException("Config item " + i + " is notifying on lower level than it is informing: " + oneConfig[1] + ">" + oneConfig[2]);
				}
			}
		}
	}
	
	public static HashMap<Integer, PokemonConfig> fetchPokemonConfig() {
		assertValidConfig();
		HashMap<Integer, PokemonConfig> configs = new HashMap<Integer, PokemonConfig>();
		for (int i = 0; i < CONFIGURATION.length; i++) {
			int[] oneConfig = CONFIGURATION[i];
			PokemonConfig onePokemonConfig = new PokemonConfig(oneConfig[0], oneConfig[1], oneConfig[2], oneConfig[3]);
			configs.put(oneConfig[0], onePokemonConfig);
		}
		return configs;
	}
	
	public static int[] getIgnoredList() {
		assertValidConfig();
		ArrayList<Integer> ignored = new ArrayList<Integer>();
		for (int i = 0; i < CONFIGURATION.length; i++) {
			int[] oneConfig = CONFIGURATION[i];
			if (oneConfig[1] == -1 || oneConfig[1] > 90) {
				ignored.add(oneConfig[0]);
			}
		}
		int[] result = new int[ignored.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = ignored.get(i);
		}
		return result;
	}
	
	public static String getIngoredListAsString() {
		assertValidConfig();
		int[] ignoredIndex = Configuration.getIgnoredList();
		StringBuffer buffer = new StringBuffer();
		if (ignoredIndex.length > 0) {
			buffer.append("[" + ignoredIndex[0]);
			for (int i = 1; i < ignoredIndex.length; i++) {
				buffer.append("," + ignoredIndex[i]);
			}
			buffer.append("]");
		}
		return buffer.toString();
	}
	
	public static boolean[] getUnownConfig() {
		return UNOWN_CONFIG;
	}
	
	@SuppressWarnings("unused")
	private static void generateFromFile(String[] args) throws NumberFormatException, JSONException, IOException {
		FileReader fr = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(fr);
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			JSONArray json = new JSONArray(sCurrentLine);
			for (int i = 0; i < json.length(); i++) {
				JSONObject onePoke = (JSONObject)json.get(i);
				int id = Integer.parseInt((String)onePoke.get("Number"));
				String name = (String)onePoke.get("Name");

				System.out.println("/*"+name+"*/    {"+id+", 100, 200, -1},");
			}
		}
		br.close();
	}
	public static void main(String[] args) throws JSONException, IOException {
		//generateFromFile(args);
		assertValidConfig();
	}
}

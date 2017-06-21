package ch.lezepito.pokeGoTracker.config;

public class PokemonConfig {
	private int id;
	private int generalThreshold;
	private int generalCpThreshold;
	private int urgentThreshold;
	
	public PokemonConfig(int id, int threshold, int cpThreshold, int urgentThreshold) {
		this.id = id;
		this.generalThreshold = threshold;
		this.generalCpThreshold = cpThreshold;
		this.urgentThreshold = urgentThreshold;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGeneralThreshold() {
		return generalThreshold;
	}
	public void setGeneralThreshold(int threshold) {
		this.generalThreshold = threshold;
	}

	public int getGeneralCpThreshold() {
		return generalCpThreshold;
	}

	public void setGeneralCpThreshold(int generalCpThreshold) {
		this.generalCpThreshold = generalCpThreshold;
	}

	public int getUrgentThreshold() {
		return urgentThreshold;
	}

	public void setUrgentThreshold(int urgentThreshold) {
		this.urgentThreshold = urgentThreshold;
	}
}

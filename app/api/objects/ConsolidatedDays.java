package api.objects;

public class ConsolidatedDays {
	
	// Wind
	protected Speed minWindSpeed;
	protected Speed maxWindSpeed;
	
	protected Speed sumWindSpeed;
	protected int nbWindSpeed;
	
	// Temperature
	protected Temperature minTemperature;
	protected Temperature maxTemperature;
	
	protected Temperature sumMaxTemperature;
	protected int nbMaxTemperature;
	
	// Rain
	protected Double minPrecipitation;
	protected Double maxPrecipitation;
	
	protected Double sumPrecipitation;
	protected int nbPrecipitation;
	
	// Days of specific condition
	protected int nbSunnyDays;
	protected int nbRainyDays;
	protected int nbSnowyDays;
	
	/*
	 * Wind
	 */
	
	/**
	 * @return the minWindSpeed
	 */
	public Speed getMinWindSpeed() {
		return minWindSpeed;
	}
	
	/**
	 * @param minWindSpeed the minWindSpeed to set
	 */
	public ConsolidatedDays setMinWindSpeed(Speed minWindSpeed) {
		this.minWindSpeed = minWindSpeed;
		return this;
	}
	
	/**
	 * @return the maxWindSpeed
	 */
	public Speed getMaxWindSpeed() {
		return maxWindSpeed;
	}
	
	/**
	 * @param maxWindSpeed the maxWindSpeed to set
	 */
	public ConsolidatedDays setMaxWindSpeed(Speed maxWindSpeed) {
		this.maxWindSpeed = maxWindSpeed;
		return this;
	}
	
	/**
	 * @return the sumWindSpeed
	 */
	public Speed getSumWindSpeed() {
		return sumWindSpeed;
	}
	
	/**
	 * @param sumWindSpeed the sumWindSpeed to set
	 */
	public ConsolidatedDays setSumWindSpeed(Speed sumWindSpeed) {
		this.sumWindSpeed = sumWindSpeed;
		return this;
	}
	
	/**
	 * @return the nbWindSpeed
	 */
	public int getNbWindSpeed() {
		return nbWindSpeed;
	}
	
	/**
	 * @param nbWindSpeed the nbWindSpeed to set
	 */
	public ConsolidatedDays setNbWindSpeed(int nbWindSpeed) {
		this.nbWindSpeed = nbWindSpeed;
		return this;
	}
	
	/*
	 * Temperature
	 */
	
	/**
	 * @return the minTemperature
	 */
	public Temperature getMinTemperature() {
		return minTemperature;
	}
	
	/**
	 * @param minTemperature the minTemperature to set
	 */
	public ConsolidatedDays setMinTemperature(Temperature minTemperature) {
		this.minTemperature = minTemperature;
		return this;
	}
	
	/**
	 * @return the maxTemperature
	 */
	public Temperature getMaxTemperature() {
		return maxTemperature;
	}
	
	/**
	 * @param maxTemperature the maxTemperature to set
	 */
	public ConsolidatedDays setMaxTemperature(Temperature maxTemperature) {
		this.maxTemperature = maxTemperature;
		return this;
	}
	
	/**
	 * @return the sumTemperature
	 */
	public Temperature getSumMaxTemperature() {
		return sumMaxTemperature;
	}
	
	/**
	 * @param sumTemperature the sumTemperature to set
	 */
	public ConsolidatedDays setSumMaxTemperature(Temperature sumMaxTemperature) {
		this.sumMaxTemperature = sumMaxTemperature;
		return this;
	}
	
	/**
	 * @return the nbTemperature
	 */
	public int getNbMaxTemperature() {
		return nbMaxTemperature;
	}
	
	/**
	 * @param nbTemperature the nbTemperature to set
	 */
	public ConsolidatedDays setNbMaxTemperature(int nbMaxTemperature) {
		this.nbMaxTemperature = nbMaxTemperature;
		return this;
	}
	
	/**
	 * @return the minPrecipitation
	 */
	public Double getMinPrecipitation() {
		return minPrecipitation;
	}
	
	/**
	 * @param minPrecipitation the minPrecipitation to set
	 */
	public ConsolidatedDays setMinPrecipitation(Double minPrecipitation) {
		this.minPrecipitation = minPrecipitation;
		return this;
	}
	
	/*
	 * Precipitation
	 */
	
	/**
	 * @return the maxPrecipitation
	 */
	public Double getMaxPrecipitation() {
		return maxPrecipitation;
	}
	
	/**
	 * @param maxPrecipitation the maxPrecipitation to set
	 */
	public ConsolidatedDays setMaxPrecipitation(Double maxPrecipitation) {
		this.maxPrecipitation = maxPrecipitation;
		return this;
	}
	
	/**
	 * @return the sumPrecipitation
	 */
	public Double getSumPrecipitation() {
		return sumPrecipitation;
	}
	
	/**
	 * @param sumPrecipitation the sumPrecipitation to set
	 */
	public ConsolidatedDays setSumPrecipitation(Double sumPrecipitation) {
		this.sumPrecipitation = sumPrecipitation;
		return this;
	}
	
	/**
	 * @return the nbPrecipitation
	 */
	public int getNbPrecipitation() {
		return nbPrecipitation;
	}
	
	/**
	 * @param nbPrecipitation the nbPrecipitation to set
	 */
	public ConsolidatedDays setNbPrecipitation(int nbPrecipitation) {
		this.nbPrecipitation = nbPrecipitation;
		return this;
	}
	
	/*
	 * Status
	 */
	
	/**
	 * @return the nbSunnyDays
	 */
	public int getNbSunnyDays() {
		return nbSunnyDays;
	}
	
	/**
	 * @param nbSunnyDays the nbSunnyDays to set
	 */
	public ConsolidatedDays setNbSunnyDays(int nbSunnyDays) {
		this.nbSunnyDays = nbSunnyDays;
		return this;
	}
	
	/**
	 * @return the nbRainyDays
	 */
	public int getNbRainyDays() {
		return nbRainyDays;
	}
	
	/**
	 * @param nbRainyDays the nbRainyDays to set
	 */
	public ConsolidatedDays setNbRainyDays(int nbRainyDays) {
		this.nbRainyDays = nbRainyDays;
		return this;
	}
	
	/**
	 * @return the nbSnowyDays
	 */
	public int getNbSnowyDays() {
		return nbSnowyDays;
	}
	
	/**
	 * @param nbSnowyDays the nbSnowyDays to set
	 */
	public ConsolidatedDays setNbSnowyDays(int nbSnowyDays) {
		this.nbSnowyDays = nbSnowyDays;
		return this;
	}
}

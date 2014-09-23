package api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * CIty representation
 * @author Jerome Baudoux
 *
 */
public class City implements Comparable<City> {

	protected String name;
	protected String country;
	
	/**
	 * City constructor
	 */
	public City() {}
	
	/**
	 * City constructor
	 * @param name city name
	 * @param country country name
	 */
	public City(String name, String country) {
		this.name = name;
		this.country = country;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public City setName(String name) {
		this.name = name;
		return this;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public City setCountry(String country) {
		this.country = country;
		return this;
	}
	
	/**
	 * @return name, country
	 */
	@JsonIgnore
	public String getNameAndCountry() {
		return this.name + ", " + this.country;
	}

	/**
	 * Natural comparator
	 * @param o other object
	 * @return -1/0/1
	 */
	@Override
	public int compareTo(City o) {
		return getNameAndCountry().compareTo(o.getNameAndCountry());
	}

	/**
	 * Warning: modified content !
	 * Lower case was added to the stings !
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.country == null) ? 0 : this.country.toLowerCase().hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.toLowerCase().hashCode());
		return result;
	}

	/**
	 * Warning: modified content !
	 * equalsIgnoreCase was used to compare the stings !
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (this.country == null) {
			if (other.country != null)
				return false;
		} else if (!this.country.equalsIgnoreCase(other.country))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
}

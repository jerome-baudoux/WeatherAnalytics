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
		return name + ", " + country;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.toLowerCase().hashCode());
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equalsIgnoreCase(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
}

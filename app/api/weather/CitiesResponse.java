package api.weather;

import java.util.List;

import api.ApiResponse;

/**
 * Response of a city request
 * @author Jerome Baudoux
 */
public class CitiesResponse extends ApiResponse<CitiesResponse> {

	/**
	 * List of cities
	 */
	protected List<String> cities;

	/**
	 * @return List of cities
	 */
	public List<String> getCities() {
		return cities;
	}

	/**
	 * @param cities List of cities
	 * @return current object
	 */
	public CitiesResponse setCities(List<String> cities) {
		this.cities = cities;
		return this;
	}
}

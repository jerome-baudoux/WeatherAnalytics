package api.response.weather;

import java.util.List;

import api.objects.City;
import api.response.ApiResponse;

/**
 * Response of a city request
 * @author Jerome Baudoux
 */
public class CitiesResponse extends ApiResponse<CitiesResponse> {

	/**
	 * List of cities
	 */
	protected List<City> cities;

	/**
	 * @return List of cities
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * @param cities List of cities
	 * @return current object
	 */
	public CitiesResponse setCities(List<City> cities) {
		this.cities = cities;
		return this;
	}
}

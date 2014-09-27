package api.objects;

/**
 * A class to hold a temperature
 * @author Jerome Baudoux
 */
public class Temperature implements Comparable<Temperature> {

	protected int celsius;
	protected int fahrenheit;

	public int getCelsius() {
		return celsius;
	}

	public Temperature setCelsius(int celsius) {
		this.celsius = celsius;
		return this;
	}

	public int getFahrenheit() {
		return fahrenheit;
	}

	public Temperature setFahrenheit(int fahrenheit) {
		this.fahrenheit = fahrenheit;
		return this;
	}

	@Override
	public int compareTo(Temperature o) {
		return new Integer(this.celsius).compareTo(o.celsius);
	}
	
	/**
	 * Adds two temperatures
	 * @param first first temperature
	 * @param second second temperature
	 * @return merged temperature
	 */
	public static Temperature add(Temperature first, Temperature second) {
		if(first == null) {
			return second;
		}
		if(second == null) {
			return first;
		}
		return new Temperature()
			.setCelsius(first.getCelsius() + second.getCelsius())
			.setFahrenheit(first.getFahrenheit() + second.getFahrenheit());
	}
}

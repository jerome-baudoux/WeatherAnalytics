package api.objects;

/**
 * A class to hold a temperature
 * @author Jerome Baudoux
 */
public class Temperature {

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
}

package api.objects;

/**
 * A class to hold a speed
 * @author Jerome Baudoux
 */
public class Speed {

	protected int kmph;
	protected int mph;
	
	public int getKmph() {
		return kmph;
	}
	public Speed setKmph(int kmph) {
		this.kmph = kmph;
		return this;
	}
	public int getMph() {
		return mph;
	}
	public Speed setMph(int mph) {
		this.mph = mph;
		return this;
	}
}

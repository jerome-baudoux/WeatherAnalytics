package api.objects;

/**
 * A class to hold a speed
 * @author Jerome Baudoux
 */
public class Speed implements Comparable<Speed> {

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

	@Override
	public int compareTo(Speed o) {
		return new Integer(this.kmph).compareTo(o.kmph);
	}
	
	/**
	 * Adds two Speed
	 * @param first first Speed
	 * @param second second Speed
	 * @return merged Speed
	 */
	public static Speed add(Speed first, Speed second) {
		if(first == null) {
			return second;
		}
		if(second == null) {
			return first;
		}
		return new Speed()
			.setKmph(first.getKmph() + second.getKmph())
			.setMph(first.getMph() + second.getMph());
	}
}

package aiInterface.data;

/**
 * Encapsulates a number.
 * 
 * @author tristanbehrens
 *
 */
public class Numeral extends Parameter 
{

	/** The value of the numner. */
	private Number value;

	/**
	 * Contructs a number.
	 * 
	 * @param value
	 */
	public Numeral(Number value) {
		
		this.value = value;
		
	}
	

	public Number getValue() {
		
		return value;
		
	}

	@Override
	public Object clone() {
		return new Numeral(value);
	}

	@Override
	public boolean equals(Object obj) {
		
		if( !(obj instanceof Numeral) )
			return false;
		
		Numeral num = (Numeral) obj;
		
		return num.value.equals(value); 
		
	}

}

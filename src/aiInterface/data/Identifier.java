package aiInterface.data;

/**
 * An identifier is an arbitrary string.
 * 
 * @author tristanbehrens
 *
 */
/**
 * @author tristanbehrens
 *
 */
public class Identifier extends Parameter {

	/** The identifier itself. */
	private String value = null;

	/** 
	 * Constructs an identifier.
	 * 
	 * @param value
	 */
	public Identifier(String value) {
		
		this.value = value;
		
	}
	
	/**
	 * Returns the identifier.
	 * 
	 * @return the identifier as a string
	 */
	public String getValue() {
		
		return value;
		
	}
	

	@Override
	public Object clone() {

		return new Identifier(this.value);
	
	}

	@Override
	public boolean equals(Object obj) {
		
		if( !(obj instanceof Identifier) )
			return false;
		
		Identifier id = (Identifier) obj;
		
		return id.value.equals(value); 
		
	}

}

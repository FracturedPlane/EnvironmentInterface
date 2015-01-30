package aiInterface.data;

import java.util.LinkedList;

import aiInterface.data.conversion.Converter;

/**
 * A percept.
 * A percept consists of a name and some parameters.
 * 
 * @author tristanbehrens
 *
 */
public class Percept extends DataContainer 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5929676291607949546L;

	private ParameterList params;
	/**
	 * Constructs a percept from a name.
	 * 
	 * @param name
	 */
	public Percept(String name) 
	{
		super(name);
	}
	
	/** 
	 * Contructs a percept from a name and an array of parameters.
	 * 
	 * @param name the name.
	 * @param parameters the parameters.
	 */
	public Percept(String name, Parameter...parameters) 
	{
		super(name);
		
		this.params = new ParameterList();
	}

	/** 
	 * Contructs a percept from a name and an array of parameters.
	 * 
	 * @param name the name.
	 * @param parameters the parameters.
	 */
	public Percept(String name, ParameterList parameters) 
	{
		super(name);
		this.params = new ParameterList();
		
	}

	/*
	@Override
	protected String toXML(int depth) {

		String xml = "";
		
		xml += indent(depth) + "<percept name=\"" + name + "\">" + "\n";
		
		for( Parameter p : params ) {
			
			xml += indent(depth+1) + "<perceptParameter>" + "\n";
			xml += p.toXML(depth+2);
			xml += indent(depth+1) + "</perceptParameter>" + "\n";
			
		}

		xml += indent(depth) + "</percept>" + "\n";

		return xml;

	}

	@Override
	public String toProlog() {
		
		String ret = "";
		
		ret+=name;

		if( params.isEmpty() == false) {
			ret += "(";
			
			ret += params.getFirst().toProlog();
			
			for( int a = 1 ; a < params.size(); a++ ) {
				Parameter p = params.get(a);
				ret += "," + p.toProlog();
			} 
			
			ret += ")";
		}
		
		return ret;
	
	}
	*/

	/*	public String toProlog() {
		
		String ret = "percept";
		
		ret+="(";
		
		ret+=name;
		
		for( Parameter p : params ) 
			ret += "," + p.toProlog();
		
		ret+=")";
		
		return ret;
	
	}*/


	@Override
	public boolean equals(Object obj) {
		
		if( !(obj instanceof Percept) )
			return false;
		
		return super.equals(obj);

	}

	@Override
	public DataContainer convertToDataContainer(String source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data Convert(DataContainer convert, Converter converter, Data data)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataContainer convert(DataContainer dataContainer)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

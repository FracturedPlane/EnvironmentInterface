package aiInterface.data;

import java.util.Arrays;
import java.util.LinkedList;

import aiInterface.data.conversion.Converter;

/**
 * This enum represents commands that can be sent to the environment.
 * 
 * This class does not work very well...
 * 
 * @author tristanbehrens
 *
 */
public class EnvironmentCommand extends DataContainer 
{

	/** start the environment */
	public static final int START = 1;
	
	/** stop the environment */
	public static final int KILL = 2;
	
	/** pause the environment */
	public static final int PAUSE = 3;
	
	/** reset the environment */
	public static final int RESET = 4;
	
	/** initialize the environment */
	public static final int INIT = 5; 
	
	/** a command that is not defined yet uses name and parameters. */
	public static final int MISC = 6;

	private int type = 0;
	
	private ParameterList params;

	/**
	 * Contructs an environment-command that is not MISC.
	 * 
	 * @param type
	 * @param params
	 */
	public EnvironmentCommand(int type, Parameter...params)
	{
		
		super(); // no name, no params
		
		this.type = type;
		this.params = new ParameterList();
		
		// do not get fooled by the caller
		if( this.type < 1 || this.type > 6)
			this.type = 0;
		
		if( this.type == INIT ) {
			
			this.params.addAll(Arrays.asList(params));
			
		}
		else if( this.type == MISC ) {

			this.params.addAll(Arrays.asList(params));
			
		}
		
	}
	
	public EnvironmentCommand(int type, ParameterList params) 
	{
		super(); // no name, no params
		
		this.type = type;
		this.params = new ParameterList();
		
		// do not get fooled by the caller
		if( this.type < 1 || this.type > 6)
			this.type = 0;
		
		if( this.type == INIT ) {
			
			this.params = params;
			
		}
		else if( this.type == MISC ) {

			this.params = params;
			
		}
		
	}
	
	/*
	public String toProlog() {

		String ret = "environmentcommand";
		
		ret+="(";
		
		if( type == START)
			ret += "start";
		else if( type == KILL)
			ret += "kill";
		else if( type == PAUSE)
			ret += "paus";
		else if( type == RESET)
			ret += "reset";
		else if( type == INIT)
			ret += "init";
		else if( type == MISC) {
			
			ret += "misc";
			ret+= "," + name;
		
		}
		
		for( Parameter p : params ) 
			ret += "," + p.toProlog();
		
		ret+=")";
		
		return ret;
	
	}
	*/

//	protected String toXML(int depth) {
//
//		String xml = "";
//		
//		if( type != MISC && type != INIT ) {
//			
//			// the name 
//			xml += indent(depth) + "<environmentCommand ";
//
//			// the type
//			xml += "type=\"";
//			if( type == START)
//				xml += "start";
//			else if( type == KILL)
//				xml += "kill";
//			else if( type == PAUSE)
//				xml += "paus";
//			else if( type == RESET)
//				xml += "reset";
//			xml += "\">" + "\n";
//
//			xml += indent(depth) + "</environmentCommand>" + "\n";
//	
//		}
//		else if( type == INIT ) {
//			
//			xml += indent(depth) + "<environmentCommand type=\"init\">" + "\n";
//			
//			for( Parameter p : params ) {
//				
//				xml += indent(depth+1) + "<environmentCommandParameter>" + "\n";
//				xml += p.toXML(depth+2);
//				xml += indent(depth+1) + "</environmentCommandParameter>" + "\n";
//				
//			}
//
//			xml += indent(depth) + "</environmentCommand>" + "\n";
//			
//		}
//		else if( type == MISC ) {
//		
//			xml += indent(depth) + "<environmentCommand name=\"" + name + "\" type=\"misc\">" + "\n";
//			
//			for( Parameter p : params ) {
//				
//				xml += indent(depth+1) + "<environmentCommandParameter>" + "\n";
//				xml += p.toXML(depth+2);
//				xml += indent(depth+1) + "</environmentCommandParameter>" + "\n";
//				
//			}
//
//			xml += indent(depth) + "</environmentCommand>" + "\n";
//			
//		}
//		
//		return xml;
//	
//	}
	
	/**
	 * Returns the type of the command.
	 * 
	 * @return the type
	 */
	public int getType() {
		
		return type;

	}
	
	@Override
	public boolean equals(Object obj) {
		
		if( !(obj instanceof EnvironmentCommand) )
			return false;
		
		return super.equals(obj);

	}

	/**
	 * Factory method.
	 * @return
	 */
	public static EnvironmentCommand newStartCommand() {
		
		return new EnvironmentCommand(EnvironmentCommand.START);
		
	}

	/**
	 * Factory method.
	 * @return
	 */
	public static EnvironmentCommand newKillCommand() {
		
		return new EnvironmentCommand(EnvironmentCommand.KILL);
		
	}

	/**
	 * Factory method.
	 * @return
	 */
	public static EnvironmentCommand newPauseCommand() {
		
		return new EnvironmentCommand(EnvironmentCommand.PAUSE);
		
	}

	/**
	 * Factory method.
	 * @return
	 */
	public static EnvironmentCommand newResetCommand() {
		
		return new EnvironmentCommand(EnvironmentCommand.RESET);
		
	}

	/**
	 * Factory method.
	 * @return
	 */
	public static EnvironmentCommand newInitCommand(ParameterList params) 
	{
		
		return new EnvironmentCommand(EnvironmentCommand.INIT, params);
		
	}

	@Override
	public DataContainer convertToDataContainer(String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data Convert(DataContainer convert, Converter converter, Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataContainer convert(DataContainer dataContainer) {
		// TODO Auto-generated method stub
		return null;
	}

}

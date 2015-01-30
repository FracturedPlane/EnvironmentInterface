package aiInterface.data;


import java.util.LinkedList;

import aiInterface.data.conversion.Converter;

/**
 * An action that can be performed by an agent through its associated entity/ies.
 * An action consists of a name and a sequence of parameters.
 * 
 * @author tristanbehrens
 */
public class Action extends DataContainer 
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2483470223360080046L;

	
	
	public Action()
	{
		super();
	}
	
	/**
	 * Constructs an action.
	 * 
	 * @param action
	 */
	public Action(String action) 
	{
		super(action);
	}

	public Action(Action action)
	{
		this(action.getSource());
		
	}


	@Override
	public boolean equals(Object obj) 
	{
		
		if( obj.getClass() == this.getClass() )
		{
			Action other = (Action) obj;
			return other.getSource() == this.getSource() && ( other.getTimeStamp() == this.getTimeStamp() );
		}
			return false;
		

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

	public LinkedList<Parameter> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataContainer convertToDataContainer(String source)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
package aiInterface.data;

import java.util.LinkedList;

import aiInterface.data.conversion.Converter;
import aiInterface.data.Attribute;


/**
 * 
 * 
 * @author glen
 *
 */
public class Parameter extends DataContainer
{
	private LinkedList<Parameter> parameters;
	private LinkedList<Attribute> attributes;
	private String name;
	
	public Parameter()
	{
		super("");
		this.parameters = new LinkedList<Parameter>();
		this.name = "";
	}
	
	public Parameter(String name)
	{
		this();
		this.setSource(new String(name));
		this.setName(new String (name));
		
	}
	
	public Parameter(Parameter param)
	{
		this();
		this.parameters = param.parameters;
		this.setName(new String(param.getName()));
	}
	
	public void addParameter(Parameter param)
	{
		this.parameters.add( new Parameter(param));
	}
	
	public LinkedList<Parameter> getParameters()
	{
		return this.parameters;
	}
	
	public boolean removeParameter(Parameter param)
	{
		return this.parameters.remove(param);
	}
	
	public String getSource()
	{
		String out = "";
		// System.out.println(this.getName());
		if ( this.parameters.isEmpty())
		{
			out = this.getName();
		}
		else
		{
			// out = out + "[ " + this.getName();
			int i = 0;
			out = out + "[" + this.getName() + ",[";
			for ( Parameter param : this.getParameters())
			{
				i++;
				if ( i < this.getParameters().size()  )
				{
					out = out + param.getSource() + ",";
				}
				else
				{
					out = out + param.getSource();
				}
			}
			out = out + "]]";
		}
		
		
		
		return out;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public DataContainer convert(DataContainer dataContainer) 
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
	public DataContainer convertToDataContainer(String source)
	{
		// TODO Auto-generated method stub
		return null;
	}
	

}

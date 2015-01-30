package aiInterface.data;

import java.sql.Timestamp;
import java.util.Date;

import aiInterface.data.conversion.Converter;


/**
 * Designed to act as a container for data that is being passed between
 * Agents and Entitys. 
 *
 * @author Glen Berseth
 */
public abstract class DataContainer implements Data, Converter
{
	
	private String source;
	private Timestamp timestamp;
	
	public DataContainer()
	{
		super();
		this.setTimeStamp((new Date()).getTime());
		this.setSource("");
	}
	
	public DataContainer(String source)
	{
		this();
		this.setSource(source);
	}
	
	public String getSource()
	{
		return new String ( this.source );
	}
	
	public void setSource( String source )
	{
		this.source = new String( source );
	}
	
	public Timestamp getTimeStamp()
	{
		return new Timestamp( this.timestamp.getTime() );
	}
	
	private void setTimeStamp(Long time)
	{
		this.timestamp = new Timestamp( time );
	}
	
	public abstract DataContainer convertToDataContainer(String source);
}

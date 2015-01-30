package aiInterface.connection;

import java.io.IOException;

import aiInterface.data.DataContainer;
import aiInterface.data.conversion.Converter;
import aiInterface.intreface.Interfacer;

/**
 * The start of the Connection Interface.
 * @author Glen Berseth
 *
 */
public abstract class Connection implements Interfacer
{
	/*
	 * This is the parent class of all DataContainer types
	 */
	private DataContainer dataContainer;
	private Converter converter;
	private String ackString = null;
	
	/**
	 * This will sent the Type that will be used to atempt to 
	 * convert outgoing messages into before sending.
	 * @param converter
	 */
	public void setConverter(Converter converter)
	{
		this.converter = converter;
	}
	
	/**
	 * The passed type will be used to contain data that is 
	 * sent to this Connection.
	 * @param containerType
	 */
	public void setContainerType(DataContainer containerType)
	{
		this.dataContainer = containerType;
	}
	
	/**
	 * Used to get a copy of the instance that is used to convert
	 * data on its way out.
	 * @return
	 */
	public Converter getConverter()
	{
		return this.converter;
	}
	
	/**
	 * Returns the DataContainer type that that will be used to 
	 * hold messages that are received by thie Connection.
	 * @return
	 */
	public DataContainer getDataContainer()
	{
		return this.dataContainer;
	}
	
	/**
	 * Used to send a bit of data back to a newly established connection to
	 * inform it that its connection is received and confirmed.
	 */
	public void acknowledgeConnection()
	{
		try 
		{
			if (this.ackString != null)
			{
				this.send(this.getAckString());
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAckString(String ackString) 
	{
		this.ackString = new String(ackString);
	}

	public String getAckString() 
	{
		return ackString;
	}
}

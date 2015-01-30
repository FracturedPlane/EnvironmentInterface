package aiInterface.intreface;

import java.io.IOException;

import aiInterface.data.DataContainer;

/**
 * All classes that support the message sending/getting part of the Interface
 * should extend this class and implement these methods.
 * @author Glen Berseth
 *
 */
public interface Interfacer
{

	/**
	 * This method will send a message in the form of a String 
	 * @param message
	 * @return This method will return a positive number if the message is sent
	 * and a negative number if the message is not sent.
	 */
	public int send( String message ) throws IOException;
	
	/**
	 * Gets a Message
	 * @return If there is no message to get then null will 
	 * be returned. Otherwise the message will be returned.
	 */
	public String getMessage() throws IOException;
	
	/**
	 * This method is to make it easier to send <code>DataContainer</code> 
	 * messages between <code>Interfacer</code>s easier.
	 * @param dataContainer
	 * @return number of bytes sent
	 * @throws IOException
	 */
	public int send(DataContainer dataContainer) throws IOException;
	
	
	
}

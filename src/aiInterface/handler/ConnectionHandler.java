package aiInterface.handler;

import java.util.LinkedList;

import org.w3c.dom.Document;

import aiInterface.connection.Connection;
import aiInterface.data.DataContainer;
import aiInterface.data.Percept;
import aiInterface.intreface.Entity;

/**
 * Handles incoming messages from the Connections.
 * 
 * @author Glen Berseth
 *
 */
public interface ConnectionHandler 
{
	
	/**
	 * Classes that implement this interface need to fully implement
	 * these two methods.
	 * @param message
	 */
	public abstract void handleMessage(String message);
	
	public abstract void handleData(DataContainer container);
	
}

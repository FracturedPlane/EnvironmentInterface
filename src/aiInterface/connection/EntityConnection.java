package aiInterface.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import aiInterface.handler.EntityConnectionHandler;
import aiInterface.intreface.Interfacer;

/**
 * Abstract class to encapsulate the functionality of what a 
 * Entity should be/do.
 * @author glen
 *
 */
public abstract class EntityConnection extends Connection implements Interfacer
{
	
	/**
	 * Holds the EntityServer Listener. This handler is used to handle 
	 * messages sent to this server.
	 */
	private LinkedList<EntityConnectionHandler> handlers;
	
	/**
	 * Basic constructor.
	 */
	public EntityConnection()
	{
		super();
		this.handlers = new LinkedList<EntityConnectionHandler>();
	}

	/**
	 * Holds the
	 * @return
	 */
	protected LinkedList<EntityConnectionHandler> getEntityListeners()
	{
		return this.handlers;
	}
	
	
	/**
	 * Adds a handler for this EntityServer.
	 * @param handler
	 */
	public void addHandler(EntityConnectionHandler listener)
	{
		this.getEntityListeners().add(listener);
	}

	/**
	 * Will notify all of the Handlers that are associated with this Connection.
	 * @return the number of listeners notified.
	 */
	public int notifyListeners(String message)
	{
		int i;
		LinkedList<EntityConnectionHandler> connectionHandlers = this.getEntityListeners();
		int length = connectionHandlers.size();
		for ( i = 0; i < length ; i++)
		{
			
			EntityConnectionHandler connection = connectionHandlers.getFirst();
			connection.handleMessage(message);
		}
		return i;
	}

	/**
	 * Will remove the passed listener if it is part of the listeners
	 * for this class.
	 * @param listener
	 */
	public void removeHandler(EntityConnectionHandler listener)
	{
		// TODO Auto-generated method stub
		this.getEntityListeners().remove(listener);
	}
}

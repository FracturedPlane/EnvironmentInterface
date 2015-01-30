package aiInterface.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;

import aiInterface.data.DataContainer;
import aiInterface.handler.AgentConnectionHandler;
import aiInterface.intreface.Interfacer;

/**
 * This Class is designed to make and handle a connection between the Agent in the environment
 * and the agent. Handling the percepts from the Agent and sending them to the agent and responding 
 * back to the environment with the action the agent chooses.
 * @author glen
 * 
 */

public abstract class AgentConnection extends Connection
{

	/*
	 * Holds the AgentServer Listener. This handler is used to handle 
	 * messages sent to this server.
	 */
	private LinkedList<AgentConnectionHandler> handlers;
	
	/**
	 * Basic constructor 
	 */
	public AgentConnection()
	{
		this.handlers = new LinkedList<AgentConnectionHandler>();
	}

	/**
	 * Protected access to the list of AgentListeners
	 * @return
	 */
	protected LinkedList<AgentConnectionHandler> getAgentListeners()
	{
		return this.handlers;
	}
	
	
	/**
	 * Adds a handler for this AgentServer.
	 * @param handler
	 */
	public void addHandler(AgentConnectionHandler listener)
	{
		this.getAgentListeners().add(listener);
	}

	/**
	 * Will notify all of the Handlers that are associated with this Connection.
	 * @return the number of listeners notified.
	 */
	public int notifyListeners(String message)
	{
		int i;
		LinkedList<AgentConnectionHandler> connectionHandlers = this.getAgentListeners();
		int length = connectionHandlers.size();
		for ( i = 0; i < length ; i++)
		{
			
			AgentConnectionHandler connection = connectionHandlers.getFirst();
			connection.handleMessage(message);
		}
		return i;
	}
	
	/**
	 * Returns this Connections handlers
	 * @return
	 */
	public HashSet<AgentConnectionHandler> getHandlers()
	{
		HashSet<AgentConnectionHandler> handlers = new HashSet<AgentConnectionHandler>();
		handlers.addAll(this.getAgentListeners());
		return handlers;
	}

	/**
	 * Will search and remove the listener if it is the list of handlers
	 * for this Connection.
	 * @param listener
	 */
	public void removeHandler(AgentConnectionHandler listener)
	{
		// TODO Auto-generated method stub
		this.getAgentListeners().remove(listener);
		
	}

}

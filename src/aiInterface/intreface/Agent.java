package aiInterface.intreface;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import eis.exceptions.RelationException;

import aiInterface.connection.AgentConnection;
import aiInterface.handler.AgentConnectionHandler;
import aiInterface.handler.AgentHandler;
import aiInterface.listener.Listener;

/**
 * This class represents the abilities of the interface.
 * 
 * An Agent should have its own connection that can be accessed though the Agent
 * As well a list of associatedEntities. When this Agent receives a message it
 * should forward/notify all of the associated Entitys.
 * 
 * @author Glen Berseth
 *
 */
public abstract class Agent implements Interfacer
{

	private String name;
	private LinkedList<Entity> associatedEntities;
	private AgentConnection connection;
	
	public Agent(String name)
	{
		this(name, null);

	}
	
	public Agent(String name, AgentConnection connection)
	{
		super();
		this.setName(name);
		this.setConnection(connection);
		this.associatedEntities = new LinkedList<Entity>();
	}
	
	/**
	 * Used to set the connection this Agent is going to use
	 * @param connection
	 */
	public void setConnection(AgentConnection connection)
	{
		this.connection = connection;
	}
	
	public AgentConnection getConnection()
	{
		return this.connection;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = new String(name);
	}
	
	public boolean equals(Object object)
	{
		if ( this.getClass() == object.getClass() )
		{
			Agent other = (Agent) object;
			if ( this.getName().equals(other.getName()))
			{
				return true;
			}
			
		}
		return false;
	}

	/**
	 * Implemented for the sake of the EIS
	 * @return
	 */
	public HashSet<AgentConnectionHandler> getListeners()
	{
		HashSet<AgentConnectionHandler> newListeners = new HashSet<AgentConnectionHandler>();
		
		for (AgentConnectionHandler listener : this.connection.getHandlers() )
		{
			newListeners.add((AgentConnectionHandler) listener);
		}
		
		return newListeners;
	}
	
	
	public void addListener(AgentConnectionHandler agentConnectionHandler)
	{
		this.connection.addHandler(agentConnectionHandler);
	}
	
	public void removeListener( AgentConnectionHandler listener )
	{
		this.connection.removeHandler(listener);
	}
	
	public abstract String getMessage();


	public abstract int send(String message);
	
	public void associateEntity(Entity entity)
	{
		this.associatedEntities.add(entity);
	}
	
	public boolean disassociateEntity( Entity entity ) throws RelationException
	{
		if ( ! this.associatedEntities.contains(entity) )
		{
			throw new RelationException("The Entity " + entity.getIdentifier() +
					"Is not associated with this Entity.");
		}
		
		return this.associatedEntities.remove(entity);
	}
	
	public LinkedList<Entity> getAssociatedEntities()
	{
		return this.associatedEntities;
	}
}

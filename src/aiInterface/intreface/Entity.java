package aiInterface.intreface;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import eis.exceptions.RelationException;

import aiInterface.connection.EntityConnection;
import aiInterface.handler.AgentHandler;
import aiInterface.handler.ConnectionHandler;
import aiInterface.handler.EntityConnectionHandler;
import aiInterface.listener.Listener;

/**
 *  This class represents the abilities of the interface.
 * 
 * An Entity should have its own connection that can be accessed though the Entity
 * As well a list of associatedAgents. When this Entity receives a message it
 * should forward/notify all of the associated Agents.
 * 
 * @author Glen Berseth
 *
 */
public abstract class Entity implements Interfacer
{

	
	private String identifier;
	private LinkedList<Agent> associatedAgents;
	private EntityConnection connection;
	
	public Entity(String identifier)
	{
		this.setIdentifier(identifier);
		this.associatedAgents = new LinkedList<Agent>();
	}
	
	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}
	
	public EntityConnection getConnection()
	{
		return this.connection;
	}

	public void setConnection(EntityConnection entityConnection)
	{
		this.connection = entityConnection;
		
	}
	
	public String getIdentifier()
	{
		return this.identifier;
	}

	
	public void addListener(EntityConnectionHandler listener)
	{
		this.getConnection().addHandler(listener);
	}
	
	public void removeListener( EntityConnectionHandler listener )
	{
		this.getConnection().removeHandler(listener);
	}
	
	public abstract int send(String message); 


	public abstract String getMessage();
	
	public void associateAgent(Agent agent)
	{
		this.associatedAgents.add(agent);
	}
	
	public boolean disassociateAgent(Agent agent) throws RelationException
	{
		if ( ! this.associatedAgents.contains(agent) )
		{
			throw new RelationException("The Entity " + agent.getName() +
					"Is not associated with this Entity.");
		}
		
		return this.associatedAgents.remove(agent);
	}

	public void removeHandler(EntityConnectionHandler entityHandler)
	{
		// TODO Auto-generated method stub
		this.removeListener(entityHandler);
		
	}
	
	public LinkedList<Agent> getAssociatedAgents()
	{
		return this.associatedAgents;
	}
}

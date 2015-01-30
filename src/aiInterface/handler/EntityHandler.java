package aiInterface.handler;

import java.io.IOException;

import aiInterface.intreface.Entity;
import aiInterface.intreface.Interfacer;
import aiInterface.connection.AgentConnection;
import aiInterface.connection.EntityConnection;
import aiInterface.data.Action;
import aiInterface.data.DataContainer;

public interface EntityHandler
{
	
	
	/**
	 * Handles the Action that is going to be sent to the Entity
	 * 
	 * @param entity the entity that the action is going to be sent to
	 * @param action the Action to be sent.
	 */
	
	public void handleData(Interfacer entity, DataContainer action);
	
	public void handleMessage(AgentConnection agentConnection, DataContainer dataContainer);
	
	public void handleMessage(AgentConnection agentConnection, String message);
	
	public void handleAction( Entity entity, Action action);

}

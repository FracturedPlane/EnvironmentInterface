package aiInterface.handler;

import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;

import aiInterface.connection.Connection;
import aiInterface.data.DataContainer;
import aiInterface.data.Percept;
import aiInterface.intreface.Agent;
import aiInterface.intreface.Entity;



public class EntityConnectionHandler implements ConnectionHandler
{
	
	private Entity entity;


	public void handleMessage(String message)
	{
		for ( Agent agent : this.getEntity().getAssociatedAgents() )
		{
			agent.send(message);
		}
	}
	
	public void handleData( DataContainer container )
	{
		for ( Agent agent : this.getEntity().getAssociatedAgents() )
		{
			try
			{
				agent.send(container);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Entity getEntity() 
	{
		return entity;
	}

	public void setEntity(Entity entity) 
	{
		this.entity = entity;
	}

	
}

package aiInterface.handler;

import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;

import aiInterface.connection.Connection;
import aiInterface.data.DataContainer;
import aiInterface.data.Percept;
import aiInterface.intreface.Agent;
import aiInterface.intreface.Entity;

public class AgentConnectionHandler implements ConnectionHandler 
{

	private Agent agent;
	


	public void handleMessage(String message)
	{
		
		LinkedList<Entity> entities = this.getAgent().getAssociatedEntities();
		for ( Entity entity : entities)
		{
			entity.send(message);
		}
	}
	
	public void handleData(DataContainer container)
	{
		LinkedList<Entity> entities = this.getAgent().getAssociatedEntities();
		for ( Entity entity : entities)
		{
			try
			{
				entity.send(container);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Agent getAgent()
	{
		return this.agent;
	}
	
	public void setAgent(Agent agent)
	{
		this.agent = agent;
	}

}

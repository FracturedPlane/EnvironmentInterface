package aiInterface.handler;


import aiInterface.connection.EntityConnection;
import aiInterface.data.DataContainer;
import aiInterface.data.Percept;
import aiInterface.intreface.Agent;
import aiInterface.intreface.Interfacer;

public interface AgentHandler
{
	
	public void handleData( Interfacer interfacer, DataContainer data );
	
	public void handleMessage(EntityConnection entityConnection, DataContainer dataContainer);

	public void handlePercept(Agent agent, Percept percept);

}

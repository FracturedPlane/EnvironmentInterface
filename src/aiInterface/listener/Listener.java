package aiInterface.listener;

import java.util.Collection;
import java.util.HashSet;

import aiInterface.handler.AgentHandler;
import aiInterface.handler.ConnectionHandler;
import aiInterface.handler.EntityConnectionHandler;


public interface Listener
{

	public HashSet<Collection<? extends ConnectionHandler>> getListeners();
	
	public void setListeners(HashSet<Collection<? extends ConnectionHandler>> listeners);
	
	public void addListener(AgentHandler listener);
	
	public void removeListener( AgentHandler listener );
	
}

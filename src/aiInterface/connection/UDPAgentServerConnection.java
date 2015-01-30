package aiInterface.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import aiInterface.data.GologAction;
import aiInterface.handler.AgentHandler;
import aiInterface.testing.client.TestingUtil;

/**
 * This class does not do much except help differentiate between the single
 * UDPBot connections for each agent, entity connection and what this class is for it the
 * UDPBotServer connection where this class will be used to make queries
 * to the Agent server and get responses for creating new UDPBotConnections.
 * 
 * i.e. this class is used just for querying the Agent server and the 
 * response is used to assign an agent on the Agent server to a UDPAgentConnection.
 * @author glenpb
 *
 */
public class UDPAgentServerConnection extends UDPAgentConnection
{

	public UDPAgentServerConnection()
	{
		super();
	}
	
	public UDPAgentServerConnection(AgentHandler listener, String host, int port) throws UnknownHostException, IOException
	{
		super(listener, host, port);
	}
	
	public UDPAgentServerConnection(AgentHandler handler, InetAddress address, int port, int receiveTimeout) throws SocketException
	{
		super(handler, address, port, receiveTimeout);
	}

	
	/**
	 * Start a waiting tread for this Connection to listen for incoming messages from the 
	 * AgentServer and then handle the messages appropriately. 
	 */
	public void run()
	{
		String message = new String("");
		
		while ( !message.contains((TestingUtil.CLOSE_CONNECTION_FLAG)) )
		{
			
			try 
			{
				message = this.getMessage();
				
				GologAction gologAction = new GologAction(message);
				System.out.println("gologAction = " + gologAction.getSource());
				
				this.getHandler().handleMessage(this.getBotConnection(), gologAction);
				
			} catch (IOException e) 
			{
				e.printStackTrace();
				continue;
			}
			
			// System.out.println("\nclient>" + message);
			
			
		}
	}

}

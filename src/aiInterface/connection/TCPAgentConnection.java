package aiInterface.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import aiInterface.connection.util.TCPNIOConnection;
import aiInterface.data.DataContainer;
import aiInterface.data.conversion.Converter;
import aiInterface.handler.AgentConnectionHandler;
import aiInterface.testing.client.TestingUtil;

/**
 * Fully usuable example TCP connection to a Agent system.
 * @author glen
 *
 */
public class TCPAgentConnection extends AgentConnection implements Runnable
{

	
	private TCPNIOConnection agentServerConnection;
	private ServerSocketChannel serverSocketChannel;

	/*
	 * Holds the agentConnection. Used to send messages to the Agent.
	 */
	private EntityConnection entityConnection;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private TCPAgentConnection()
	{
		
	}
	
	/**
	 * Constructor for creating a TCPAgentConnection
	 * @param socketNumber
	 * @param queueSize
	 * @param listener
	 * @throws IOException
	 */
	public TCPAgentConnection(int socketNumber, int queueSize,	AgentConnectionHandler listener) throws IOException
	{
		// TODO Auto-generated constructor stub
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(socketNumber), queueSize);
		this.addHandler(listener);
	}
	
	@Override
	/**
	 * Will block and return a message when one is available.
	 */
	public String getMessage() throws IOException 
	{
		// TODO Auto-generated method stub
		
		return this.getAgentServerConnection().getMessage();
	}

	@Override
	/**
	 * A new line character needs to be put on the end of messages
	 * sent to the IndiGolog Agent.
	 */
	public int send(String message) throws IOException 
	{
		// TODO Auto-generated method stub
		return this.getAgentServerConnection().send(message);
	}


	@Override
	/**
	 * Initiates this AgentConnection to wait for incoming connections. To
	 * accept them, receive messages and then close the connection when a
	 * message says so.
	 */
	public void run()
	{

		String message = "";

		try
		{
			/*
			 * Accepts a connect from the Agent environment
			 */
			this.setAgentServerConnection( new TCPNIOConnection (this.serverSocketChannel.accept()));
			System.out.println("Connection made to Agent " + 
					this.getAgentServerConnection().getConnection().socket().getLocalPort() + " " +
					this.getAgentServerConnection().getConnection().socket().getInetAddress());
			// Creates a log file to log the messages from the entities.
			// fileWriter = LogFileUtil.createLogWriterFile((LogFileUtil.createLogFile(LogFileUtil.LOG_FILES_DIR, CLASS_NAME, "." + LogFileUtil.LOG_FILES_DIR)));
			// this.send("capture\n");
			
			this.acknowledgeConnection();
			/*
			 * While message != stop
			 */
			while( !message.contains(TestingUtil.CLOSE_CONNECTION_FLAG))
			{
				/*
				 * After accepting a connection it will call receive to block and
				 * wait for a message from the entity.
				 * 
				 * This will wait for a full message. and only return when a proper
				 * full message is found.
				 */
				message = this.getMessage();
				
				DataContainer container = this.getDataContainer().convertToDataContainer(message);
				// logs the message.
				// fileWriter.write(Calendar.getInstance().getTime() + ": " + message + "\n");
				// fileWriter.flush();
				
				
				// Creates a new request to be sent to the AgentServer.
				// XMLPercept percept = new XMLPercept(message);
				// percept.setSource(message);
				
				/*
				 * Sends a message to the handler to handle the handle
				 * (send) the request to the Agent Server.
				 */
				// this.getAgentConnection().send(percept);
				// XMLPercept xmlPercept = new XMLPercept(message);
				
				this.notifyListeners(container);
				// this.getHandler().handleMessage(this.getAgentConnection(), message);
			}
			// fileWriter.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			TestingUtil.printExceptionInfo(e);
			e.printStackTrace();
		}
		System.out.println("Agent connection done");
	}

	private int notifyListeners(DataContainer container)
	{
		// TODO Auto-generated method stub
		int i;
		LinkedList<AgentConnectionHandler> connectionHandlers = this.getAgentListeners();
		int length = connectionHandlers.size();
		for ( i = 0; i < length ; i++)
		{
			
			AgentConnectionHandler connection = connectionHandlers.getFirst();
			connection.handleData(container);
		}
		return i;
		
	}

	public TCPNIOConnection getAgentServerConnection() 
	{
		return this.agentServerConnection;
	}

	public void setAgentServerConnection(TCPNIOConnection connection) 
	{
		this.agentServerConnection = connection;
	}

	public ServerSocketChannel getServerSocketChannel() 
	{
		return serverSocketChannel;
		
	}

	public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) 
	{
		this.serverSocketChannel = serverSocketChannel;
	}

	public EntityConnection getEntityConnection()
	{
		return entityConnection;
	}

	public void setEntityConnection(EntityConnection entityConnection) 
	{
		this.entityConnection = entityConnection;
	}

	/**
	 * Should be able to send any kind of data container that that converter
	 * class supports
	 */
	@Override
	public int send(DataContainer dataContainer) throws IOException 
	{
		// TODO Auto-generated method stub
		Converter convert = this.getConverter();
		DataContainer contain = convert.convert(dataContainer);
		return this.send(contain.getSource());

	}
	
}

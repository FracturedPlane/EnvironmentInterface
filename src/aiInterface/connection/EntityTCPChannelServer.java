package aiInterface.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.Calendar;
import java.util.LinkedList;

import aiInterface.connection.util.TCPNIOConnection;
import aiInterface.data.DataContainer;
import aiInterface.data.conversion.Converter;
import aiInterface.handler.EntityConnectionHandler;
import aiInterface.logging.LogFileUtil;
import aiInterface.testing.client.TestingUtil;

/**
 * Fully usable example of what could be used for type of connection
 * @author glen
 *
 */
public class EntityTCPChannelServer extends EntityConnection implements Runnable
{
	
	public static final String CLASS_NAME = "EntityTCPChannelServer";
	private static final String PROP_LOG_FILE_DIR = "logFileDirectory";
	/*
	 * Used to hold the connection to the ServerSocketChannel for reading and
	 * writing to the Socket.
	 */
	private ServerSocketChannel serverSocketChannel;
	private TCPNIOConnection entityServerConnection;

	/**
	 * Basic Constructor
	 */
	public EntityTCPChannelServer()
	{
		super();
	}

	/**
	 * Construct and instance of the EntityTCPChannelServer.
	 * @param socketNumber int to select the socket number to be listeing on.
	 * @param queueSize - The number of connections allowed to wait in a queue 
	 * they send a message.
	 * @param handler - the handler/Handler for this server.
	 * @throws IOException
	 */
	public EntityTCPChannelServer(int socketNumber, int queueSize,	EntityConnectionHandler listener) throws IOException
	{
		// TODO Auto-generated constructor stub
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(socketNumber), queueSize);
		this.addHandler(listener);
		
	}


	/**
	 * Gets the SocketChannel
	 * @return
	 */
	public TCPNIOConnection getEntityServerConnection()
	{
		return entityServerConnection;
	}


	/**
	 * Used to set the SocketChannel Connection.
	 * @param entityServerConnection
	 */
	public void setEntityServerConnection(TCPNIOConnection entityServerConnection) 
	{
		this.entityServerConnection = entityServerConnection;
	}
	


	/**
	 * Initiates this EntityConnection to wait for incoming connections. To
	 * accept them, receive messages and then close the connection when a
	 * message says so.
	 */
	public void run()
	{

		String message = "";
		BufferedWriter fileWriter = null;

		try
		{
			/*
			 * Accepts a connect from the Entity environment
			 */
			this.setEntityServerConnection( new TCPNIOConnection (this.serverSocketChannel.accept()));
			System.out.println("Connection made to Entity " + this.getEntityServerConnection().getConnection().socket().getLocalPort() + " " + this.getEntityServerConnection().getConnection().socket().getInetAddress());
			// Creates a log file to log the messages from the entities.
			// fileWriter = LogFileUtil.createLogWriterFile((LogFileUtil.createLogFile(LogFileUtil.LOG_FILES_DIR, CLASS_NAME, "." + LogFileUtil.LOG_FILES_DIR)));
			
			/*
			 * While message != stop
			 */
			while (!message.contains(TestingUtil.CLOSE_CONNECTION_FLAG))
			{
				/*
				 * After accepting a connection it will call receive to block and
				 * wait for a message from the entity.
				 * 
				 * This will wait for a full message. and only return when a proper
				 * full message is found.
				 */
				message = this.getMessage();
				// logs the message.
				// fileWriter.write(Calendar.getInstance().getTime() + ": " + message + "\n");
				// fileWriter.flush();
				
				
				// Creates a new request to be sent to the AgentServer.
				DataContainer percept = this.getDataContainer().convertToDataContainer(message);
				// percept.setSource(message);
				
				/*
				 * Sends a message to the handler to handle the handle
				 * (send) the request to the Agent Server.
				 */
				// this.getAgentConnection().send(percept);
				// XMLPercept xmlPercept = new XMLPercept(message);
				
				this.notifyListeners(percept);
				// this.getHandler().handleMessage(this.getAgentConnection(), message);
			}
			// fileWriter.close();
		}
		catch (IOException e)
		{
			TestingUtil.printExceptionInfo(e);
		}
		System.out.println("EntityConnection Done");
	}

	/**
	 * Will notifiy all of the listeners assigned to this Connection.
	 * @param percept
	 * @return
	 */
	private int notifyListeners(DataContainer percept)
	{
		// TODO Auto-generated method stub
		int i;
		LinkedList<EntityConnectionHandler> connectionHandlers = this.getEntityListeners();
		int length = connectionHandlers.size();
		for ( i = 0; i < length ; i++)
		{
			
			EntityConnectionHandler connection = connectionHandlers.getFirst();
			connection.handleData(percept);
		}
		return i;
		
	}

	/**
	 * 
	 * 	Attempts to retrieve a full message.
	 * If no full message is available then <code>null</code> will be returned.
	 * 
	 * @throws IOException 
	 */
	@Override
	public String getMessage() throws IOException 
	{
		return this.getEntityServerConnection().getMessage();
	}
	
	/**
	 * Sends a message out to the client entity server.
	 * Returns the number of bytes written.
	 */
	public int send(String message) throws IOException 
	{
		
		// Won't crash if Entity is not ready.
		if (this.getEntityServerConnection() != null)
		{
			return this.getEntityServerConnection().send(message);
		}
		return 0;
	}

	@Override
	/**
	 * Will attempt to send the passed DataContainer.
	 */
	public int send(DataContainer dataContainer) throws IOException 
	{
		Converter convert = this.getConverter();
		DataContainer contain = convert.convert(dataContainer);
		return this.send(contain.getSource());
		// TODO Auto-generated method stub
	}
	
}

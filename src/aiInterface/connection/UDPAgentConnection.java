package aiInterface.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import aiInterface.data.Data;
import aiInterface.data.DataContainer;
import aiInterface.data.GologAction;
// import aiInterface.data.PrologPercept;
// import aiInterface.data.XMLPercept;
import aiInterface.environment.UnrealEI;
import aiInterface.handler.AgentHandler;
import aiInterface.testing.client.TestingUtil;


public class UDPAgentConnection extends AgentConnection implements Runnable
{

	private InetAddress address;
	private DatagramSocket socket = null;
	private DatagramPacket packet;
	private AgentHandler listener;
	private int port;
	private int receiveMessageLength = 1024;
	private EntityConnection entityConnection;
	
	public UDPAgentConnection()
	{
		super();
	}
	
	public UDPAgentConnection(AgentHandler listener, String host, int port)
			throws UnknownHostException, IOException
	{
		this(listener, InetAddress.getByName(host), port, 0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates a UDPAgentConnection.
	 * 
	 * @param listener The connection listener
	 * @param address - The address of the host to connect to 
	 * @param port - The port to connect to and the Host
	 * @param receiveTimeout - Sets the amount of time the receive() call will
	 * block before it gives up. 
	 * @throws SocketException
	 */
	public UDPAgentConnection(AgentHandler listener, InetAddress address, int port, int receiveTimeout) throws SocketException
	{
	
		this.setHandler(listener);
		this.setAddress(address);
		this.setPort(port);
	
		this.setSocket(new DatagramSocket());
		this.getSocket().setSoTimeout(receiveTimeout);
	}
	
	public void setAddress(InetAddress addres)
	{
		this.address = addres;
	}


	public String getMessage() throws IOException
	{
		return new String(receive(this.receiveMessageLength));
	}
	
	
	/**
	 * This method can block for a while.
	 */
	public byte[] receive(int length) throws IOException
	{
		// TODO Auto-generated method stub
		
		byte[] data = new byte[length];
		DatagramPacket receivePacket = new DatagramPacket(data, data.length, this.address, this.port);
		this.getSocket().receive(receivePacket);
		System.out.println("recieved a packet from " + receivePacket.getAddress() + " " + receivePacket.getPort());
		return receivePacket.getData();
	}

	/**
	 * Always returns 0.
	 */
	public int send(String message)
	{
		// TODO Auto-generated method stub
	
		byte[] data = null;
		try
		{
			data = message.getBytes(UnrealEI.CHARSET);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		packet = new DatagramPacket(data, data.length, this.address, this.port);
		try
		{
			socket.send(packet);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	
	/*
	/**
	 * Sends the percept to the AgentConnection.
	 * @param percept
	 * @return
	 */
	/*
	public int send(DataContainer percept)
	{
		Data data = null;
		PrologPercept prologPercept = new PrologPercept();
		data = prologPercept.Convert(percept, prologPercept, prologPercept);
		
		System.out.println(" data.getSource = " + data.getSource());
		return send(data.getSource());		
	}
	
	public int send(XMLPercept percept)
	{
		Data data = null;
		PrologPercept prologPercept = new PrologPercept();
		data = prologPercept.Convert(percept, prologPercept, prologPercept);
		return send(data.getSource());		
	}
	*/
	
	public DatagramSocket getSocket() 
	{
		return socket;
	}

	public void setSocket(DatagramSocket socket) 
	{
		this.socket = socket;
	}

	public DatagramPacket getPacket()
	{
		return packet;
	}

	public void setPacket(DatagramPacket packet)
	{
		this.packet = packet;
	}

	public AgentHandler getHandler() 
	{
		return this.listener;
	}

	public void setHandler(AgentHandler listener)
	{
		this.listener = listener;
	}

	public int getPort() 
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * Start a waiting tread for this Connection to listen for incoming messages from the 
	 * AgentServer and then handle the messages appropriately. 
	 */
	public void run()
	{
		// TODO Auto-generated method stub
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
			}
			
			System.out.println("\nclient>" + message);
			
			
		}
	}

	public void setBotConnection(EntityConnection agentConnection) throws IOException
	{
		this.entityConnection = agentConnection;
	}
	
	public EntityConnection getBotConnection()
	{
		
		return this.entityConnection;
	}

	@Override
	public int notifyListeners(String message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int send(DataContainer dataContainer) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}

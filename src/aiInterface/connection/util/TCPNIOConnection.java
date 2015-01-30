package aiInterface.connection.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import aiInterface.connection.EntityConnection;
import aiInterface.data.DataContainer;
import aiInterface.environment.UnrealEI;
import aiInterface.util.MessageFraming;

/**
 * This is an implementation of a TCPNIO host connection.
 * @author glen
 *
 */
public class TCPNIOConnection extends EntityConnection
{
	static Logger logger = Logger.getLogger("TCPNioConnection.class");
	private static final String CLASS_NAME = "TCPNIOConnectionUtil";
	private static final int BUFFER_CAPACITY = 256;
	private String buffer;
	private SocketChannel Connection;
	
	/**
	 * Basic constructor
	 * Just creates the buffer for this instance
	 * @throws UnsupportedEncodingException
	 */
	public TCPNIOConnection() throws UnsupportedEncodingException
	{
		super();
		byte[] temp = new byte[10];
		this.buffer = new String(temp, UnrealEI.CHARSET);
	}
	
	/**
	 * Create a host connection according to the passed SocketChannel
	 * @param channel
	 * @throws UnsupportedEncodingException
	 */
	public TCPNIOConnection(SocketChannel channel) throws UnsupportedEncodingException
	{
		this();
		this.setConnection(channel);
	}
	
	/**
	 * Block and returns when a full message is available in the buffer.
	 */
	public String getMessage() throws IOException 
	{
		byte buff[] = new byte[20];
		if ( this.buffer == null)
		{
			this.buffer = new String(buff, UnrealEI.CHARSET);
		}
		String out = new String("");
		int loopLimit = 5;
		// FileWriter fileWriter = LogFileUtil.createLogFileWriter((LogFileUtil.createLogFile(LogFileUtil.LOG_FILES_DIR, CLASS_NAME + ".getMessage()", "." + LogFileUtil.LOG_FILES_DIR)));
		
		
		for ( int i = 0; i < loopLimit ; i++ )
		{
			try
			{
				this.buffer = new String(buffer + this.recieve());
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//fileWriter.write(Calendar.getInstance().getTime() + ": " + buffer + "\n");
			//fileWriter.flush();
			/*
			buffer = new String( MessageFraming.screenCharacters(buffer));
			fileWriter.write(Calendar.getInstance().getTime() + "after screen: " + buffer + "\n");
			fileWriter.flush();
			*/
			/*
			 * For some reason this is not evaluating to true when it should be.
			 */
			if ( MessageFraming.containsMessageFrame(buffer, MessageFraming.START_FRAME, MessageFraming.END_FRAME) )
			{
				out = new String(MessageFraming.parseOutMessage(buffer));
				this.buffer = new String( this.buffer.substring(this.buffer.indexOf(MessageFraming.END_FRAME)
						+ MessageFraming.END_FRAME.length()));
				return out;
			}
			
		}
		// fileWriter.close();
		return out;
	}
	
	/**
	 * Created for testing purposes.
	 * Returns when any message is available.
	 * @return
	 */
	public String getMessageWithoutFrame()
	{
		String out = "";
		try
		{
			out = new  String( this.recieve() );
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Does all of the hard work of fetching data from the connection,
	 * converting it and making sure it is well formed.
	 * Then returns a String representation of the data received.
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String recieve() throws IOException, InterruptedException
	{
		// TODO Auto-generated method stub
		ByteBuffer inbuffer = ByteBuffer.allocate(BUFFER_CAPACITY);
		byte buffer[] = new byte[BUFFER_CAPACITY];
		// inbuffer.order(ByteOrder.LITTLE_ENDIAN);
		// BufferedWriter fileWriter = LogFileUtil.createLogWriterFile((LogFileUtil.createLogFile(LogFileUtil.LOG_FILES_DIR, CLASS_NAME + ".receive()", "." + LogFileUtil.LOG_FILES_DIR)));
		
		int readCount = 0;

		readCount = this.getConnection().read(inbuffer);
		// buffer = inbuffer.array();
		
		/*
		 * Flipping seems to work. 
		 */
		// inbuffer.flip();
		// System.out.println(".recieve() inbuffer " + inbuffer.toString());
		// System.out.println("inbuffer " + inbuffer.asCharBuffer());
		// System.out.println("inbuffer order" + inbuffer.order().toString());
		// System.out.println("buffer = " + new String(buffer));
		// System.out.println("buffer "buffer.)
		/*
		inbuffer.get(buffer);
		*/
		/*
		 * does not actually clear the buffer. It will reset the position, mark and limit for the buffer.
		 */
		inbuffer.clear();
		int i;
		for ( i = 0; inbuffer.hasRemaining() && i < (BUFFER_CAPACITY) && i <= readCount + 1; i++ )
		{
			//int pos = inbuffer.arrayOffset() + i;
			buffer[i] = inbuffer.get();
			// System.out.print((char)buffer[i] + "" + ", @possition = " + inbuffer.position());
		}
		
		// System.out.println("\n");
		
		String out = new String(buffer, UnrealEI.CHARSET);
		
		out = new String (out.substring(0, i));
		// System.out.println("i = " + i);
		logger.debug("getMesgage, out = " + out);
		// fileWriter.write(Calendar.getInstance().getTime() + ": " + out + "\n");
		// fileWriter.flush();
		// fileWriter.close();
		// Thread.sleep(500);
		if ( i < 10 )
		{
			return new String("");
		}
		return out;
	}
	
	private void setConnection(SocketChannel Connection)
	{
		this.Connection = Connection;
	}
	
	public SocketChannel getConnection()
	{
		return this.Connection;
	}
	
	/**
	 * This method will send the exact message without appending the 
	 * framing around the data.
	 */
	public int send(String message) throws IOException 
	{
		logger.debug("About to send " + message + "|");
		int out = 0;
		// message = new String (MessageFraming.frameString(message));
		ByteBuffer buffer = ByteBuffer.allocate(message.length());
		byte bufferin[] = null;
		bufferin = message.getBytes(UnrealEI.CHARSET);
		buffer.put(bufferin, 0 , bufferin.length);
		// As found before clear() only resents positioning pointers in the ByteBuffer.
		buffer.clear();
		try
		{
			out = this.getConnection().write(buffer);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Done sending " + message + "|");
		return out;
	}

	@Override
	public int send(DataContainer dataContainer) throws IOException 
	{
		// TODO Auto-generated method stub
		return 0;
	}
}

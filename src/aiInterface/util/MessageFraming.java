package aiInterface.util;

public class MessageFraming
{
	
	//public static final String START_FRAME = "<message5>";
	public static final String START_FRAME = "mESSAGE5";
	// public static final String END_FRAME = "</message5>";
	public static final String END_FRAME = "messagE5";
	public static final String MESSAGE_MATCHING_REGEX = ".*" + START_FRAME +".*" + END_FRAME + ".*";
	
	public static String frameString(String message)
	{
		return START_FRAME + message + END_FRAME;
	}
	
	/**
	 * This method is used to break apart a string be the delimiter messageDelimiter
	 * @param message THe message to parse
	 * @param messageDelimiter used to signify the end of a message.
	 * @return
	 */
	public static boolean containsMessageFrame(String message, String messageDelimiter)
	{
		return message.contains(messageDelimiter);
	}
	
	public static boolean containsMessageFrame( String message, String frameStart, String frameEnd)
	{
		boolean out = false;
		int startIndex = message.indexOf(frameStart);
		if ( startIndex < 0 )
		{
			return out;
		}
		int endIndex = message.indexOf(frameEnd, startIndex);
		
		if ( endIndex > 0)
		{
			out = true;
		}
		
		
		return out;
	}
	
	
	
	public static String parseOutMessage(String buffer)
	{
		return new String (buffer.substring(buffer.indexOf(MessageFraming.START_FRAME) + MessageFraming.START_FRAME.length(),
				buffer.indexOf(MessageFraming.END_FRAME, buffer.indexOf(MessageFraming.START_FRAME))));
	}
	
	public static String parseOutMessage(String message, String delimiter)
	{
		return message.split(delimiter)[0];
	}
	
	public static String screenCharacters(String old)
	{
		String allowedChars = new String("?\" -,.<>/0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\t\n");
		String out = new String();
		for ( int i=0; i < old.length(); i++)
		{
			if ( allowedChars.indexOf(old.charAt(i)) >= 0 ) 
			{
				out = new String( out + old.charAt(i));
			}
		}
		return out;
	}
	
	/**
	 * Removes newline character, tab characters and carrage return
	 * characters from the string.
	 * @param message
	 * @return
	 */
	public static String removeWhitespace(String message)
	{
		String stringOut = new String("");
		String regex1 = "[\n\t\r]";
		stringOut = new String(message.replaceAll(regex1, ""));
		return stringOut;
	}
}

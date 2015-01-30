package aiInterface.logging;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AIIEISLogger 
{
	static Logger logger = Logger.getLogger("FileAppenderExample.class");
	static String logpropsFile = "properties/log4j.properties";
	
	public AIIEISLogger() throws IOException
	{
		 PropertyConfigurator.configure(logpropsFile);

	}
	
	public static Logger getLogger(String classname)
	{
		PropertyConfigurator.configure(logpropsFile);
		return Logger.getLogger(classname);
	}
	
	  public static void main(String[] args)throws Exception 
	  {
		  AIIEISLogger logy = new AIIEISLogger();
		  logy.info("Log has been appended to your output.txt");  
		  logy.info("See your output.txt"); 
	  }
	  
	  public void info(String info)
	  {
		  logger.info(info);
	  }
	  
	  public void warn(String warnMessage)
	  {
		  logger.warn(warnMessage);
	  }
	  
	  public void debug(String debugMessage)
	  {
		  logger.debug(debugMessage);
	  }
}

package aiInterface.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This class is used to make logging for the AIinterface simpler
 * 
 * There is possibly a lot of IO going in and out through the Interface
 * to be able to tack IO bugs everything between each agent and entity
 * will need to be logged to make sure the message are being passed properly.
 * 
 * @author GlenB
 *
 */

public class AIinterfaceLogger extends Logger
{

	protected AIinterfaceLogger(String name, String resourceBundleName)
	{
		super(name, resourceBundleName);
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLogger(String classname)
	{
		Logger logger = Logger.getLogger(classname);
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(classname, 1000000, 1);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.addHandler(fileHandler);
		
		return Logger.getLogger(classname);
	}

}

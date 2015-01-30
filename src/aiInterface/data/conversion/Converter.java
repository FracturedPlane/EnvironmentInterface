package aiInterface.data.conversion;

import aiInterface.data.Data;
import aiInterface.data.DataContainer;

/**
 * This is an interface to support converting from one
 * Data to another. 
 * 
 * The idea is to support many kinds of Data conversion.
 * 
 * For example Converting from an XML format to a prolog
 * list structure and back. 
 * 
 * 
 * @author GlenB
 *
 */
public interface Converter 
{
	
	/**
	 * This is the method is the interface
	 * 
	 * To add support for different Conversions simply create a method in the
	 * class that implements this interface and the type it takes will be a 
	 * Descendant of DataContainer.
	 * @param dataContainer
	 * @return
	 */
	public DataContainer convert(DataContainer dataContainer);
	
	
}

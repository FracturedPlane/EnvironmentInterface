package aiInterface.testing.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestingUtil
{

	
	public static final String GLENS_NEMESIS_XMLFILE_LOCATION = "XML/GlensNemesis.xml";
	
	public static final String CLOSE_CONNECTION_FLAG = "CloseConnection";
	
		
	
	public static Document parseXmlString(String xmlString) throws ParserConfigurationException, SAXException, IOException
	{
		//get the factory
		
		Document out = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
		
	
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Create InputStream source for parser.
			 InputSource inputSource = new InputSource( new StringReader( xmlString ) );
			//parse using builder to get DOM representation of the XML file

			out = db.parse(inputSource);
				
		return out;
	}
		
	
	 public static String readFileAsString(String filePath) throws java.io.IOException
	 {
		 
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        
        char[] buf = new char[1024];
        int numRead=0;
        
        while ((numRead=reader.read(buf)) != -1)
        {
            fileData.append(buf, 0, numRead);
        }
        reader.close();
        return fileData.toString();
    }

	
	 /**
	  * Attempts to parse out a valid xml <code>Document</code> from the passed string.
	  * All newline, tabs and carrage return characters will be removed.
	  * @param xmlFile
	  * @return
	  * @throws IOException
	  * @throws ParserConfigurationException
	  * @throws SAXException
	  */
	 public static Document parseXmlFile(String xmlFile) throws IOException, ParserConfigurationException, SAXException
		{


				String regex1 = "[\n\t\r]";
	
				
				String stringOut = readFileAsString(xmlFile);
				
				stringOut = stringOut.replaceAll(regex1, "");
				// System.out.println(stringOut + "!!!");
				//parse using builder to get DOM representation of the XML file

				return parseXmlString(stringOut);
		}
	
	
	public static Document parseXmlFile(File xmlFile)
	{
		Document out = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{
			String regex = "[\n\t]";
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			String stringOut = readFileAsString(xmlFile.getPath());
			
			stringOut.replaceAll(regex, "");
			
			// System.out.println(stringOut);
			//parse using builder to get DOM representation of the XML file
			out = db.parse(stringOut);
			

		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}catch(SAXException se) 
		{
			se.printStackTrace();
		}catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		return out;
	}
	
	public static String XMLDOMtoString( Document document )
	{
		
		String out = null;
		try
		{
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);
			Source source = new DOMSource(document);
			transformer.transform(source, result);
			writer.close();
			out = writer.toString();
		}
		catch ( Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
			
		}

		return out;
	}
	
	public static void printExceptionInfo(Exception e)
	{
		System.out.println( e.getClass().getName() + " Error: ");
		System.out.println(e.getMessage() + "\n" + e.getStackTrace());
	}
	
	public static File createFile(String filePath) throws IOException
	{
		File file = new File(filePath);
		
		if ( !file.exists() )
		{
			file.getParentFile().mkdirs();
			file.createNewFile();
			// System.out.println("creating new file " + file.toString());
		}
		else
		{
			// System.out.println("File exists: " + file.toString());
		}
		return file;
	}
}

package aiInterface.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import aiInterface.testing.client.TestingUtil;

public class LogFileUtil 
{
	
	public static final String PROPERTIES = "properties";
	public static final String LOG_FILES_DIR = "logs";

	public LogFileUtil ()
	{
		
	}
	
	public static File createLogFile(String dir, String prefix, String sufix) throws IOException
	{
		String filePath = "";
		filePath = filePath + dir + File.separator + prefix + File.separator + prefix;
		
		String pattern = "yyyy-MM-dd-HH-mm-ss";
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		String date = sf.format(new Date()); // current time
		
		filePath = filePath + date + sufix;
		System.out.println("trying to create the file " + filePath);
		return TestingUtil.createFile(filePath);
	}
	
	public static BufferedWriter createLogWriterFile(File file) throws IOException
	{
		return new BufferedWriter(new FileWriter(file));

	}
	public static FileWriter createLogFileWriter(File file) throws IOException
	{
		return new FileWriter(file);

	}
}

package aiInterface.data;

import java.sql.Timestamp;

import aiInterface.data.conversion.Converter;

public interface Data
{

	public String getSource();
	public void setSource(String source);
	public Timestamp getTimeStamp();
	
	
}

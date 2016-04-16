package Reporting.Models;
import java.util.ArrayList;

public class HtmlRow {
	public HtmlRow(ArrayList<HtmlColumn> columns)
	{
		Values = new String[columns.size()];
		Columns = columns;
	}
	public ArrayList<HtmlColumn> Columns;
	public String[] Values;
	
	public void SetValue(String columnName, String value) throws Exception
	{
		for(int x = 0; x < Columns.size(); x++)
		{
			if(Columns.get(x).columnName == columnName)
			{
				Values[x] = value;
				return;
			}
		}
		throw new Exception("column doesn't exist: " + columnName);
	}
	
	public void SetValues(ArrayList<String> values) throws Exception
	{
		if(values.size() > Values.length)
		{
			throw new Exception("Values being added are more than available columns.");
		}
		for(int x = 0; x < values.size(); x++)
		{
			Values[x] = values.get(x);
		}
	}
}

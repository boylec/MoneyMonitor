package Reporting.Models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import Core.Models.Category;

public class HtmlTable {
	public HtmlTable(ArrayList<String> columnsToCreate)
	{
		Columns = new ArrayList<HtmlColumn>();
		for(int x = 0; x < columnsToCreate.size(); x++)
		{
			Columns.add(new HtmlColumn(columnsToCreate.get(x)));
		}
	}
	
	public ArrayList<HtmlColumn> Columns;
	public ArrayList<HtmlRow> Rows = new ArrayList<HtmlRow>();
	public String TableTitle = "Report Table";
	
	public int AddRow(ArrayList<String> rowValues) throws Exception
	{
		HtmlRow newRow = new HtmlRow(Columns);
		newRow.SetValues(rowValues);
		Rows.add(newRow);
		return Rows.size()-1;
	}
}

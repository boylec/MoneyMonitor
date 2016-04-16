package Core.Services;

import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import Core.Models.AmountComparator;
import Core.Models.Category;
import Core.Models.DateComparator;
import Core.Models.CategoryComparator;
import Core.Models.SourceComparator;
import Core.Models.Transaction;
import Reporting.Models.HtmlColumn;
import Reporting.Models.HtmlDocument;
import Reporting.Models.HtmlTable;

public class ReportBuilder {

	public HtmlDocument BuildReport(CategoryListing catListing, ArrayList<Transaction> transactions, ArrayList<Category> catFilters, ReportBuilder.SortingType sortType, String reportTitle) throws Exception
	{
		ArrayList<Transaction> filteredTrans = catListing.FilterTransactions(transactions, catFilters);
		switch(sortType)
		{
			case Amount:
				Collections.sort(filteredTrans,new AmountComparator());
				break;
			case Date:
				Collections.sort(filteredTrans,new DateComparator());
				break;
			case Category:
				Collections.sort(filteredTrans,new CategoryComparator(catListing));
				break;
			case Source:
				Collections.sort(filteredTrans,new SourceComparator());
				break;
			default:
				break;
		}
		ArrayList<String> colNames = new ArrayList<String>();
		colNames.add("Source");
		colNames.add("Amount");
		colNames.add("Date");
		colNames.add("Spending Categories");
		HtmlTable transactionTable = new HtmlTable(colNames);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		for(int x = 0; x < filteredTrans.size(); x++)
		{
			Transaction tran = filteredTrans.get(x);
			ArrayList<String> rowValues = new ArrayList<String>();
			rowValues.add(tran.get_source());
			rowValues.add(formatter.format(tran.get_amount()));
			rowValues.add(tran.get_dateOfTransaction().toString());
			rowValues.add(GetCategoryString(catListing, tran));
			transactionTable.AddRow(rowValues);
		}
		
		
		ArrayList<String> summaryColNames = new ArrayList<String>();
		summaryColNames.add("Category");
		summaryColNames.add("Total Spent");
		summaryColNames.add("Sub-Total");
		HtmlTable summaryTable = new HtmlTable(summaryColNames);
		
		Collections.sort(catFilters);
		double lastAmount = 0;
		for(int x = 0; x < catFilters.size(); x++)
		{
			Category cat = catFilters.get(x);
			ArrayList<String> rowValues = new ArrayList<String>();
			rowValues.add(cat.CategoryName);
			double totalSpentOnCat = GetTotalSpent(cat,filteredTrans,catListing);
			rowValues.add(formatter.format(totalSpentOnCat));
			rowValues.add(formatter.format(totalSpentOnCat + lastAmount));
			lastAmount = totalSpentOnCat+lastAmount;
			summaryTable.AddRow(rowValues);
		}
		return new HtmlDocument(reportTitle, "Transactions", transactionTable, summaryTable, sortType);
	}
	
	public static String GetCategoryString(CategoryListing catListing, Transaction tran)
	{
		String catString = "";
		for(Category cat : catListing.GetCategories(tran))
		{
			catString += cat.CategoryName + ", ";
		}
		if(catString.endsWith(", "))
		{
			catString = catString.substring(0,catString.length()-2);
		}
		if(catString == "")
		{
			catString = "None";
		}
		return catString;
	}
	
	private double GetTotalSpent(Category cat, ArrayList<Transaction> filteredTrans, CategoryListing catListing){
		double amount = 0;
		for(int x = 0; x < filteredTrans.size(); x++)
		{
			Transaction tran = filteredTrans.get(x);
			if(catListing.IsInCategory(cat, tran))
			{
				amount += tran.get_amount();
			}
		}
		return amount;
	}
	
	public enum SortingType {
		Date,
		Amount,
		Source,
		Category
	}
}

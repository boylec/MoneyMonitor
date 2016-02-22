package Core.Models;

import java.util.ArrayList;

public class Account {
	public String AccountName;
	public String AccountNumber;
	private ArrayList<Transaction> transactionListing;
	
	public Account(String AccountName, String AccountNumber, ArrayList<Transaction> transactions)
	{
		this.AccountName = AccountName;
		this.AccountNumber = AccountNumber;
		this.transactionListing = transactions;
	}
	
	public ArrayList<Transaction> GetTransactions()
	{
		return transactionListing;
	}
}

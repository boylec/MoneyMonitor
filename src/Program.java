import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import BankService.DummyBank;
import BankService.IBankService;
import Core.Models.Account;
import Core.Models.Bank;
import Core.Models.CredentialSet;
import Core.Models.Transaction;

public class Program {
    private static Scanner _scanner = new Scanner(System.in);
	private static ArrayList<IBankService> _availableBankServices = new ArrayList<IBankService>()
			{{
				add(new DummyBank());
			}};
	private static Bank theBank;
	private static boolean connected = false;
	public static void main(String[] args) {
		System.out.println();
		PrintMenu();
	}
	
	private static void PrintMenu()
	{
		boolean exitChosen = false;
		while(!exitChosen)
		{
			System.out.println("Main Menu");
			System.out.println("==========");
			if(!connected)
			{
				System.out.println("1.Connect to your bank");
				System.out.println("2.Exit");
			}
			else
			{
				ArrayList<Account> accounts = theBank.GetAccounts();
				
				System.out.println("View transactions for account");
				System.out.println("=============================");
				for(int x=0; x < accounts.size(); x++)
				{
					Account account = accounts.get(x);
					System.out.println(x+1 + ". Account: " + account.AccountName + "\nAccount Number: " + account.AccountNumber);
				}
				System.out.println(accounts.size()+1 + ".Exit");
			}
	
		    int choice = _scanner.nextInt();
		    _scanner.nextLine();
		    if(!connected)
		    {
		    	if(choice == 1)
		    	{
		    		ConnectToBank();
		    	}
		    	else if(choice == 2)
		    	{
		    		exitChosen = true;
		    	}
		    }
		    else
		    {
		    	ArrayList<Account> accounts = theBank.GetAccounts();
		    	if(choice <= accounts.size())
		    	{
		    		ShowTransactions(accounts.get(choice-1));
		    	}
		    	if(choice == accounts.size()+1)
		    	{
		    		exitChosen = true;
		    	}
		    }
		}
		System.out.println("Exiting...");
	}

	private static void ShowTransactions(Account account) {
		ArrayList<Transaction> transactions = account.GetTransactions();
		System.out.println();
		System.out.println("Printing transactions for Account: " + account.AccountName);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		for(int x = 0; x < transactions.size(); x++)
		{
			Transaction thisTransaction = transactions.get(x);
			System.out.println(x+1 + ".\nSource: " + thisTransaction.get_source() + "\nDate: " + thisTransaction.get_dateOfTransaction() + "\nAmount: " + formatter.format(thisTransaction.get_amount()) + "\n");
		}
	}

	private static void ConnectToBank() {
		System.out.println();
		System.out.println("Pick a bank to connect to:");
		System.out.println("==========================");
		int availableBanksSize = _availableBankServices.size();
		for(int x = 0; x < availableBanksSize; x++)
		{
			IBankService thisBank = _availableBankServices.get(x);
			System.out.println(x + 1 + "." + thisBank.GetBankname());
		}
	    int choice = _scanner.nextInt();
	    _scanner.nextLine();
	    if(choice <= availableBanksSize)
	    {
	    	IBankService selectedBankService = _availableBankServices.get(choice-1);
	    	System.out.println("Connecting to " + selectedBankService.GetBankname());
	    	System.out.println("Please enter your credentials...");
	    	System.out.println("Username: ");
	    	CredentialSet credentials = new CredentialSet();
	    	credentials.userName = _scanner.nextLine();
	    	System.out.println("Password: ");
	    	credentials.password = _scanner.nextLine();
	    	System.out.println("Authenticating...");
	    	theBank = new Bank(selectedBankService);
	    	if(theBank.Authorize(credentials))
	    	{
	    		connected = true;
	    		System.out.println("Connected!");
	    	}
	    	else
	    	{
	    		System.out.println("Authentication failed...");
	    	}
	    }
	}
}

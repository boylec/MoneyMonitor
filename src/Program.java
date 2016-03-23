import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import BankService.DummyBank;
import BankService.IBankService;
import Core.Models.Account;
import Core.Models.Bank;
import Core.Models.Category;
import Core.Models.CredentialSet;
import Core.Models.Transaction;
import UserDummyDatabase.UserContext;

public class Program {
    private static Scanner _scanner = new Scanner(System.in);
	private static ArrayList<IBankService> _availableBankServices = new ArrayList<IBankService>()
			{{
				add(new DummyBank());
			}};
	private static Bank theBank;
	private static MenuState menuState = MenuState.NotConnected;
	private static Account selectedAccount = null;
	private static Category selectedCategory = null;
	private static Transaction selectedTransaction = null;
	private static ArrayList<Category> categoryFilters = new ArrayList<Category>();
	private static UserContext UserContext = new UserContext();
	
	public static void main(String[] args) {
		System.out.println();
		PrintMenu();
	}
	
	private static void PrintMenu()
	{
		
		boolean exitChosen = false;
		while(!exitChosen)
		{
			int choice;
			switch(menuState)
			{
				case NotConnected:
					System.out.println("Main Menu (Not Connected)");
					System.out.println("=========================");
					System.out.println("1.Connect to your bank");
					System.out.println("2.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    switch(choice)
				    {
				    case 1:
				    	ConnectToBank();
				    	break;
				    case 2:
				    	exitChosen = true;
				    	break;
			    	default:
			    		break;
				    }
					break;
					
				case Connected:
					ArrayList<Account> accounts = theBank.GetAccounts();
					
					System.out.println("Main Menu (Connected)");
					System.out.println("=====================");
					for(int x=0; x < accounts.size(); x++)
					{
						Account account = accounts.get(x);
						System.out.println(x+1 + ". Account: " + account.AccountName + "\nAccount Number: " + account.AccountNumber);
					}
					System.out.println(accounts.size()+1 + ".View/Edit Categories");
					System.out.println(accounts.size()+2 + ".Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
			    	if(choice <= accounts.size())
			    	{
			    		selectedAccount = accounts.get(choice-1);
			    		menuState = MenuState.AccountSelected;
			    	}
			    	else if(choice == accounts.size()+1)
			    		menuState = MenuState.EditCategories;
			    	else if(choice == accounts.size()+2)
			    		exitChosen = true;
			    	break;
				case EditCategories:
					System.out.println("Editing Categories (Connected)");
					System.out.println("==============================");
					int index = 0;
					int totalCats = UserContext.userCategories.size();
					for(Category cat : UserContext.userCategories)
					{
						System.out.println(index + 1 + "." + cat.CategoryName);
						index++;
					}
					System.out.println(totalCats+1 + ".Add Category");
					System.out.println(totalCats+2 + ".Back to Main Menu");
					System.out.println(totalCats+3 + ".Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
			    	if(choice <= totalCats)
			    	{
			    		selectedCategory = UserContext.userCategories.get(choice-1);
			    		menuState = MenuState.CategorySelected;
			    	}
			    	else if(choice == totalCats+1)
			    	{
			    		System.out.println("What would you like to name this category?");
			    		String newName = _scanner.nextLine();
			    		UserContext.userCategories.add(new Category(newName));
			    	}	
			    	else if(choice == totalCats+2)
			    		menuState = MenuState.Connected;
			    	else if(choice == totalCats+3)
			    		exitChosen = true;
			    	break;
				case CategorySelected:
					PrintAvailableCategories();
					System.out.println("Editing Category: " + selectedCategory.CategoryName + " (Connected)");
					System.out.println("==============================");
					System.out.println("1.Remove category");
					System.out.println("2.Rename category");
					System.out.println("3.Back to All Categories");
					System.out.println("4.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
			    	switch(choice)
			    	{
			    	case 1:
			    		UserContext.CategoryAssociations.RemoveCategoryGlobal(selectedCategory);
			    		UserContext.TransactionCategoryOverrider.RemoveCategoryGlobal(selectedCategory);
			    		UserContext.userCategories.remove(selectedCategory);
			    		categoryFilters.remove(selectedCategory);
			    		selectedCategory = null;
			    		menuState = MenuState.EditCategories;
			    		break;
			    	case 2:
			    		System.out.println("What would you like to rename " + selectedCategory.CategoryName + " to?");
			    		String newName = _scanner.nextLine();
			    		int indexInCategoryFilters = categoryFilters.indexOf(selectedCategory);
			    		categoryFilters.get(indexInCategoryFilters).CategoryName = newName;
			    		selectedCategory.CategoryName = newName;
			    		selectedCategory = null;
			    		menuState = MenuState.EditCategories;
			    		break;
			    	case 3:
			    		selectedCategory = null;
			    		menuState = MenuState.EditCategories;
			    		break;
			    	case 4:
			    		exitChosen = true;
			    		break;
			    	}
					break;
				case AccountSelected:
					PrintActiveFilters();
					ShowTransactions();
					System.out.println("Main Menu (Connected) Viewing Account: " + selectedAccount.AccountName + ", #" + selectedAccount.AccountNumber);
					System.out.println("=====================");
					System.out.println("1.Print Transactions");
					System.out.println("2.Add/Remove Category Filters");
					System.out.println("3.Edit Specific Transaction");
					System.out.println("4.Back to Account Select");
					System.out.println("5.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
				    switch(choice)
				    {
					    case 1:
					    	ShowTransactions();
					    	break;
					    case 2:
					    	menuState = MenuState.EditTransactionFilters;
					    	break;
					    case 3:
					    	selectedTransaction = SelectFromFilteredTransactions();
					    	menuState = MenuState.EditTransaction;
					    	break;
					    case 4:
					    	selectedAccount = null;
					    	menuState = MenuState.Connected;
					    	break;
					    case 5:
					    	exitChosen = true;
					    	break;
					    default:
					    	break;
				    }
					break;
				case EditTransactionFilters:
					System.out.println("Editing Category Filters (Connected)");
					System.out.println("==============================");
					
					PrintActiveFilters();
					PrintAvailableCategories();
					
					System.out.println("Select One: ");
					System.out.println("===========]");
					System.out.println("1.Add category filter");
					System.out.println("2.Remove category filter");
					System.out.println("3.Return to Account Menu");
					System.out.println("4.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
				    switch(choice)
				    {
				    case 1:
				    	Category catToAdd = SelectFromAvailableCategories();
				    	if(!categoryFilters.contains(catToAdd))
				    	{
				    		categoryFilters.add(catToAdd);
				    	}
				    	else
				    	{
				    		System.out.println("Category is already being used as a filter.");
				    	}
				    	break;
				    case 2:
				    	Category catToRemove = SelectFromActiveFilters();
				    	if(!categoryFilters.remove(catToRemove))
				    	{
				    		System.out.println("Failed to remove category from filter list.");
				    	}
				    	break;
				    case 3:
				    	menuState = MenuState.AccountSelected;
				    	break;
				    case 4:
				    	exitChosen = true;
				    	break;
				    }
				    break;
				case EditTransaction:
					
					System.out.println("\nEditing Transaction (Connected)");
					System.out.println("==============================");
					
					PrintTransaction(selectedTransaction);
					
					System.out.println("Select One: ");
					System.out.println("===========]");
					System.out.println("1.Change amount");
					System.out.println("2.Change categories");
					System.out.println("3.Return to Account Menu");
					System.out.println("4.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
				    switch(choice)
				    {
				    case 1:
				    	System.out.println("Enter new amount in decimal form");
				    	double newAmount = _scanner.nextDouble();
				    	selectedTransaction.set_amount(newAmount);
				    	break;
				    case 2:
				    	menuState = MenuState.EditTransactionCategory;
				    	break;
				    case 3:
				    	menuState = MenuState.AccountSelected;
				    	break;
				    case 4:
				    	exitChosen = true;
				    	break;
				    }
				    break;
				case EditTransactionCategory:
			    	System.out.println("\nEditing Transaction Categories (Connected)");
					System.out.println("==============================");
					
					PrintTransaction(selectedTransaction);
					PrintTransactionCategories(selectedTransaction);
					
					System.out.println("\nSelect One: ");
					System.out.println("===========]");
					System.out.println("1.Override rule and add custom category for this transaction ONLY");
					System.out.println("2.Add category to rule for transactions from " + selectedTransaction.get_source());
					System.out.println("3.Override rule and remove category for this transaction ONLY");
					System.out.println("4.Remove category for all transactions from " + selectedTransaction.get_source());
					System.out.println("5.Return to Account Menu");
					System.out.println("6.Exit");
				    choice = _scanner.nextInt();
				    _scanner.nextLine();
				    
				    Category catToAdd;
				    Category catToRemove;
				    switch(choice)
				    {
				    case 1:
				    	catToAdd = SelectFromAvailableCategories();
				    	UserContext.CategoryAssociations.AssociateCategory(catToAdd, selectedTransaction);
				    	break;
				    case 2:
				    	catToAdd = SelectFromAvailableCategories();
				    	UserContext.CategoryAssociations.AssociateCategory(catToAdd, selectedTransaction.get_source());
				    	break;
				    case 3:
				    	catToRemove = SelectTransactionCategory();
				    	UserContext.CategoryAssociations.DisassociateCategory(catToRemove, selectedTransaction);
				    	break;
				    case 4:
				    	catToRemove = SelectTransactionCategory();
				    	UserContext.CategoryAssociations.DisassociateCategory(catToRemove, selectedTransaction.get_source());
				    	exitChosen = true;
				    	break;
				    case 5:
				    	selectedTransaction = null;
				    	menuState = MenuState.AccountSelected;
				    	break;
				    case 6:
					    exitChosen = true;
					    break;
				    }
			    break;
			}
				    
		}
		System.out.println("Exiting...");
	}

	private static Category SelectFromActiveFilters(){
		int index = 0;
		for(Category cat : categoryFilters)
		{
			System.out.println(index + 1 + "." + cat.CategoryName);
			index++;
		}
		System.out.println("Select an active category filter");
		int choice = _scanner.nextInt();
		_scanner.nextLine();
		return categoryFilters.get(choice - 1);
	}
	
	private static Category SelectFromAvailableCategories(){
		int index = 0;
		for(Category cat : UserContext.userCategories)
		{
			System.out.println(index + 1 + "." + cat.CategoryName);
			index++;
		}
		System.out.println("Select a category");
		int choice = _scanner.nextInt();
		_scanner.nextLine();
		return UserContext.userCategories.get(choice - 1);
	}
	
	private static Transaction SelectFromFilteredTransactions()
	{
		ArrayList<Transaction> allTrans = UserContext.CategoryAssociations.FilterTransactions(selectedAccount.GetTransactions(), categoryFilters);
		int index = 0;
		for(Transaction trans : allTrans )
		{
			PrintTransaction(index, trans);
			index++;
		}
		System.out.println("Select a transaction");
		int choice = _scanner.nextInt();
		_scanner.nextLine();
		return allTrans.get(choice - 1);
	}
	

	
	private static Category SelectTransactionCategory()
	{
		ArrayList<Category> catsToPrint = (ArrayList<Category>)UserContext.CategoryAssociations.GetCategories(selectedTransaction);
		
		System.out.println("Transaction Categories");
		System.out.println("======================");
		int index = 0;
		for(Category cat : catsToPrint)
		{
			System.out.println(index + "." + cat);
			index++;
		}
		System.out.println("\nSelect a category");
		int choice = _scanner.nextInt();
		_scanner.nextLine();

		return ((ArrayList<Category>)UserContext.CategoryAssociations.GetCategories(selectedTransaction)).get(choice - 1);
	}
	
	private static void PrintTransactionCategories(Transaction trans)
	{
		ArrayList<Category> catsToPrint = new ArrayList<Category>();
		
		catsToPrint = (ArrayList<Category>)UserContext.CategoryAssociations.GetCategories(trans);
		
		System.out.println("");
		System.out.println("Transaction Categories");
		System.out.println("======================");
		if(catsToPrint == null)
		{
			System.out.println("None");
		}
		else
		{
			for(Category cat : catsToPrint)
			{
				System.out.println(cat.CategoryName);
			}
		}
	}
	
	private static void PrintTransactionCategoriesShort(Transaction trans)
	{
		ArrayList<Category> catsToPrint = new ArrayList<Category>();
		catsToPrint = (ArrayList<Category>)UserContext.CategoryAssociations.GetCategories(trans);
		int index = 0;
		if(catsToPrint != null)
		{
			for(Category cat : catsToPrint)
			{
				System.out.print(cat.CategoryName);
				if(index < catsToPrint.size()-1)
					System.out.print(",");
				else
					System.out.print("\n");
				index++;
			}
		}
	}
	
	private static void PrintTransaction(Transaction trans)
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		System.out.println("Source: " + trans.get_source() + "\nDate: " + trans.get_dateOfTransaction() + "\nAmount: " + formatter.format(trans.get_amount()));
		System.out.print("Categories: ");
		PrintTransactionCategoriesShort(trans);
		System.out.print("\n");
	}
	
	private static void PrintTransaction(int index, Transaction trans)
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		System.out.println(index+1 + ".Source: " + trans.get_source() + "\nDate: " + trans.get_dateOfTransaction() + "\nAmount: " + formatter.format(trans.get_amount()));
		System.out.print("Categories: ");
		PrintTransactionCategoriesShort(trans);
		System.out.print("\n");
	}
	
	private static void PrintActiveFilters()
	{
		System.out.println("");
		System.out.println("\tACTIVE FILTERS");
		System.out.println("\t=============");
		for(Category cat : categoryFilters)
		{
			System.out.println("\t" + cat.CategoryName);
		}
		System.out.println("");
	}
	
	private static void PrintAvailableCategories()
	{
		System.out.println("");
		System.out.println("\tREGISTERED CATEGORIES");
		System.out.println("\t=====================");
		for(Category cat : UserContext.userCategories)
		{
			System.out.println("\t" + cat.CategoryName);
		}
		System.out.println("");
	}
	private static void ShowTransactions() {
		ArrayList<Transaction> unfilteredTransactions = selectedAccount.GetTransactions();		
		ArrayList<Transaction> filteredTransactions = categoryFilters.size() > 0 ? UserContext.CategoryAssociations.FilterTransactions(unfilteredTransactions, categoryFilters) : unfilteredTransactions;
		System.out.println();
		System.out.println("Printing transactions for Account: " + selectedAccount.AccountName);
		for(int x = 0; x < filteredTransactions.size(); x++)
		{
			Transaction thisTransaction = filteredTransactions.get(x);
			PrintTransaction(x, thisTransaction);
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
	    		menuState = MenuState.Connected;
	    		System.out.println("Connected!");
	    	}
	    	else
	    	{
	    		System.out.println("Authentication failed...");
	    	}
	    }
	}
	
	public enum MenuState{
		NotConnected,
		Connected,
		EditCategories,
		AccountSelected,
		CategorySelected,
		EditTransactionFilters,
		EditTransaction,
		EditTransactionCategory
	}
}

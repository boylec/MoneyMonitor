package Core.Services;


import java.util.ArrayList;

import Core.Models.Category;
import Core.Models.Transaction;

public class TransactionCategoryOverrider {
	public ArrayList<OverridePairing> overrides = new ArrayList<OverridePairing>();
	
	public class OverridePairing
	{
		public OverridePairing(Transaction tran, Category cat)
		{
			transaction = tran;
			categories.add(cat);
		}
		
		public OverridePairing(Transaction tran)
		{
			transaction = tran;
		}
		
		public Transaction transaction;
		public ArrayList<Category> categories = new ArrayList<Category>();
	}
	
	public void AddCategoryToTransaction(Category cat, Transaction trans)
	{
		boolean added = false;
		for(OverridePairing override : overrides)
		{
			if(override.transaction.get_transactionId() == trans.get_transactionId())
			{
				if(!override.categories.contains(cat))
				{
					override.categories.add(cat);
				}
			}
		}
		if(!added)
		{
			overrides.add(new OverridePairing(trans,cat));
		}
	}
	
	public void RemoveCategoryFromTransaction(Category cat, Transaction trans)
	{
		boolean found = false;
		for(OverridePairing override : overrides)
		{
			if(override.transaction.get_transactionId() == trans.get_transactionId())
			{
				found = true;
				if(override.categories.contains(cat))
				{
					override.categories.remove(cat);
				}
				break;
			}
		}
		if(!found)
		{
			overrides.add(new OverridePairing(trans));
		}
	}
	
	public boolean CheckIfOverriden(Transaction transaction)
	{
		for(OverridePairing override : overrides)
		{
			if(override.transaction.get_transactionId() == transaction.get_transactionId())
			{
				return true;
			}
		}
		return false;
	}
	
	public void RemoveCategoryGlobal(Category cat)
	{
		for(OverridePairing override : overrides)
		{
			if(override.categories.contains(cat))
			{
				override.categories.remove(cat);
			}
		}
	}
	
	public void RemoveTransactionFromOverridesList(Transaction transaction)
	{
		int indexToRemove = 0;
		boolean found = false;
		for(OverridePairing override : overrides)
		{
			if(override.transaction.get_transactionId() == transaction.get_transactionId())
			{
				found = true;
				break;
			}
			indexToRemove++;
		}
		if(found)
		{
			overrides.remove(indexToRemove);
		}
	}
	
	public ArrayList<Category> GetOverridenCategories(Transaction transaction)
	{
		for(OverridePairing override : overrides)
		{
			if(override.transaction.get_transactionId() == transaction.get_transactionId())
			{
				return override.categories;
			}
		}
		return null;
	}
}

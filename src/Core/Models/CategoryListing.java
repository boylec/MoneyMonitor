package Core.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

//Describes and allows for interaction with a collection of categories.
public class CategoryListing {
	private TransactionCategoryOverrider CategoryOverrider = new TransactionCategoryOverrider();
	private Map<String,ArrayList<Category>> categoryAssociations = new HashMap<String,ArrayList<Category>>();
	
	public CategoryListing()
	{
		categoryAssociations = new HashMap<String,ArrayList<Category>>();
	}
	
	public Iterable<Category> GetCategories(Transaction trans)
	{
		ArrayList<Category> cats = new ArrayList<Category>();
		if(CategoryOverrider.CheckIfOverriden(trans))
		{
			cats = CategoryOverrider.GetOverridenCategories(trans);
		}
		else
		{
			cats = categoryAssociations.get(trans.get_source());
		}
		return cats;
	}
	
	public Iterable<String> GetTransactionSources(Category category)
	{
		ArrayList<String> sources = new ArrayList<String>();
		for(Entry<String,ArrayList<Category>> entry : categoryAssociations.entrySet())
		{
			if(entry.getValue().contains(category))
			{
				sources.add(entry.getKey());
			}
		}
		return sources;
	}
	
	public void RemoveCategoryGlobal(Category cat)
	{
		for(Entry<String,ArrayList<Category>> entry: categoryAssociations.entrySet())
		{
			if(entry.getValue().contains(cat))
			{
				entry.getValue().remove(cat);
			}
		}
	}
	
	public ArrayList<Transaction> FilterTransactions(ArrayList<Transaction> initialTransactions, ArrayList<Category> filters)
	{
		if(filters.size() == 0)
		{
			return initialTransactions;
		}
		
		ArrayList<Transaction> results = new ArrayList<Transaction>();
		for(Transaction tran : initialTransactions)
		{
			for(Category filter : filters){
				if(IsInCategory(filter, tran) && !results.contains(tran))
				{
						results.add(tran);
				}
			}
		}
		return results;
	}
	
	public ArrayList<Category> RemoveSourceGlobal(String source)
	{
		return categoryAssociations.remove(source);
	}
	
	public boolean DisassociateCategory(Category cat, String source){
		ArrayList<Category> currentAssociations = categoryAssociations.get(source);
		if(currentAssociations != null && currentAssociations.contains(cat))
		{
			currentAssociations.remove(cat);
			categoryAssociations.replace(source, currentAssociations);
			return true;
		}
		return false;
	}
	
	public void DisassociateCategory(Category cat, Transaction trans)
	{
		CategoryOverrider.RemoveCategoryFromTransaction(cat, trans);
	}
	
	public boolean AssociateCategory(Category cat, String source)
	{
		ArrayList<Category> currentAssociations = categoryAssociations.get(source);
		if(currentAssociations != null && !currentAssociations.contains(cat))
		{
			currentAssociations.add(cat);
			categoryAssociations.replace(source, currentAssociations);
			return true;
		}
		else
		{
			ArrayList<Category> initialAssociation = new ArrayList<Category>();
			initialAssociation.add(cat);
			categoryAssociations.put(source, initialAssociation);
			return true;
		}
	}
	
	public void AssociateCategory(Category cat, Transaction trans)
	{
		CategoryOverrider.AddCategoryToTransaction(cat, trans);
	}
	
	public boolean IsInCategory(Category cat, Transaction source)
	{
		if(CategoryOverrider.CheckIfOverriden(source))
		{
			return CategoryOverrider.GetOverridenCategories(source).contains(cat);
		}
		else
		{
			ArrayList<Category> cats =  categoryAssociations.get(source.get_source());
			if(cats == null)
				return false;
			else
				return cats.contains(cat);
		}
	}
}

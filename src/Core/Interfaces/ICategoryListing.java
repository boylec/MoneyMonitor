package Core.Interfaces;

import java.util.Iterator;

import Core.Models.Category;

public interface ICategoryListing {
	public Category RemoveAt(int index);
	public int Insert(Category cat);
	public Category InsertAndRetrieve(Category cat);
	public Iterable<Category> GetAll();
	public int Count();
	public Iterator<Category> iterator();
	public void SortAlphabetical();
}

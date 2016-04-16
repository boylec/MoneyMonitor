package Core.Models;

import java.util.Comparator;

import Core.Services.CategoryListing;
import Core.Services.ReportBuilder;

	public class CategoryComparator implements Comparator<Transaction>
	{
		private CategoryListing _catListing;
		public CategoryComparator(CategoryListing catListing)
		{
			_catListing = catListing;
		}
		@Override
		public int compare(Transaction tran1, Transaction tran2) {
			String catString1 = ReportBuilder.GetCategoryString(_catListing, tran1);
			String catString2 = ReportBuilder.GetCategoryString(_catListing, tran2);
			int result = catString1.compareTo(catString2); 
			return result;
		}
	}


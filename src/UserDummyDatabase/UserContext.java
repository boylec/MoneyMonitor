package UserDummyDatabase;

import java.util.ArrayList;

import Core.Models.Category;
import Core.Services.CategoryListing;
import Core.Services.TransactionCategoryOverrider;

public class UserContext {
	public UserContext()
	{
		TransactionCategoryOverrider = new Core.Services.TransactionCategoryOverrider();
		CategoryAssociations = new CategoryListing();
		
		for(Category cat : userCategories)
		{
			switch(cat.CategoryName)
			{
				case "Food":
					CategoryAssociations.AssociateCategory(cat, "Mcdonalds");
					CategoryAssociations.AssociateCategory(cat, "Burger King");
					CategoryAssociations.AssociateCategory(cat, "Kroger");
					CategoryAssociations.AssociateCategory(cat, "Tom Thumb");
					break;
				case "Shopping":
					CategoryAssociations.AssociateCategory(cat, "Gamespot.com");
					CategoryAssociations.AssociateCategory(cat, "Amazon.com");
					CategoryAssociations.AssociateCategory(cat, "Kroger");
					CategoryAssociations.AssociateCategory(cat, "Nebraska Furniture Mart");
					CategoryAssociations.AssociateCategory(cat, "Tom Thumb");
					CategoryAssociations.AssociateCategory(cat, "Macy's");
					break;
				case "Gas Station":
					CategoryAssociations.AssociateCategory(cat, "Shell Gas Station");
					break;
				case "Education":
					CategoryAssociations.AssociateCategory(cat, "University of Texas");
					break;
				case "Miscellaneous":
					CategoryAssociations.AssociateCategory(cat, "ATM Free");
					CategoryAssociations.AssociateCategory(cat, "Shell Gas Station");
					break;
				case "Need":
					CategoryAssociations.AssociateCategory(cat, "Reliant Energy");
					CategoryAssociations.AssociateCategory(cat, "City of Richardson");
					CategoryAssociations.AssociateCategory(cat, "Kroger");
					CategoryAssociations.AssociateCategory(cat, "Tom Thumb");
					CategoryAssociations.AssociateCategory(cat, "Shell Gas Station");
					break;
				case "Want":
					CategoryAssociations.AssociateCategory(cat, "Nebraska Furniture Mart");
					CategoryAssociations.AssociateCategory(cat, "Gamespot.com");
					CategoryAssociations.AssociateCategory(cat, "Amazon.com");
					CategoryAssociations.AssociateCategory(cat, "Macy's");
					break;
				default:
					break;
			}
		}
	}
	
	public ArrayList<Category> userCategories = InitialData.userCategories;
	public ArrayList<String> potentialSources  = InitialData.potentialSources;
	public CategoryListing CategoryAssociations;
	public TransactionCategoryOverrider TransactionCategoryOverrider;
}

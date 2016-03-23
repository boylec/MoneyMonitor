package UserDummyDatabase;

import java.util.ArrayList;

import Core.Models.Category;

public class InitialData {
	public static ArrayList<String> potentialSources  = new ArrayList<String>(){{
		add("Mcdonalds");
		add("Shell Gas Station");
		add("Macy's");
		add("ATM Fee");
		add("Nebraska Furniture Mart");
		add("Burger King");
		add("Gamespot.com");
		add("Amazon.com");
		add("Kroger");
		add("Tom Thumb");
		add("University of Texas");
		add("Reliant Energy");
		add("City of Richardson");
	}};
	
	public static ArrayList<Category> userCategories = new ArrayList<Category>(){{
		add(new Category("Food"));
		add(new Category("Shopping"));
		add(new Category("Gas Station"));
		add(new Category("Education"));
		add(new Category("Miscellaneous"));
		add(new Category("Groceries"));
		add(new Category("Need"));
		add(new Category("Want"));
		add(new Category("Bill"));
	}};;
	
}

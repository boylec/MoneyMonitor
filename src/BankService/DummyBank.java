package BankService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

import Core.Models.Account;
import Core.Models.Transaction;
import UserDummyDatabase.InitialData;

public class DummyBank implements IBankService{
	private static String bankName = "Bank of America";
	private static String acceptableUsername = "testUser";
	private static String acceptablePassword = "testPassword";

	private static UUID authorizedToken = null;
	@SuppressWarnings("serial")
	private static ArrayList<Transaction> transactions = new ArrayList<Transaction>(){{
		Random generator = new Random();
		int numberOfPotentialSources = InitialData.potentialSources.size();
		//Generate 100 random transactions
		for(int x = 0; x < 100; x++)
		{
			//Randomized transaction
			add(new Transaction(true,generator.nextDouble() * 100.0,InitialData.potentialSources.get(generator.nextInt(numberOfPotentialSources-1)), GenerateRandomDate(2016,2016),x));
		}
	}};
	
	@SuppressWarnings("serial")
	private static ArrayList<Account> accounts = new ArrayList<Account>()
			{{
				add(new Account("Test Account","12341234",transactions));
			}};
	public boolean Authorize(String userName, String password, UUID token)
	{
		if(userName.equals(acceptableUsername) && password.equals(acceptablePassword))
		{
			authorizedToken = token;
			return true;
		}
		return false;
	}
	
	public ArrayList<Account> GetAccounts(UUID token)
	{
		if(token.equals(authorizedToken))
		{
			return accounts;
		}
		return null;
	}
	
	public String GetBankname()
	{
		return bankName;
	}
	
    public static Date GenerateRandomDate(int minYear, int maxYear) {

        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(minYear, maxYear);

        gc.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}

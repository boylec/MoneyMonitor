package BankService;

import java.util.ArrayList;
import java.util.UUID;

import Core.Models.Account;
import Core.Models.Transaction;

public interface IBankService {
	public String bankName = "Bank of America";
	public UUID authorizedToken = null;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public ArrayList<Account> accounts = new ArrayList<Account>();
	public boolean Authorize(String userName, String password, UUID token);
	
	public ArrayList<Account> GetAccounts(UUID token);
	
	public String GetBankname();
}

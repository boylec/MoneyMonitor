package Core.Models;

import java.util.ArrayList;
import java.util.UUID;

import BankService.IBankService;

public class Bank {
	public UUID authorizedToken;
	public String bankName;
	public String ipAddress;
	private IBankService bankService;
	private boolean isConnected = false;
	
	public Bank(IBankService bankService)
	{
		this.bankService = bankService;
	}
	public boolean Authorize(CredentialSet credentials) {
		UUID tokenToPass = UUID.randomUUID();
		boolean authorized = bankService.Authorize(credentials.userName, credentials.password, tokenToPass);
		if(authorized)
		{
			isConnected = true;
			authorizedToken = tokenToPass;
			bankName = bankService.GetBankname();
			return true;
		}
		return false;
	}

	public ArrayList<Account> GetAccounts() {
		return bankService.GetAccounts(authorizedToken);
	}
}

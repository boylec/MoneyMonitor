package Core.Models;

import java.util.Comparator;

public class AmountComparator implements Comparator<Transaction>
{
	@Override
	public int compare(Transaction tran1, Transaction tran2) {
		if(tran1.get_amount() == tran2.get_amount())
		{
			return 0;
		}
		else if(tran1.get_amount() < tran2.get_amount())
			return -1;
		else
			return 1;
	}
}
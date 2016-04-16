package Core.Models;

import java.util.Comparator;



public class DateComparator implements Comparator<Transaction>
{
	@Override
	public int compare(Transaction tran1, Transaction tran2) {
		return tran1.get_dateOfTransaction().compareTo(tran2.get_dateOfTransaction());
	}
}

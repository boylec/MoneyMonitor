package Core.Models;

import java.util.Comparator;



public class SourceComparator implements Comparator<Transaction>
{
	@Override
	public int compare(Transaction tran1, Transaction tran2) {
		return tran1.get_source().compareTo(tran2.get_source());
	}
}

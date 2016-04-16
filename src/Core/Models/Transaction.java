package Core.Models;

import java.util.Comparator;
import java.util.Date;

public class Transaction {
	private int _transactionId = 0;
	private boolean _isExpense = true;
	private double _amount = 0;
	private String _source = "";
	private Date _dateOfTransaction = new Date();
	
	public Transaction(boolean isExpense, double amount, String source, Date date, int transId)
	{
		set_transactionId(transId);
		set_isExpense(isExpense);
		set_amount(amount);
		set_source(source);
		set_dateOfTransaction(date);
	}

	public boolean get_isExpense() {
		return _isExpense;
	}

	public void set_isExpense(boolean _isExpense) {
		this._isExpense = _isExpense;
	}

	public double get_amount() {
		return _amount;
	}

	public void set_amount(double _amount) {
		this._amount = _amount;
	}

	public String get_source() {
		return _source;
	}

	public void set_source(String _source) {
		this._source = _source;
	}

	public Date get_dateOfTransaction() {
		return _dateOfTransaction;
	}

	public void set_dateOfTransaction(Date _dateOfTransaction) {
		this._dateOfTransaction = _dateOfTransaction;
	}

	public int get_transactionId() {
		return _transactionId;
	}

	public void set_transactionId(int _transactionId) {
		this._transactionId = _transactionId;
	}
	
	
}



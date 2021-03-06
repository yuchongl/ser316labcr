/*
  File:	Savings.java
  Author: Yuchong Li, Timothy Dirusso	
  Date:	Feb. 20, 2017
  
  Description: Savings class implementation
*/

package banking.primitive.core;

/**
Class:	Savings

Description: implements the saving account class
*/
public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int _numWithdraws = 0;
	
	private final int _maxNumWithdraw = 3;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}


	/**
	  Method: deposit
	  Inputs: amount to deposit
	  Returns: true if successes, false if failed

	  Description: deposit into an account
	*/
	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
		}
		return false;
	}

	/**
	  Method: withdraw
	  Inputs: amount to withdraw
	  Returns: true if successes, false if failed

	  Description: withdraw from an account
	*/
	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == STATE.OPEN && amount > 0.0f) {
			balance = balance - amount;
			_numWithdraws++;
			if (_numWithdraws > _maxNumWithdraw){
				balance = balance - 1.0f;
			}
			// KG BVA: should be < 0
			if (balance <= 0.0f) {
				setState(STATE.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}

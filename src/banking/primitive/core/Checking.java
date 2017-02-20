/*
  File:	Checking.java
  Author: Yuchong Li, Timothy Dirusso	
  Date:	Feb. 20, 2017
  
  Description: Checking class implementation
*/

package banking.primitive.core;

public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int _numWithdraws = 0;
	private final int _maxNumWithdraw = 10;
	
	private Checking(String name) {
		super(name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > 0.0f) {
			balance = balance + amount;
		
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == STATE.OPEN || (getState() == STATE.OVERDRAWN && balance > -100.0f)) {
				balance = balance - amount;
				_numWithdraws++;
				if (_numWithdraws > _maxNumWithdraw){
					balance = balance - 2.0f;
				}
				if (balance < 0.0f) {
					setState(STATE.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	public String getType() { 
		{return "Checking"; }
		}
	
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}

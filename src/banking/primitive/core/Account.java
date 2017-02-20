/*
  File:	Account.java
  Author: Yuchong Li, Timothy Dirusso	
  Date:	Feb. 20, 2017
  
  Description: Account class implementation
*/

package banking.primitive.core;

public abstract class Account implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    protected enum STATE {
        OPEN, CLOSED, OVERDRAWN
    };

    protected float balance = 0.0F;
    protected String name;
    private STATE state;

    protected Account(String n) {
        name = n;
        state = STATE.OPEN;
    }

    protected Account(String n, float b) {
        this(n); 
        balance = b;
    }

    /**
     * @return name of the Account
     */
    public final String getName() {
        return name;
    }

    /**
     * @return balance in the Account
     */
    public final float getBalance() {
        return balance;
    }

    /**
     * @return either "Checking" or "Savings"
     */
    public abstract String getType();

    protected final STATE getState() {
        return state;
    }

    protected final void setState(STATE s) {
        state = s;
    }
    
    /**
     * Adds money to an account. May not be done if the account is CLOSED
     * 
     * @param parameter
     *            amount is a deposit and must be > 0
     * @return true if the deposit was successful, false if not due to amount or
     *         invalid state
     */
    public abstract boolean deposit(float amount);

    /**
     * Takes money out of an account. If the balance falls below 0 then the
     * account is moved to an OVERDRAWN state
     * 
     * @param parameter
     *            amount is a withdrawal and must be > 0
     * @return true if the deposit was successful, false if not due to amount or
     *         invalid state
     */
    public abstract boolean withdraw(float amount);

  
    public String toString() {
        return "Account " + name + " has $" + balance + "and is " + getState()
                + "\n";
    }
}

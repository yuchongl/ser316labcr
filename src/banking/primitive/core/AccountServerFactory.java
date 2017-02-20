/*
  File:	AccountServerFactory.java
  Author: Yuchong Li, Timothy Dirusso	
  Date:	Feb. 20, 2017
  
  Description: Factory for the AccountServer class
*/

package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}

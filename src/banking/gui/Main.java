/*
  File:	Main.java
  Author: Yuchong Li, Timothy Dirusso	
  Date:	Feb. 20, 2017
  
  Description: Where the program starts
*/

package banking.gui;

import javax.swing.JFrame;

/**
 * main method for running the program.
 * @author kevinagary
 *
 */

/**
Class:	Main

Description: implements the entry point for the program
*/
final class Main {
	/**
	 * Private constructor to address STYLE issue.
	 */
	private Main() {
	}
	
	private static final String _usage = "Usage: java FormMain <property file>"; 
	
	/**
	 * All methods should have a Javadoc according to STYLE.
	 * @param args command-line arguments
	 * @throws Exception as per typical main specifications
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println(_usage);
			System.exit(1);
		}

		String propertyFile = args[0];
		JFrame frame = new MainFrame(propertyFile);
		frame.setVisible(true);

	}
}

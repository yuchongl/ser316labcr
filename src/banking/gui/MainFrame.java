/*
  File:	MainFrame.java
  Author: Yuchong Li, Timothy Dirusso
  Date:	Feb. 20, 2017
  
  Description: sets up the GUI components
*/


package banking.gui;

import banking.primitive.core.Account;
import banking.primitive.core.AccountServer;
import banking.primitive.core.AccountServerFactory;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
class MainFrame extends JFrame {
	AccountServer	myServer;
	Properties		props;
	JLabel			typeLabel;
	JLabel			nameLabel;
	JLabel			balanceLabel;
	JComboBox		typeOptions;
	JTextField		nameField;
	JTextField		balanceField;
	JButton 		depositButton;
	JButton 		withdrawButton;
	JButton			newAccountButton;
	JButton			displayAccountsButton;
	JButton			displayODAccountsButton;
	
	final String _msgAccountCreated = "Account created successfully";
	final String _msgAccountNotCreated = "_msgAccountNotCreated!";
	final String _msgAccountSaved = "Accounts saved";
	final String _msgSaveAccountError = "Error saving accounts";
	final String _msgDepositSuccessful = "Deposit successful";
	final String _msgDepositUnseccessful = "Deposit unsuccessful";
	final String _msgWithdralSuccessful = "Withdrawal successful";
	final String _msgWithdrawalUnseccessful = "Withdrawal unsuccessful";
	
	public MainFrame(String propertyFile) throws IOException {

		//** initialize myServer
		myServer = AccountServerFactory.getMe().lookup();

		props = new Properties();

		FileInputStream fis = null; 
		try {
			fis =  new FileInputStream(propertyFile);
			props.load(fis);
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		_constructForm();
	}

	
	private void _constructForm() {
		//*** Make these read from properties
		typeLabel		= new JLabel(props.getProperty("TypeLabel"));
		nameLabel		= new JLabel(props.getProperty("NameLabel"));
		balanceLabel	= new JLabel(props.getProperty("BalanceLabel"));

		Object[] accountTypes = {"Savings", "Checking"};
		typeOptions = new JComboBox(accountTypes);
		nameField = new JTextField(20);
		balanceField = new JTextField(20);

		newAccountButton = new JButton("New Account");
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton saveButton = new JButton("Save Accounts");
		displayAccountsButton = new JButton("List Accounts");
		JButton displayAllAccountsButton = new JButton("All Accounts");

		this.addWindowListener(new FrameHandler());
		newAccountButton.addActionListener(new NewAccountHandler());
		displayAccountsButton.addActionListener(new DisplayHandler());
		displayAllAccountsButton.addActionListener(new DisplayHandler());
		depositButton.addActionListener(new DepositHandler());
		withdrawButton.addActionListener(new WithdrawHandler());
		saveButton.addActionListener(new SaveAccountsHandler());		
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		JPanel panel1 = new JPanel();
		panel1.add(typeLabel);
		panel1.add(typeOptions);
		
		JPanel panel2 = new JPanel();
		panel2.add(displayAccountsButton);
		panel2.add(displayAllAccountsButton);
		panel2.add(saveButton);
		
		JPanel panel3 = new JPanel();
		panel3.add(nameLabel);
		panel3.add(nameField);
		
		JPanel panel4 = new JPanel();
		panel4.add(balanceLabel);
		panel4.add(balanceField);
		
		JPanel panel5 = new JPanel();
		panel5.add(newAccountButton);
		panel5.add(depositButton);
		panel5.add(withdrawButton);

		pane.add(panel1);
		pane.add(panel2);
		pane.add(panel3);
		pane.add(panel4);
		pane.add(panel5);
		
		setSize(400, 250);
	}

	class DisplayHandler implements ActionListener {
		
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent
		  Returns:

		  Description: reacts to the action performed
		*/
		public void actionPerformed(ActionEvent e) {
			List<Account> accounts = null;
			if (e.getSource() == displayAccountsButton) {
				accounts = myServer.getActiveAccounts();
			} else {
				accounts = myServer.getAllAccounts();
			}
			StringBuffer sb = new StringBuffer();
			Account thisAcct = null;
			for (Iterator<Account> li = accounts.iterator(); li.hasNext();) {
				thisAcct = (Account)li.next();
				sb.append(thisAcct.toString()+"\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString());
		}
	}
	
	// Complete a handler for new account button
	class NewAccountHandler implements ActionListener {
		
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent
		  Returns:

		  Description: reacts to the action performed
		*/
		public void actionPerformed(ActionEvent e) {
			String type = typeOptions.getSelectedItem().toString();
			String name = nameField.getText();
			String balance = balanceField.getText();

			if (myServer.newAccount(type, name, Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, _msgAccountCreated);
			} else {
				JOptionPane.showMessageDialog(null, _msgAccountNotCreated);
			}
		}
	}
	
	// Complete a handler for new account button
	class SaveAccountsHandler implements ActionListener {
		
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent
		  Returns:

		  Description: reacts to the action performed
		*/
		public void actionPerformed(ActionEvent e) {
			try {
				myServer.saveAccounts();
				JOptionPane.showMessageDialog(null, _msgAccountSaved);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(null, _msgSaveAccountError);
			}
		}
	}

	// Complete a handler for deposit button
	class DepositHandler implements ActionListener {
		
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent
		  Returns:

		  Description: reacts to the action performed
		*/
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.deposit(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, _msgDepositSuccessful);
			} else {
				JOptionPane.showMessageDialog(null, _msgDepositUnseccessful);
			}		
		}
	}
	
	// Complete a handler for deposit button
	class WithdrawHandler implements ActionListener {
		
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent
		  Returns:

		  Description: reacts to the action performed
		*/
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.withdraw(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, _msgWithdralSuccessful);
			} else {
				JOptionPane.showMessageDialog(null, _msgWithdrawalUnseccessful);
			}		
		}
	}
	
	//** Complete a handler for the Frame that terminates 
	//** (System.exit(1)) on windowClosing event

	static class FrameHandler extends WindowAdapter {
		
		/**
		  Method: windowClosing
		  Inputs: closing event
		  Returns:

		  Description: reacts to the closing event
		*/
		public void windowClosing(WindowEvent e) {

			System.exit(0);
		}
	}
}

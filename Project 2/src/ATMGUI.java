/*
* File: ATMGUI.java
* Author: John Kucera
* Date: April 10, 2019
* Purpose: This java program implements an ATM machine, producing a Java GUI
* that can Withdraw from, Deposit into, Transfer currency among, and show 
* Balance of a Checking account and a Savings account.
*/

// import of appropriate Java classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class ATMGUI extends JFrame {
    // Instance Variables
    private static JButton withdrawBtn = new JButton("Withdraw");
    private static JButton depositBtn = new JButton("Deposit");
    private static JButton transferBtn = new JButton("Transfer to");
    private static JButton balanceBtn = new JButton ("Balance");
    private static JTextField amountTxt = new JTextField("");
    private static JRadioButton checkingRad = new JRadioButton("Checking");
    private static JRadioButton savingsRad = new JRadioButton("Savings");
    private static JOptionPane frame = new JOptionPane();
    private ButtonGroup acctTypeBtns = new ButtonGroup();
    
    // Creating Account objects
    public static Account checkingAcct = new Account(). new Checking();
    public static Account savingsAcct = new Account(). new Savings();
    
    // Creating Accounts
    public static void makeAccounts (double checkingStart, double savingsStart) {
        checkingAcct.setBalance(0.00);
        savingsAcct.setBalance(0.00);
    }
    
    // Handling checks
    public void errorValidNumber() {
        JOptionPane.showMessageDialog(frame, "Please enter desired amount in " +
                "US Dollars. Withdrawals must be in $20.00 "
                + "increments.");
    }
    
    // Clear input box
    public void clearInput() {
        amountTxt.setText("");
    }
    
    // Obtaining input
    public double getInput() {
        try {
            return Double.parseDouble(amountTxt.getText());
        }
        catch (NumberFormatException e) {
            System.out.println("Number Format Exception" + e.getMessage());
            clearInput();
            return 0;
        }
    }
    
    // Conversion for dollar amount
    private static DecimalFormat round = new DecimalFormat("$0.00");
    
    // Withdraw Listener
    class WithdrawBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (getInput() > 0 && (getInput() % 20) == 0) {
                    if (checkingRad.isSelected()) { // Checking
                        checkingAcct.withdraw(getInput());
                        JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been withdrawn"
                                + " from your Checking account.");
                    }
                    else if (savingsRad.isSelected()) { // Savings
                        savingsAcct.withdraw(getInput());
                        JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been withdrawn"
                                + " from your Savings account.");
                    }
                    clearInput();
                }
                else {
                    errorValidNumber();
                    clearInput();
                }
            }
            catch (InsufficientFunds ex) {
                JOptionPane.showMessageDialog(frame, "Insufficient Funds. " +
                "Please check your Balance.");
            }
        }
    } // End of Withdraw Listener
    
    // Deposit Listener
    class DepositBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (getInput() > 0) {
                if (checkingRad.isSelected()) { // Checking
                    checkingAcct.deposit(getInput());
                    JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been deposited"
                                + " into your Checking account.");
                }
                else if (savingsRad.isSelected()) { // Savings
                        savingsAcct.deposit(getInput());
                        JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been deposited"
                                + " into your Savings account.");
                    }
                    clearInput();
                }
            else {
                errorValidNumber();
                clearInput();
            }
        }
    } // End of Deposit Listener
    
    // Transfer Listener
    class TransferToBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (getInput() > 0) {
                    if (checkingRad.isSelected()) { // Checking
                        savingsAcct.transferFrom(getInput());
                        checkingAcct.transferTo(getInput());
                        JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been transferred"
                                + " from your Savings account to your Checking"
                                + " account.");
                    }
                    else if (savingsRad.isSelected()) { // Savings
                        checkingAcct.transferFrom(getInput());
                        savingsAcct.transferTo(getInput());
                        JOptionPane.showMessageDialog(frame, 
                                round.format(getInput()) + " has been transferred"
                                + " from your Checking account to your Savings"
                                + " account.");
                    }
                    clearInput();
                }
                else {
                    errorValidNumber();
                    clearInput();
                }
            }
            catch (InsufficientFunds ex) {
                JOptionPane.showMessageDialog(frame, "Insufficient Funds. " +
                "Please check your Balance.");
            }
        }
    } // End of Transfer Listener
    
    // Balance Listener
    class BalanceBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (checkingRad.isSelected()) { // Checking
                JOptionPane.showMessageDialog(frame, "The Balance of your "
                        + "Checking Account is: " 
                        + round.format(checkingAcct.getBalance()));
            } 
            else if (savingsRad.isSelected()) { // Savings
                JOptionPane.showMessageDialog(frame, "The Balance of your "
                        + "Savings Account is: " 
                        + round.format(savingsAcct.getBalance()));
            } 
            else {
                errorValidNumber();
                clearInput();
            }
        }
    } // End of Balance Listener
    
    // Creating GUI
    public ATMGUI() {
        // Frame creation
        super("John's ATM"); // Titling frame
        setLayout(new GridBagLayout());
        GridBagConstraints layout = new GridBagConstraints();
        setSize(300, 200);
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel txtPanel = new JPanel();
        JPanel btnPanel = new JPanel();
        layout.gridy = 2;
        
        // Buttons
        add(btnPanel);
        btnPanel.setLayout(new GridLayout(3, 2, 10, 10));
        btnPanel.add(withdrawBtn);
        withdrawBtn.addActionListener(new WithdrawBtnListener());
        btnPanel.add(depositBtn);
        depositBtn.addActionListener(new DepositBtnListener());
        btnPanel.add(transferBtn);
        transferBtn.addActionListener(new TransferToBtnListener());
        btnPanel.add(balanceBtn);
        balanceBtn.addActionListener(new BalanceBtnListener());
        acctTypeBtns.add(checkingRad);
        btnPanel.add(checkingRad);
        acctTypeBtns.add(savingsRad);
        btnPanel.add(savingsRad);
        checkingRad.setSelected(true);
        
        // Input Box
        add(txtPanel, layout);
        txtPanel.setLayout(new GridLayout(1, 1));
        txtPanel.add(amountTxt);
        amountTxt.setPreferredSize(new Dimension(225, 25));
        
        // Account creation
        makeAccounts(0.00, 0.00);

    } // End of GUI Creation
    
    // Displaying GUI
    public void display() {
        setVisible(true);
    }
    
    // Main method
    public static void main(String[] args) {
        ATMGUI newAcct = new ATMGUI();
        newAcct.display();
    } // End of main method
} // End of class

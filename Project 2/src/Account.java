/*
* File: Account.java
* Author: John Kucera
* Date: April 10, 2019
* Purpose: This java program is meant to accompany ATMGUI.java. It constructs
* Account objects and provides methods for handling the four buttons in the GUI 
* and the $1.50 service charge.
*/

// import of necessary Java classes
import javax.swing.*;

public class Account {
    // Instance variables
    private static int numWithdrawals;
    private double balance;
    private final double FEE = 1.50;
    
    // Default constructor
    public Account() {
        
    }
    
    // Constructors, using subclasses for checking and savings
    public class Checking extends Account {
        public Checking() {
            
        }
    }
    public class Savings extends Account {
        public Savings() {
            
        }
    }
    
    // Getters and Setters
    public int getNumWithdrawals() {
        return numWithdrawals;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setNumWithdrawals(int numWithdrawals) {
        this.numWithdrawals = numWithdrawals;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    // Button Methods
    // Withdraw with Fee check
    public void withdraw(double amount) throws InsufficientFunds {
        if (this.balance - amount < 0 || ((numWithdrawals >= 4) && 
                ((this.balance - amount - FEE) < 0))) {
            throw new InsufficientFunds();
        }
        numWithdrawals++;
        if (numWithdrawals == 4) {
            JOptionPane.showMessageDialog(null, "After 4 withdrawals are made, "
            + "a $1.50 Service Fee is charged from the selected Account per"
            + " Withdrawal.");
            this.balance -= amount;
        }
        else if (numWithdrawals > 4) {
            JOptionPane.showMessageDialog(null, "$1.50 has been charged to the "
            + "selected Account.");
            this.balance = (this.balance - amount) - FEE;
        }
        else {
            this.balance -= amount;
        }
    }
    // Deposit
    public void deposit(double amount) {
        this.balance += amount;
    }
    // Transfer from
    public void transferFrom(double amount) throws InsufficientFunds {
        if (this.balance - amount < 0) {
            throw new InsufficientFunds();
        }
        this.balance -= amount;
    }
    // Transfer to
    public void transferTo(double amount) {
        this.balance += amount;
    }
} // end of class

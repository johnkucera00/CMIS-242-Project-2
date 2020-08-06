/*
* File: InsufficientFunds.java
* Author: John Kucera
* Date: April 10, 2019
* Purpose: This java program is meant to accompany ATMGUI.java. It is a user
* defined checked exception that handles situations where withdrawals or
* transfers are attempted when there are not enough funds in the account.
*/

public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
        super();
    }
}

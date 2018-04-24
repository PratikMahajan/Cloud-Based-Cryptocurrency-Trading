/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;

/**
 *
 * @author Ankit
 */
public class TransactionList {
    
    private ArrayList<Transaction> transactionList;
    
    public TransactionList()
    {
        transactionList = new ArrayList<Transaction>();
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }
    
    public Transaction addTransaction(){
        Transaction transaction = new Transaction();
        transactionList.add(transaction);
        return transaction;
    }
}

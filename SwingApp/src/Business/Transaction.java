/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;


public class Transaction {
    private static int count = 1;
    private int transactionId;
    private String transactiondate;
    private String buyer;
    private String seller;
    private float coinQuantity;
    private float amount;
    
    public Transaction(){
        transactionId = count;
        count++;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public float getCoinQuantity() {
        return coinQuantity;
    }

    public void setCoinQuantity(float coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
   
}

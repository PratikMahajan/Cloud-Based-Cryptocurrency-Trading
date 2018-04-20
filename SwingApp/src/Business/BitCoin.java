/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Ankit
 */
public class BitCoin {
    
    private static int unit = 100;
    private static int price = 100;
    public  int        bitcoinUnit;
    public  double     bitcoinPrice;
    
    public BitCoin(){
        bitcoinUnit = unit;
        bitcoinPrice = price; 
    }

    public int getBitcoinUnit() {
        return bitcoinUnit;
    }

    public void setBitcoinUnit(int bitcoinUnit) {
        this.bitcoinUnit = bitcoinUnit;
    }

    public double getBitcoinPrice() {
        return bitcoinPrice;
    }

    public void setBitcoinPrice(double bitcoinPrice) {
        this.bitcoinPrice = bitcoinPrice;
    }    
}

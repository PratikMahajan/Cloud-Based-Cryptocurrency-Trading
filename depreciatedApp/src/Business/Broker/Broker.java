/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Broker;

import java.util.ArrayList;

/**
 *
 * @author mahajan
 */
public class Broker {
    
    
    private String brokerName;
    private String brokerId;
    private static int counter = 345001;
    
    public Broker()
    {
        counter++;
        this.brokerId = String.valueOf(counter);
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerId() {
        return brokerId;
    }
    
    
    @Override
    public String toString()
    {
        return this.brokerName;
    }
    
    
}

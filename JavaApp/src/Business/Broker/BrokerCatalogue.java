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
public class BrokerCatalogue {
    
    private ArrayList<Broker> brokerCatalogue;

    public BrokerCatalogue()
    {
        this.brokerCatalogue= new ArrayList<Broker>();
    }
    
    public BrokerCatalogue(ArrayList<Broker> brk)
    {
        this.brokerCatalogue = brk;
    }
    
    public ArrayList<Broker> getBrokerCatalogue() {
        return brokerCatalogue;
    }
    
    public void addBroker(Broker brk)
    {
        this.brokerCatalogue.add(brk);
    }
    
    public void removeBroker(Broker brk)
    {
        this.brokerCatalogue.remove(brk);
    }
    
    
}

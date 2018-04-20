/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Accounts.UserAccountDirectory;
import Business.Broker.BrokerCatalogue;
import Business.Customer.CustomerCatalogue;

/**
 *
 * @author mahajan
 */
public class Admin {
    
    public static Admin          masterClass;
    private BrokerCatalogue      brokerCatalogue;
    private CustomerCatalogue    customerCatalogue;
    private UserAccountDirectory userAccountDirectory;
    
    private Admin()
    {
        this.brokerCatalogue      = new BrokerCatalogue();
        this.customerCatalogue    = new CustomerCatalogue();
        this.userAccountDirectory = new UserAccountDirectory();
    }
    
    public static Admin getInstance() {
        if (masterClass == null) {
            masterClass = new Admin();
        }
        return masterClass;
    }

    
    public BrokerCatalogue getBrokerCatalogue() {
        return brokerCatalogue;
    }

    
    public CustomerCatalogue getCustomerCatalogue() {
        return customerCatalogue;
    }

    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    
    public void setUserAccountDirectory(UserAccountDirectory userAccountDirectory) {
        this.userAccountDirectory = userAccountDirectory;
    }
    
    
}


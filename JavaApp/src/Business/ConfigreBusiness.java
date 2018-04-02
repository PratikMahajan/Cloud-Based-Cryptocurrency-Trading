/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Accounts.Person;
import Business.Accounts.UserAccount;
import Business.Broker.Broker;
import Business.Customer.Customer;

/**
 *
 * @author karanracca
 */
public class ConfigreBusiness {

    public static Admin intialize() {

        Admin msc = Admin.getInstance();
        
        //Adding Admin
        Person p = new Person();
        p.setFirstName("Admin");
        p.setLastName("Admin");
        UserAccount ua = new UserAccount("admin", "admin", UserAccount.ADMIN_ROLE, p);
        msc.getUserAccountDirectory().addUserAccount(ua);
        
        Customer per = new Customer("08-21-1995", "Tom","02115");
        msc.getCustomerCatalogue().addCustomer(per);
        UserAccount uac = new UserAccount("cust", "cust", UserAccount.CUSTOMER_ROLE, per);
        msc.getUserAccountDirectory().addUserAccount(uac);
        
        return msc;
    }
}

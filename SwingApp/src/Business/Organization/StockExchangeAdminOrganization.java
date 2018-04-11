/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.StockExchangeAdminRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Ankit
 */
public class StockExchangeAdminOrganization extends Organization{

    public StockExchangeAdminOrganization() {
        super(Type.StockExchange_Admin.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new StockExchangeAdminRole());
        return roles;
    }
     
}

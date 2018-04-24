/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import userinterface.StockExchangeAdminRole.StockExchangeAdminWorkAreaJPanel;
import javax.swing.JPanel;


public class StockExchangeAdminRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new StockExchangeAdminWorkAreaJPanel(userProcessContainer, enterprise);
        //return new StockExchangeAdminWorkAreaJPanel(userProcessContainer, enterprise);
    }  
}

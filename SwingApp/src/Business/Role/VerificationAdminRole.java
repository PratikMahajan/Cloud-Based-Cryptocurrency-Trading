/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import userinterface.StockExchangeAdminRole.StockExchangeAdminWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author kalsara.a
 */
public class VerificationAdminRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new StockExchangeAdminWorkAreaJPanel(userProcessContainer, enterprise);
        //return new VerificationAdminWorkAreaJPanel(userProcessContainer, enterprise);
    }  
}  
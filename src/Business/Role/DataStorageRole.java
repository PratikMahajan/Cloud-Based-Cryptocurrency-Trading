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
import userinterface.BrokerRole.BrokerWorkAreaJPanel;
import javax.swing.JPanel;


public class DataStorageRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new BrokerWorkAreaJPanel(userProcessContainer, account, organization, business);
        //return new DataStorageWorkAreaJPanel(userProcessContainer, account, (DataStorageOrganization)organization, enterprise);
    }   
} 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.json.JSONException;
import userinterface.CustomerRole.CustomerMainPanel;


public class CustomerRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        try {
            //return new CustomerWorkAreaJPanel(userProcessContainer, account, (CustomerOrganization)organization, enterprise);
            return new CustomerMainPanel(userProcessContainer, account, (CustomerOrganization)organization, enterprise);
            //return new CustomerWorkAreaJPanel(userProcessContainer, account, (CustomerOrganization)organization, enterprise);
        } catch (IOException ex) {
            Logger.getLogger(CustomerRole.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerRole.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}

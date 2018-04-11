/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.BrokerRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
* @author Ankit
 */
public class BrokerOrganization extends Organization{

    public BrokerOrganization() {
        super(Organization.Type.Broker.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new BrokerRole());
        return roles;
    }    
}

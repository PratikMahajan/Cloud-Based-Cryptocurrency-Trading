/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.ExchangeRole;
import Business.Role.Role;
import java.util.ArrayList;


public class ExchangeOrganization extends Organization {
    
    public ExchangeOrganization() {
        super(Organization.Type.Exchange.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new ExchangeRole());
        return roles;
    }  
}

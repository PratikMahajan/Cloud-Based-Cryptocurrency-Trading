/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.Role;
import Business.Role.VerificationAdminRole;
import java.util.ArrayList;


public class VerificationAdminOrganization extends Organization{

    public VerificationAdminOrganization() {
        super(Type.Verification_Admin.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new VerificationAdminRole());
        return roles;
    }    
}

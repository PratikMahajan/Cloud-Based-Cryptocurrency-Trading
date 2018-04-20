/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.DocumentVerificationRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author kalsara.a
 */
public class DocumentVerificationOrganization extends Organization{

    public DocumentVerificationOrganization() {
        super(Organization.Type.Document_Verification.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new DocumentVerificationRole());
        return roles;
    }    
}

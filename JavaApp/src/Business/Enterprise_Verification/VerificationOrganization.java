/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise_Verification;

import java.util.ArrayList;

/**
 *
 * @author Ankit
 */
public class VerificationOrganization extends Organization {
    
        public VerificationOrganization() {
        super(Organization.Type.VerificationAssistant.getValue());
    }

    @Override
    public
    ArrayList<Role> getSupportedRole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

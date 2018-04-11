/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise_Verification;

import javax.swing.JPanel;

/**
 *
 * @author Ankit
 */
public abstract class Role {
    
        public enum RoleType{
            
        Admin("Admin Role"),
        VerificationAssistant("Verification Assistant Role");
        
        private String value;
        
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
//NEEDS TO BE UNCOMMENTED BACK    
//public abstract JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

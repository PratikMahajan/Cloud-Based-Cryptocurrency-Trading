/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.UserAccount;

/**
 *
 * @author mahajan
 */

public class MyAwsCredentials {
    
    private static String AccessKeyID="***********************";   
    private static String SecretAccessKey="****************************";

    public static String getAccessKeyID() {
        return AccessKeyID;
    }

    

    public static String getSecretAccessKey() {
        return SecretAccessKey;
    }
    
    
    
}

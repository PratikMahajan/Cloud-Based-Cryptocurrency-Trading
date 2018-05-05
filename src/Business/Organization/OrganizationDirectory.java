/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;


public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
        if (type.getValue().equals(Type.Customer.getValue())){
            organization = new CustomerOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Broker.getValue())){
            organization = new BrokerOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Exchange.getValue())){
            organization = new ExchangeOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Data_Storage.getValue())){
            organization = new DataStorageOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.StockExchange_Admin.getValue())){
            organization = new StockExchangeAdminOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Verification_Admin.getValue())){
            organization = new VerificationAdminOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Document_Verification.getValue())){
            organization = new DocumentVerificationOrganization();
            organizationList.add(organization);
        }
        return organization;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author kalsara.a
 */
public class StockExchangeEnterprise extends Enterprise {
    
    public StockExchangeEnterprise(String name){
        super(name,EnterpriseType.StockExchange);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Customer;

/**
 *
 * @author mahajan
 */
public class Customer 
{
    private String custName;
    private String custDOB;
    private String customerId;
    private static int counter=3874001;
    
    public Customer()
    {
        counter++;
        this.customerId=String.valueOf(counter);
        
    }
    
    public Customer(String DOB, String Name, String zipC)
    {
        this.custName=Name;
        this.custDOB=DOB;
        counter++;
        this.customerId=String.valueOf(counter);
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustDOB() {
        return custDOB;
    }

    public void setCustDOB(String custDOB) {
        this.custDOB = custDOB;
    }

    public String getCustomerId() {
        return customerId;
    }
    
    
    @Override
    public String toString()
    {
        return this.custName;
    }
    
}

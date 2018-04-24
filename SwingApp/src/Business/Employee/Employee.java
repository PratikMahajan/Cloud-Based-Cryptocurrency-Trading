/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Employee;


public class Employee {
    
    private String     firstName;
    private String     lastName;
    private int        id;
    private static int count = 1;
    private boolean    verifiedUser = false;
    private String     usraddress;

    public Employee() {
        id = count;
        count++;
    }

    public String getUsraddress() {
        return usraddress;
    }

    public void setUsraddress(String usraddress) {
        this.usraddress = usraddress;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isVerifiedUser() {
        return verifiedUser;
    }

    public void setVerifiedUser(boolean verifiedUser) {
        this.verifiedUser = verifiedUser;
    }

    @Override
    public String toString() {
        return firstName + ", " +  lastName;
    }

}
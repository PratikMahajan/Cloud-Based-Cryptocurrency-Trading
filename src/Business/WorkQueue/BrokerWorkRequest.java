/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

/**
 *
 * @author Ankit
 */
public class BrokerWorkRequest extends WorkRequest{
    
    private String     testResult;
    private int        requestNumber;
    private static int count = 10;
    
    public BrokerWorkRequest(){
        requestNumber = count;
        count++;
    }

    @Override
    public int getRequestNumber() {
        return requestNumber;
    }

    @Override
    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }
    
    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.CustomerRole;

import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.UserAccount.UserAccount;
import Business.UserAccount.MyAwsCredentials;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mahajan
 */



public class ManageAccount extends javax.swing.JPanel  {

    /**
     * Creates new form AddMoney
     */
    
    private JPanel               userProcessContainer;
    private CustomerOrganization organization;
    private Enterprise           enterprise;
    private UserAccount          userAccount;
    
    private String imagepath;
    
    
    public ManageAccount(JPanel userProcessContainer, UserAccount account, CustomerOrganization organization, Enterprise enterprise) throws IOException, JSONException, InterruptedException, InvocationTargetException {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.organization         = organization;
        this.enterprise           = enterprise;
        this.userAccount          = account;
        LblName.setText("Welcome");
        
        setInvestments();
        getPrice();
        
        
    }
    
    
    private void setInvestments()
    {
        double usrcoins= userAccount.getEmployee().getWl().getCoins();
        double usrdollars=userAccount.getEmployee().getWl().getDollars();
        
        lblDollarData.setText(Double.toString(usrdollars));
        lblCoinData.setText(Double.toString(usrcoins));
        
    }
   
    
  private void uploadtoS3()
  {
        String bucketName     = "aedprojectvalidate";
        String keyName        = "demoupload2"; // filename of the file uploaded
	String uploadFileName = imagepath; //file to be uploaded from computer
        
        MyAwsCredentials as= new MyAwsCredentials();
        
        
        AWSCredentials credentials = new BasicAWSCredentials(as.getAccessKeyID(), as.getSecretAccessKey());
        
        
        
        AmazonS3 s3client = new AmazonS3Client(credentials);
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(uploadFileName);
            s3client.putObject(new PutObjectRequest(
            		                 bucketName, keyName, file));

         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        
        
  }
    
 
    private void getPrice() throws IOException, JSONException
    {
       try {

//		URL url = new URL("http://127.0.0.1:5000/dynamicPrice");
                URL url = new URL("http://aedstock.herokuapp.com/dynamicPrice");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
                output = br.readLine();
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
                output = output.replace("[", "").replace("]", "");
                JSONObject obj = new JSONObject(output);
                Double price = obj.getDouble("Price");
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
                String pr=price.toString();
//                stockprice=pr.substring(0, Math.min(pr.length(), 7));
                lblPriceData.setText(pr.substring(0, Math.min(pr.length(), 7)));
		Double change= ((price-400.00)/4.00);
                String chg=change.toString();
                String changel= chg.substring(0, Math.min(pr.length(), 4));
                lblChangeData.setText(changel+" %");
//                changep=changel+" %";
                conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	

    
        
        
    
}
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LblName = new javax.swing.JLabel();
        lblCoinIndex = new javax.swing.JLabel();
        lblPriceName = new javax.swing.JLabel();
        lblPriceData = new javax.swing.JLabel();
        lblChangeData = new javax.swing.JLabel();
        lblChangeName = new javax.swing.JLabel();
        lblInvestmentName = new javax.swing.JLabel();
        lblDollarsName = new javax.swing.JLabel();
        lblCoinName = new javax.swing.JLabel();
        lblCoinData = new javax.swing.JLabel();
        lblDollarData = new javax.swing.JLabel();
        btnback = new javax.swing.JButton();
        btnSelectImg = new javax.swing.JButton();
        txtSelectedPath = new javax.swing.JTextField();
        btnUpload = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        LblName.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblName.setText("...");

        lblCoinIndex.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblCoinIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoinIndex.setText("Coin Index");
        lblCoinIndex.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPriceName.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblPriceName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPriceName.setText("Price");
        lblPriceName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPriceData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPriceData.setText("NULL");
        lblPriceData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblChangeData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangeData.setText("NULL");
        lblChangeData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblChangeName.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblChangeName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangeName.setText("Change %");
        lblChangeName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblInvestmentName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblInvestmentName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvestmentName.setText("My Investments");
        lblInvestmentName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDollarsName.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblDollarsName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDollarsName.setText("Dollars");
        lblDollarsName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCoinName.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblCoinName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoinName.setText("Coins");
        lblCoinName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCoinData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoinData.setText("NULL");
        lblCoinData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblDollarData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDollarData.setText("NULL");
        lblDollarData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnback.setText("Back");
        btnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackActionPerformed(evt);
            }
        });

        btnSelectImg.setText("Select Image");
        btnSelectImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectImgActionPerformed(evt);
            }
        });

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh Feed");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(426, 426, 426)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCoinName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDollarsName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDollarData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCoinData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblInvestmentName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPriceData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblChangeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblChangeData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblCoinIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(58, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpload)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnback)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(189, 189, 189)
                            .addComponent(btnSelectImg)
                            .addGap(18, 18, 18)
                            .addComponent(txtSelectedPath, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LblName, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(lblCoinIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblChangeData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInvestmentName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDollarsName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDollarData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCoinName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCoinData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSelectImg)
                            .addComponent(txtSelectedPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpload)
                        .addGap(127, 127, 127)
                        .addComponent(btnback))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnbackActionPerformed

    private void btnSelectImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectImgActionPerformed
        // TODO add your handling code here:
        JFileChooser openImage = new JFileChooser();
        openImage.showOpenDialog(null);
        File image= openImage.getSelectedFile();
        String imgPath= image.getAbsolutePath();
        imagepath=imgPath;
        txtSelectedPath.setText(imgPath);
          
        
    }//GEN-LAST:event_btnSelectImgActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        // TODO add your handling code here:
        uploadtoS3();
        
        
    }//GEN-LAST:event_btnUploadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:

            getPrice();
            setInvestments();
        } catch (IOException ex) {
            Logger.getLogger(ManageAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ManageAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblName;
    private javax.swing.JButton btnSelectImg;
    private javax.swing.JButton btnUpload;
    private javax.swing.JButton btnback;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblChangeData;
    private javax.swing.JLabel lblChangeName;
    private javax.swing.JLabel lblCoinData;
    private javax.swing.JLabel lblCoinIndex;
    private javax.swing.JLabel lblCoinName;
    private javax.swing.JLabel lblDollarData;
    private javax.swing.JLabel lblDollarsName;
    private javax.swing.JLabel lblInvestmentName;
    private javax.swing.JLabel lblPriceData;
    private javax.swing.JLabel lblPriceName;
    private javax.swing.JTextField txtSelectedPath;
    // End of variables declaration//GEN-END:variables
}

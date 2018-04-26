/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.CustomerRole;


import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.UserAccount.UserAccount;
import com.amazonaws.services.config.model.AggregatedSourceType;
import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mahajan
 */
public class CustomerMainPanel extends javax.swing.JPanel {

    /**
     * Creates new form CustomerMainPanel
     */
    
    private JPanel               userProcessContainer;
    private CustomerOrganization organization;
    private Enterprise           enterprise;
    private UserAccount          userAccount;
    private String addr;
    public CustomerMainPanel(JPanel userProcessContainer, UserAccount account, CustomerOrganization organization, Enterprise enterprise) throws IOException, JSONException {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.organization         = organization;
        this.enterprise           = enterprise;
        this.userAccount          = account;
        LblName.setText("Welcome " + userAccount.getEmployee().getFirstName() + " " + userAccount.getEmployee().getLastName() );
        
        addr="adr"+userAccount.getEmployee().getFirstName()+userAccount.getEmployee().getLastName();
        
        getPrice();
        setInvestments();
        setStatus();
        
        //enable buy and sell button only for verified user
        if(userAccount.getEmployee().isVerifiedUser()){
            btnSellCoins.setEnabled(true);
            btnBuyCoins.setEnabled(true);
        }
    }

     
    private void postBuyMatch() throws IOException, JSONException
    {
       try {

		URL url = new URL("http://aedstock.herokuapp.com/buymatched");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                
		String input = "{\"address\":\""+addr+"\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
                
                if(conn.getResponseCode() == 200){
                    System.out.println("Response positive");
                }
		if (conn.getResponseCode() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn.getResponseCode());
                        System.out.println("none");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
//
		String output;
                output = br.readLine();
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
                output = output.replace("[", "").replace("]", "");
                JSONObject obj = new JSONObject(output);
                double quant = obj.getDouble("quantity");
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                double usrcoins= userAccount.getEmployee().getWl().getCoins();
                userAccount.getEmployee().getWl().setCoins(usrcoins+quant);
		
                setInvestments();
                
                conn.disconnect();
                
                URL url2 = new URL("http://aedstock.herokuapp.com/delbuy");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		conn2.setRequestMethod("POST");
		conn2.setRequestProperty("Content-Type", "application/json");
                conn2.setDoOutput(true);
                
		String input2 = "{\"address\":\""+addr+"\"}";

		OutputStream os2 = conn2.getOutputStream();
		os2.write(input2.getBytes());
		os2.flush();
                
                if(conn2.getResponseCode() == 200){
                    System.out.println("Response positive");
                }
		if (conn2.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn2.getResponseCode());
		}
                
                conn2.disconnect();
                

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
		

    }
    
    
    
    
    
    
    
    private void postSellMatch() throws IOException, JSONException
    {
       try {

		URL url = new URL("http://aedstock.herokuapp.com/sellmatched");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                
		String input = "{\"address\":\""+addr+"\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
                
                if(conn.getResponseCode() == 200){
                    System.out.println("Response positive");
                }
		if (conn.getResponseCode() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn.getResponseCode());
                        System.out.println("none");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
//
		String output;
                output = br.readLine();
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
                output = output.replace("[", "").replace("]", "");
                JSONObject obj = new JSONObject(output);
                double quant = obj.getDouble("quantity");
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                double usrcoins= userAccount.getEmployee().getWl().getCoins();
                userAccount.getEmployee().getWl().setCoins(usrcoins-quant);
		
                setInvestments();
                
                conn.disconnect();
                
                URL url2 = new URL("http://aedstock.herokuapp.com/delsel");
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		conn2.setRequestMethod("POST");
		conn2.setRequestProperty("Content-Type", "application/json");
                conn2.setDoOutput(true);
                
		String input2 = "{\"address\":\""+addr+"\"}";

		OutputStream os2 = conn2.getOutputStream();
		os2.write(input2.getBytes());
		os2.flush();
                
                if(conn2.getResponseCode() == 200){
                    System.out.println("Response positive");
                }
		if (conn2.getResponseCode() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn2.getResponseCode());
                        System.out.println("none");
		}
                
                conn2.disconnect();
                

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
		

    }
   
   
    
     private void setInvestments()
    {
        double usrcoins= userAccount.getEmployee().getWl().getCoins();
        double usrdollars=userAccount.getEmployee().getWl().getDollars();
        
        lblDollarData.setText(Double.toString(usrdollars));
        lblCoinData.setText(Double.toString(usrcoins));
        
    }
    
    
    private void getPrice() throws IOException, JSONException
    {
       try {

		URL url = new URL("http://aedstock.herokuapp.com/dynamicPrice");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn.getResponseCode());
                        System.out.println("none");
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
                lblPriceData.setText(pr.substring(0, Math.min(pr.length(), 7)));
		Double change= ((price-400.00)/4.00);
                String chg=change.toString();
                String changel= chg.substring(0, Math.min(pr.length(), 4));
                lblChangeData.setText(changel+" %");
                conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
    }
    
    
    
    private void setStatus() throws JSONException
    {
        
        try {

		URL url = new URL("http://aedstock.herokuapp.com/recVerify");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                
		String input = "{\"address\":\""+addr+"\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
                
                if(conn.getResponseCode() == 200){
                    System.out.println("Response positive");
                }
		if (conn.getResponseCode() != 200) {
			System.out.println("nada");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
//
		String output;
                output = br.readLine();
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
                output = output.replace("[", "").replace("]", "");
                JSONObject obj = new JSONObject(output);
                int price = obj.getInt("bool");
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                if (price==1)
                {
                   userAccount.getEmployee().setVerifiedUser(true);
                }
		
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

        DummyDel = new javax.swing.JButton();
        LblName = new javax.swing.JLabel();
        lblCoinIndex = new javax.swing.JLabel();
        lblChangeName = new javax.swing.JLabel();
        lblPriceName = new javax.swing.JLabel();
        lblPriceData = new javax.swing.JLabel();
        lblChangeData = new javax.swing.JLabel();
        lblInvestmentName = new javax.swing.JLabel();
        lblDollarsName = new javax.swing.JLabel();
        lblCoinName = new javax.swing.JLabel();
        lblCoinData = new javax.swing.JLabel();
        lblDollarData = new javax.swing.JLabel();
        btnAddMoney = new javax.swing.JButton();
        btnBuyCoins = new javax.swing.JButton();
        lblPplSaying = new javax.swing.JLabel();
        btnViewTransactions = new javax.swing.JButton();
        btnSellCoins = new javax.swing.JButton();
        manageAccountJBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        DummyDel.setText("Refresh Feed");
        DummyDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DummyDelActionPerformed(evt);
            }
        });

        LblName.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblName.setText("...");

        lblCoinIndex.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblCoinIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoinIndex.setText("Coin Index");
        lblCoinIndex.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblChangeName.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        lblChangeName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangeName.setText("Change %");
        lblChangeName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btnAddMoney.setText("Add Money");
        btnAddMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMoneyActionPerformed(evt);
            }
        });

        btnBuyCoins.setText("Buy Coins");
        btnBuyCoins.setEnabled(false);
        btnBuyCoins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyCoinsActionPerformed(evt);
            }
        });

        lblPplSaying.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblPplSaying.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPplSaying.setText("What are People Saying ");
        lblPplSaying.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnViewTransactions.setText("View Transactions");
        btnViewTransactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTransactionsActionPerformed(evt);
            }
        });

        btnSellCoins.setText("Sell Coins");
        btnSellCoins.setEnabled(false);
        btnSellCoins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSellCoinsActionPerformed(evt);
            }
        });

        manageAccountJBtn.setText("Manage My Account");
        manageAccountJBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageAccountJBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel1.setText("Buying and Selling of coins is enabled only for verified users");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(726, 726, 726)
                        .addComponent(DummyDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPplSaying)
                                .addGap(251, 251, 251)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblCoinName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblDollarsName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lblDollarData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lblCoinData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(jLabel1))
                                            .addComponent(lblInvestmentName, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 30, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblPriceData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lblChangeName, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                                    .addComponent(lblChangeData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addComponent(lblCoinIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAddMoney)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuyCoins)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSellCoins)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnViewTransactions)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(manageAccountJBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblName, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPplSaying, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCoinIndex, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChangeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceData, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(lblChangeData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuyCoins, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSellCoins, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
<<<<<<< HEAD
                    .addComponent(manageAccountJBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 370, Short.MAX_VALUE)
=======
                    .addComponent(btnSellCoins1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
>>>>>>> remotes/origin/Mahajan
                .addComponent(DummyDel))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void DummyDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DummyDelActionPerformed
<<<<<<< HEAD
        // TODO add your handling code here:
        
        CustomerWorkAreaJPanel cm= new CustomerWorkAreaJPanel(userProcessContainer, userAccount, organization, enterprise);
        userProcessContainer.add("CWAJ",cm);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
=======
        try {
            // TODO add your handling code here:
            
            
            getPrice();
            setInvestments();
            postBuyMatch();
            postSellMatch();
            setStatus();
            
//        CustomerWorkAreaJPanel cm= new CustomerWorkAreaJPanel(userProcessContainer, userAccount, organization, enterprise);
//        userProcessContainer.add("CWAJ",cm);
//        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
//        layout.next(userProcessContainer);
//        
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
>>>>>>> remotes/origin/Mahajan
    }//GEN-LAST:event_DummyDelActionPerformed

    private void btnAddMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMoneyActionPerformed
        // TODO add your handling code here:
        AddMoney cm = null;
        try {
            cm = new AddMoney(userProcessContainer, userAccount, organization, enterprise);
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        userProcessContainer.add("add Money",cm);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnAddMoneyActionPerformed

    private void btnBuyCoinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyCoinsActionPerformed
        
        BuyCoins cm = null;
        try {
            cm = new BuyCoins(userProcessContainer, userAccount, organization, enterprise);
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        userProcessContainer.add("Buy Coins",cm);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnBuyCoinsActionPerformed

    private void btnViewTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTransactionsActionPerformed
        // TODO add your handling code here:
        TransactionsJPanel tjpl = new TransactionsJPanel(userProcessContainer, userAccount, organization, enterprise);
        userProcessContainer.add("Transactions Panel",tjpl);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewTransactionsActionPerformed

    private void btnSellCoinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSellCoinsActionPerformed
        
        SellCoins cm = null;
        try {
            cm = new SellCoins(userProcessContainer, userAccount, organization, enterprise);
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        userProcessContainer.add("Buy Coins",cm);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnSellCoinsActionPerformed

    private void manageAccountJBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageAccountJBtnActionPerformed
       
        ManageAccount cm = null;
        try {
            cm = new ManageAccount(userProcessContainer, userAccount, organization, enterprise);
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) { 
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        userProcessContainer.add("Manage Account",cm);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_manageAccountJBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DummyDel;
    private javax.swing.JLabel LblName;
    private javax.swing.JButton btnAddMoney;
    private javax.swing.JButton btnBuyCoins;
    private javax.swing.JButton btnSellCoins;
    private javax.swing.JButton btnViewTransactions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblChangeData;
    private javax.swing.JLabel lblChangeName;
    private javax.swing.JLabel lblCoinData;
    private javax.swing.JLabel lblCoinIndex;
    private javax.swing.JLabel lblCoinName;
    private javax.swing.JLabel lblDollarData;
    private javax.swing.JLabel lblDollarsName;
    private javax.swing.JLabel lblInvestmentName;
    private javax.swing.JLabel lblPplSaying;
    private javax.swing.JLabel lblPriceData;
    private javax.swing.JLabel lblPriceName;
    private javax.swing.JButton manageAccountJBtn;
    // End of variables declaration//GEN-END:variables
}

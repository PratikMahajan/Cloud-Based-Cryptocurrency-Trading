/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.CustomerRole;

import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.Transaction;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mahajan
 */
public class SellCoins extends javax.swing.JPanel {

    /**
     * Creates new form AddMoney
     */
    
    private JPanel               userProcessContainer;
    private CustomerOrganization organization;
    private Enterprise           enterprise;
    private UserAccount          userAccount;
    private String addr;
    
    public SellCoins(JPanel userProcessContainer, UserAccount account, CustomerOrganization organization, Enterprise enterprise) throws IOException, JSONException {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.organization         = organization;
        this.enterprise           = enterprise;
        this.userAccount          = account;

        LblName.setText("Sell Coins");
        
        addr="adr"+userAccount.getEmployee().getFirstName()+userAccount.getEmployee().getLastName();
          
        
        getPrice();
        setInvestments();
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
                String pr= obj.getString("price");
                double pricesell=Double.parseDouble(pr);
                double usrdollars=userAccount.getEmployee().getWl().getDollars();
                userAccount.getEmployee().getWl().setDollars(usrdollars-pricesell*quant);
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                double usrcoins= userAccount.getEmployee().getWl().getCoins();
                userAccount.getEmployee().getWl().setCoins(usrcoins+quant);
		
                
                
                String seller= obj.getString("recv_from");
                Transaction trans = userAccount.getEmployee().getTl().addTransaction();
                trans.setAmount((float)pricesell);
                trans.setCoinQuantity((float)quant);
                trans.setSeller(seller);
                trans.setBuyer(addr);
                trans.setTransactiondate(new Date().toString());
                
                
                
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

//		e.printStackTrace();

	  } catch (IOException e) {

//		e.printStackTrace();

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
                String pr= obj.getString("price");
                double pricesell=Double.parseDouble(pr);
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                double usrcoins= userAccount.getEmployee().getWl().getCoins();
                userAccount.getEmployee().getWl().setCoins(usrcoins-quant);
                double usrdollars=userAccount.getEmployee().getWl().getDollars();
                userAccount.getEmployee().getWl().setDollars(usrdollars+pricesell*quant);
		
                
                String seller= obj.getString("send_to");
                Transaction trans = userAccount.getEmployee().getTl().addTransaction();
                trans.setAmount((float)pricesell);
                trans.setCoinQuantity((float)quant);
                trans.setSeller(addr);
                trans.setBuyer(seller);
                trans.setTransactiondate(new Date().toString());
                
                
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

//		e.printStackTrace();

	  } catch (IOException e) {

//		e.printStackTrace();

	  }
		

    }
   
   
    
     private void setInvestments()
    {
        double usrcoins= userAccount.getEmployee().getWl().getCoins();
        double usrdollars=userAccount.getEmployee().getWl().getDollars();
        
        lblDollarData.setText(Double.toString(usrdollars));
        lblCoinData.setText(Double.toString(usrcoins));
        
    }
    
    
    private void getSellCoin() throws IOException, JSONException
    {
       try {

		URL url = new URL("http://aedstock.herokuapp.com/sell");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                
                int quant=Integer.parseInt(txtQuantity.getText());
                String amtt=lblPriceData.getText();
                
		String input = "{\"address\":\""+addr+"\",\"quantity\":"+quant+",\"amount\":\""+amtt+"\"}";

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

//		BufferedReader br = new BufferedReader(new InputStreamReader(
//			(conn.getInputStream())));
////
//		String output;
//                output = br.readLine();
////		while ((output = br.readLine()) != null) {
////			System.out.println(output);
////		}
//                output = output.replace("[", "").replace("]", "");
//                JSONObject obj = new JSONObject(output);
//                Double price = obj.getDouble("Price");
////                System.out.println(firstItem.getInt("id"));
////                System.out.println(price);
//                String pr=price.toString();

		
                conn.disconnect();

	  } catch (MalformedURLException e) {

//		e.printStackTrace();

	  } catch (IOException e) {

//		e.printStackTrace();

	  }
		

    }
    
    
    private void getPrice() throws IOException, JSONException
    {
       try {

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
                lblPriceData.setText(pr.substring(0, Math.min(pr.length(), 7)));
		Double change= ((price-400.00)/4.00);
                String chg=change.toString();
                String changel= chg.substring(0, Math.min(pr.length(), 4));
                lblChangeData.setText(changel+" %");
                conn.disconnect();

	  } catch (MalformedURLException e) {

//		e.printStackTrace();

	  } catch (IOException e) {

//		e.printStackTrace();

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
        jLabel1 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblpricediscl = new javax.swing.JLabel();

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

        jLabel1.setText("Enter Quantity of Coins:");

        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQuantityKeyTyped(evt);
            }
        });

        jButton1.setText("Sell");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Refresh Feed");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lblpricediscl.setText("jLabel2");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblpricediscl, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(txtQuantity))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                    .addComponent(lblCoinIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnback))
                        .addContainerGap(35, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(188, 188, 188))
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
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(lblpricediscl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(136, 136, 136)
                .addComponent(btnback)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnbackActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:

            getSellCoin();
            setInvestments();
            postBuyMatch();
            postSellMatch();
            lblpricediscl.setText("You're Selling Coins at "+lblPriceData.getText()+" $");
            JOptionPane.showMessageDialog(null, "Sell request sent", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(SellCoins.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(SellCoins.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtQuantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c))  //accept only digits
        {
           evt.consume();
        }
    }//GEN-LAST:event_txtQuantityKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:

            getPrice();
        } catch (IOException ex) {
            Logger.getLogger(SellCoins.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(SellCoins.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblName;
    private javax.swing.JButton btnback;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel lblpricediscl;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}

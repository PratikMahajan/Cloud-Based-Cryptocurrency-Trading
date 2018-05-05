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
import com.amazonaws.services.config.model.AggregatedSourceType;
//import com.amazonaws.services.iot.model.Status;
import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


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
    public CustomerMainPanel(JPanel userProcessContainer, UserAccount account, CustomerOrganization organization, Enterprise enterprise) throws IOException, JSONException, TwitterException {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.organization         = organization;
        this.enterprise           = enterprise;
        this.userAccount          = account;
        LblName.setText("Welcome "+ userAccount.getEmployee().getFirstName()+userAccount.getEmployee().getLastName());
        
        addr="adr"+userAccount.getEmployee().getFirstName()+userAccount.getEmployee().getLastName();
        
        getPrice();
        setInvestments();
        setStatus();
        twitterfeed();
        //enable buy and sell button only for verified user
        if(userAccount.getEmployee().isVerifiedUser()){
            btnAddMoney.setEnabled(true);
            btnBuyCoins.setEnabled(true);
        }
    }

    
    private static AccessToken loadAccessToken(){
    String token = "********************************************";// set twitter tokens 
    String tokenSecret = "****************************************";/// set twitter tokens 
    return new AccessToken(token, tokenSecret);
    }
    private void twitterfeed() throws TwitterException
    {
        System.out.println("Twitter Feed");
        AccessToken accessToken = loadAccessToken();
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("*********************");// set twitter tokens 
        builder.setOAuthConsumerSecret("***************************************");// set twitter tokens 
        Configuration configuration = builder.build();
        TwitterFactory factory = new TwitterFactory(configuration);
        Twitter twitter = factory.getInstance(accessToken);
        
        Query query = new Query("bitcoin");
        QueryResult result = twitter.search(query);
        int i=0;
        ArrayList<String> tweetu=new ArrayList<String>();
        for (Status status : result.getTweets()) {
//            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            tweetu.add(status.getText());
            if (i==5)
            {
                break;
            }
        }
        
        lbl1.setText(tweetu.get(0));
        lbl2.setText(tweetu.get(1));
        lbl3.setText(tweetu.get(2));
        lbl4.setText(tweetu.get(3));
        lbl5.setText(tweetu.get(4));
        
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
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn2.getResponseCode());
                         System.out.println("Response negative");
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
                double usrdollars=userAccount.getEmployee().getWl().getDollars();
                userAccount.getEmployee().getWl().setDollars(usrdollars+pricesell*quant);
//                System.out.println(firstItem.getInt("id"));
//                System.out.println(price);
//                String pr=price.toString();
                double usrcoins= userAccount.getEmployee().getWl().getCoins();
                userAccount.getEmployee().getWl().setCoins(usrcoins-quant);
		
                
                
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

//		e.printStackTrace();

	  } catch (IOException e) {

//		e.printStackTrace();

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
                else
                {
                   userAccount.getEmployee().setVerifiedUser(false);
                }
		
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
        btnSellCoins1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbl1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbl2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        lbl3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        lbl4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        lbl5 = new javax.swing.JTextArea();

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
        btnAddMoney.setEnabled(false);
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

        btnSellCoins1.setText("Sell Coins");
        btnSellCoins1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSellCoins1ActionPerformed(evt);
            }
        });

        jButton1.setText("Manage Account");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lbl1.setColumns(5);
        lbl1.setRows(5);
        jScrollPane1.setViewportView(lbl1);

        lbl2.setColumns(20);
        lbl2.setRows(5);
        jScrollPane2.setViewportView(lbl2);

        lbl3.setColumns(20);
        lbl3.setRows(5);
        jScrollPane3.setViewportView(lbl3);

        lbl4.setColumns(20);
        lbl4.setRows(5);
        jScrollPane4.setViewportView(lbl4);

        lbl5.setColumns(20);
        lbl5.setRows(5);
        jScrollPane5.setViewportView(lbl5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPplSaying, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane3)
                                    .addComponent(jScrollPane5))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(78, 78, 78)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblCoinName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(lblDollarsName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(lblDollarData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(lblCoinData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(lblInvestmentName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnAddMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(lblPriceData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblChangeData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(lblChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(btnBuyCoins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(btnSellCoins1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lblCoinIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(206, 206, 206)
                                        .addComponent(DummyDel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblName, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblInvestmentName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCoinIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDollarsName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDollarData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPriceName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCoinName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCoinData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPriceData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblChangeData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DummyDel)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuyCoins, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSellCoins1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPplSaying, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void DummyDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DummyDelActionPerformed
        try {
            // TODO add your handling code here:
            
            
            getPrice();
            setInvestments();
            postBuyMatch();
            postSellMatch();
            setStatus();
            twitterfeed();
//        CustomerWorkAreaJPanel cm= new CustomerWorkAreaJPanel(userProcessContainer, userAccount, organization, enterprise);
//        userProcessContainer.add("CWAJ",cm);
//        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
//        layout.next(userProcessContainer);
//        
        } catch (IOException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(CustomerMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_DummyDelActionPerformed

    private void btnAddMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMoneyActionPerformed
        // TODO add your handling code here:
        AddMoney cm=null;
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
        
        BuyCoins cm=null;
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
        TransactionsJPanel tjpl = new TransactionsJPanel(userProcessContainer, userAccount, organization, enterprise, addr);
        userProcessContainer.add("Transactions Panel",tjpl);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewTransactionsActionPerformed

    private void btnSellCoins1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSellCoins1ActionPerformed
        
        
        SellCoins cm=null;
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
        
    }//GEN-LAST:event_btnSellCoins1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        ManageAccount cm=null;
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
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DummyDel;
    private javax.swing.JLabel LblName;
    private javax.swing.JButton btnAddMoney;
    private javax.swing.JButton btnBuyCoins;
    private javax.swing.JButton btnSellCoins1;
    private javax.swing.JButton btnViewTransactions;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea lbl1;
    private javax.swing.JTextArea lbl2;
    private javax.swing.JTextArea lbl3;
    private javax.swing.JTextArea lbl4;
    private javax.swing.JTextArea lbl5;
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
    // End of variables declaration//GEN-END:variables
}

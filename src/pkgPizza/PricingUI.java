package pkgPizza;


import java.awt.Color;
import java.sql.*;
import java.util.*;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class PricingUI extends javax.swing.JFrame
{

private static Connection conn = DBSingle.getConn().getConnection();
private Component frame;

    /** Creates new form PricingUI */
    public PricingUI() {
        initComponents();
        getContentPane().setBackground(Color.getHSBColor(249,238,205));
        fillcboCrust();
        fillcboSize();
        fillcboSpecialty();

    }

    public void fillcboSize()
    {

        String strIngredientsList;

        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT * FROM Size");
           ResultSet rs1 = stmt.getResultSet();

           cboPizzaSize.removeAllItems();

           while(rs1.next())
           {
               Size mySize = new Size();
               mySize.setSizeID(Integer.parseInt(rs1.getString("Size_ID")));
               mySize.setSize(rs1.getString("Size"));
               cboPizzaSize.addItem(mySize);
               mySize = null;

           }
           
        }
        catch(SQLException ex)
        {

        }
    }

     public void fillcboCrust()
    {

        String strIngredientsList;

        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT * FROM crust_type");
           ResultSet rs3 = stmt.getResultSet();

           cboCrustType.removeAllItems();

           while(rs3.next())
           {
                Crust myCrust = new Crust();
               myCrust.setCrustID(Integer.parseInt(rs3.getString("CrustType_ID")));

                myCrust.setCrust(rs3.getString("CrustType"));

                cboCrustType.addItem(myCrust);
                myCrust = null;

           }
           
        }
        catch(SQLException ex)
        {
            System.out.println("catch is running for crust");
        }
    }

          public void fillcboSpecialty()
    {

        String strIngredientsList;

        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT distinct (PizzaType), PizzaType_ID FROM Pizza_Prices");
           ResultSet rs2 = stmt.getResultSet();

           cboSpecialty.removeAllItems();

           while(rs2.next())
           {


               Specialty mySpecialty = new Specialty();
               mySpecialty.setSpecialtyID(Integer.parseInt(rs2.getString("PizzaType_ID")));

                mySpecialty.setSpecialty(rs2.getString("PizzaType"));

                cboSpecialty.addItem(mySpecialty);
                mySpecialty = null;

           }
           
        }
        catch(SQLException ex)
        {
            System.out.println("catch is running for specialty");
        }
    }

    public void close()
    {
        this.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txtPerItem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnPerItem = new javax.swing.JButton();
        txtSizeCrustPrice = new javax.swing.JTextField();
        cboCrustType = new javax.swing.JComboBox();
        cboPizzaSize = new javax.swing.JComboBox();
        btnSizeCrust = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboSpecialty = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtNumOfItems = new javax.swing.JTextField();
        btnSpecialty = new javax.swing.JButton();
        pnlGreenStripe = new javax.swing.JPanel();
        pnlBlueStripe = new javax.swing.JPanel();
        lblPizzajpg = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuExit = new javax.swing.JMenu();
        mnuMainMenu = new javax.swing.JMenuItem();
        mnuExitForm = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(249, 238, 205));

        jPanel3.setBackground(new java.awt.Color(249, 238, 205));

        txtPerItem.setFont(new java.awt.Font("Garamond", 0, 18));
        txtPerItem.setText("New Item Price");

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 18));
        jLabel1.setText("Change \"Per Item\" Price for Pizza Toppings");

        btnPerItem.setFont(new java.awt.Font("Garamond", 0, 18));
        btnPerItem.setText("Implement New \"Per Item\" Price");
        btnPerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerItemActionPerformed(evt);
            }
        });

        txtSizeCrustPrice.setFont(new java.awt.Font("Garamond", 0, 18));
        txtSizeCrustPrice.setText("New Price");

        cboCrustType.setFont(new java.awt.Font("Garamond", 0, 18));
        cboCrustType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboCrustType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCrustTypeActionPerformed(evt);
            }
        });

        cboPizzaSize.setFont(new java.awt.Font("Garamond", 0, 18));
        cboPizzaSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboPizzaSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPizzaSizeActionPerformed(evt);
            }
        });

        btnSizeCrust.setFont(new java.awt.Font("Garamond", 0, 18));
        btnSizeCrust.setText("Implement Size/Crust Price Change");
        btnSizeCrust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSizeCrustActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 141));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1128, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 141));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1128, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 18));
        jLabel2.setText("Change Base Price of Size/Crust Type ");

        cboSpecialty.setFont(new java.awt.Font("Garamond", 0, 18));
        cboSpecialty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSpecialty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSpecialtyActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 18));
        jLabel3.setText("Change Price of Specialty Pizzas");

        txtNumOfItems.setFont(new java.awt.Font("Garamond", 0, 18));
        txtNumOfItems.setText("No. of Items");

        btnSpecialty.setFont(new java.awt.Font("Garamond", 0, 18));
        btnSpecialty.setText("Implement Specialty Price Change");
        btnSpecialty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpecialtyActionPerformed(evt);
            }
        });

        pnlGreenStripe.setBackground(new java.awt.Color(0, 153, 0));
        pnlGreenStripe.setAlignmentY(0.4F);
        pnlGreenStripe.setPreferredSize(new java.awt.Dimension(420, 30));
        pnlGreenStripe.setRequestFocusEnabled(false);

        javax.swing.GroupLayout pnlGreenStripeLayout = new javax.swing.GroupLayout(pnlGreenStripe);
        pnlGreenStripe.setLayout(pnlGreenStripeLayout);
        pnlGreenStripeLayout.setHorizontalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1283, Short.MAX_VALUE)
        );
        pnlGreenStripeLayout.setVerticalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        pnlBlueStripe.setBackground(new java.awt.Color(0, 0, 141));
        pnlBlueStripe.setAlignmentX(0.0F);
        pnlBlueStripe.setAlignmentY(0.0F);
        pnlBlueStripe.setFocusable(false);
        pnlBlueStripe.setPreferredSize(new java.awt.Dimension(590, 30));

        javax.swing.GroupLayout pnlBlueStripeLayout = new javax.swing.GroupLayout(pnlBlueStripe);
        pnlBlueStripe.setLayout(pnlBlueStripeLayout);
        pnlBlueStripeLayout.setHorizontalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1283, Short.MAX_VALUE)
        );
        pnlBlueStripeLayout.setVerticalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        lblPizzajpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkgPizza/tomato_pizza.jpg"))); // NOI18N
        lblPizzajpg.setText("lblPizzaPic");
        lblPizzajpg.setAlignmentY(0.0F);
        lblPizzajpg.setFocusable(false);
        lblPizzajpg.setIconTextGap(0);
        lblPizzajpg.setInheritsPopupMenu(false);

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 24));
        jLabel4.setText("CJS Pizza");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1283, Short.MAX_VALUE)
                    .addComponent(pnlBlueStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1283, Short.MAX_VALUE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(477, 477, 477)
                .addComponent(cboSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(txtNumOfItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(542, Short.MAX_VALUE)
                .addComponent(btnSpecialty)
                .addGap(539, 539, 539))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(467, Short.MAX_VALUE)
                .addComponent(cboPizzaSize, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cboCrustType, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(txtSizeCrustPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(486, 486, 486))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(118, 118, 118))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(621, Short.MAX_VALUE)
                .addComponent(txtPerItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(617, 617, 617))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(629, 629, 629)
                .addComponent(jLabel4)
                .addContainerGap(628, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(555, 555, 555)
                .addComponent(jLabel3)
                .addContainerGap(554, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(508, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(506, 506, 506))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(531, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(529, 529, 529))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(537, Short.MAX_VALUE)
                .addComponent(btnSizeCrust)
                .addGap(532, 532, 532))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(547, Short.MAX_VALUE)
                .addComponent(btnPerItem)
                .addGap(540, 540, 540))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pnlBlueStripe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(txtPerItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnPerItem)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboPizzaSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCrustType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSizeCrustPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(btnSizeCrust)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboSpecialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumOfItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnSpecialty)
                .addGap(57, 57, 57))
        );

        mnuExit.setText("File");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });

        mnuMainMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        mnuMainMenu.setText("Back To Main Menu");
        mnuMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMainMenuActionPerformed(evt);
            }
        });
        mnuExit.add(mnuMainMenu);

        mnuExitForm.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnuExitForm.setText("Exit");
        mnuExitForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitFormActionPerformed(evt);
            }
        });
        mnuExit.add(mnuExitForm);

        jMenuBar1.add(mnuExit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPerItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPerItemActionPerformed
    {//GEN-HEADEREND:event_btnPerItemActionPerformed

       Double dblToppingPrices;

       
        dblToppingPrices = Double.parseDouble(txtPerItem.getText());
        System.out.println(dblToppingPrices);
        


        try
        {
            
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            
            String update = "UPDATE Pizza_Prices SET ToppingPrices = " + dblToppingPrices;
            
            JOptionPane.showMessageDialog(frame,
            "Topping Price Changed.");

            ResultSet rs = stmt.executeQuery(update);
            
            

            
            while (rs.next())
            { 
                dblToppingPrices = rs.getDouble("ToppingPrices");

            }
            
        }
        catch(Exception e)

                {
                    System.out.println(e.getMessage());
                }
        
        
        txtPerItem.setText("New Item Price");
        


    }//GEN-LAST:event_btnPerItemActionPerformed

    private void btnSizeCrustActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSizeCrustActionPerformed
    {//GEN-HEADEREND:event_btnSizeCrustActionPerformed

       Connection con;
       Statement stmt;

       Size mySize = (Size)cboPizzaSize.getSelectedItem();
       int pizzaSize = mySize.getSizeID();
       
       Crust myCrust = (Crust)cboCrustType.getSelectedItem();
       int pizzaCrust = myCrust.getCrustID();
       

       String strPrice;
       strPrice = txtSizeCrustPrice.getText();


        int crustID;

        
        try
        {
            
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs1 = stmt.executeQuery("select CrustType_ID from crust_type where CrustType = " + crust);
            rs1 = stmt.getResultSet();

            while(rs1.next())
            {
                crustID = rs1.getInt("CrustType_ID");
            }

             

            String update2 = "UPDATE baseprices set Price = " + strPrice + " where Size_ID = " + pizzaSize + " AND CrustType_ID = " + pizzaCrust + ";";
            
            JOptionPane.showMessageDialog(frame,
            "Base Pizza Price Changed.");

          
            ResultSet rs = stmt.executeQuery(update2);
            
            
            
            while (rs.next())
            { 
                strPrice = rs.getString("Price");

            }
            
        }
        catch(Exception e)

                {
                    System.out.println(e.getMessage());
                }
        
        txtSizeCrustPrice.setText("New Price");
        cboPizzaSize.setSelectedIndex(0);
        cboCrustType.setSelectedIndex(0);


    }//GEN-LAST:event_btnSizeCrustActionPerformed

    private void btnSpecialtyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSpecialtyActionPerformed
    {//GEN-HEADEREND:event_btnSpecialtyActionPerformed

       int intNumToppings;

        intNumToppings = Integer.parseInt(txtNumOfItems.getText());
        System.out.println(intNumToppings);


        try
        {
            
            
          
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      
            String update = "UPDATE Pizza_Prices SET NumToppings = " + txtNumOfItems.getText() 
                    + " WHERE PizzaType = '" + cboSpecialty.getSelectedItem() + "';";
            
            System.out.println(update);

            JOptionPane.showMessageDialog(frame,
                "Price Changed.");
            
            ResultSet rs = stmt.executeQuery(update);
            
          
          
            while (rs.next())
            { 
                intNumToppings = rs.getInt("NumToppings");

            }
            
        }
        catch(Exception e)

                {
                    System.out.println(e.getMessage());
                }
        
        
        txtNumOfItems.setText("No. of Items");
        cboSpecialty.setSelectedIndex(0);
        
        
    }//GEN-LAST:event_btnSpecialtyActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuExitActionPerformed
    {//GEN-HEADEREND:event_mnuExitActionPerformed
        // Does not do anything.
}//GEN-LAST:event_mnuExitActionPerformed

    private void mnuMainMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuMainMenuActionPerformed
    {//GEN-HEADEREND:event_mnuMainMenuActionPerformed
        this.dispose();
        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.setVisible(true);
        mainMenu.enableButtons();
}//GEN-LAST:event_mnuMainMenuActionPerformed

    private void mnuExitFormActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuExitFormActionPerformed
    {//GEN-HEADEREND:event_mnuExitFormActionPerformed
        // Close the program
        
        int result = JOptionPane.showConfirmDialog(
    frame,
    "Are you sure you want to exit?",
    "CJS Software Solutions",
    JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
    }           

       
    }//GEN-LAST:event_mnuExitFormActionPerformed

    private void cboPizzaSizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cboPizzaSizeActionPerformed
    {//GEN-HEADEREND:event_cboPizzaSizeActionPerformed
        try
        {
               Size mySize = new Size();
               mySize = (Size) cboPizzaSize.getSelectedItem();
        }
        catch(Exception ex)
        {

        }
        

    }//GEN-LAST:event_cboPizzaSizeActionPerformed

    private void cboCrustTypeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cboCrustTypeActionPerformed
    {//GEN-HEADEREND:event_cboCrustTypeActionPerformed
        try
        {
        Crust myCrust = new Crust();
        myCrust = (Crust) cboCrustType.getSelectedItem();
        }
        catch(Exception ex)
        {

        }
    }//GEN-LAST:event_cboCrustTypeActionPerformed

    private void cboSpecialtyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cboSpecialtyActionPerformed
    {//GEN-HEADEREND:event_cboSpecialtyActionPerformed
        try
        {
        Specialty mySpecialty = new Specialty();
        mySpecialty = (Specialty) cboSpecialty.getSelectedItem();
        }
        catch(Exception ex)
        {

        }
    }//GEN-LAST:event_cboSpecialtyActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PricingUI().setVisible(true);
            }
        });
    }

    //personal variables
    String size;
    String crust;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPerItem;
    private javax.swing.JButton btnSizeCrust;
    private javax.swing.JButton btnSpecialty;
    private javax.swing.JComboBox cboCrustType;
    private javax.swing.JComboBox cboPizzaSize;
    private javax.swing.JComboBox cboSpecialty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblPizzajpg;
    private javax.swing.JMenu mnuExit;
    private javax.swing.JMenuItem mnuExitForm;
    private javax.swing.JMenuItem mnuMainMenu;
    private javax.swing.JPanel pnlBlueStripe;
    private javax.swing.JPanel pnlGreenStripe;
    private javax.swing.JTextField txtNumOfItems;
    private javax.swing.JTextField txtPerItem;
    private javax.swing.JTextField txtSizeCrustPrice;
    // End of variables declaration//GEN-END:variables

}

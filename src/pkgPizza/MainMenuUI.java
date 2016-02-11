/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainMenuUI.java
 *
 * Created on Oct 1, 2011, 3:34:14 PM
 */

package pkgPizza;

import java.sql.*;
import java.util.*;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Steve
 */
public class MainMenuUI extends javax.swing.JFrame {
    
    private Component frame;

    private static Connection conn = DBSingle.getConn().getConnection();

    /** Creates new form MainMenuUI */
    public MainMenuUI() {
        initComponents();
        warningLabel();

    }

    public void disableButtons()
    {
        btnPriceChange.setEnabled(false);
        btnInvReOrder.setEnabled(false);
        btnMenuItems.setEnabled(false);
        btnInvReceive.setEnabled(false);
    }

    public void enableButtons()
    {
        btnPriceChange.setEnabled(true);
        btnInvReOrder.setEnabled(true);
        btnMenuItems.setEnabled(true);
        btnInvReceive.setEnabled(true);
    }



public void warningLabel()
{
    //declare variable
    String Ingredient;
    int numRecords = 0;

    //establish databsse connection
    //query database for ingredients that have OnHand < ReOrder point
    //append ingredients found to warming label
    //make label visible IF the result set is not null

    txtWarning.setText("Warning: Ingredients Below Re-Order:\n");

    try
    {
    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    stmt.executeQuery("SELECT * FROM Ingredients WHERE OnHand + OnOrder < ReOrder");
    ResultSet rs = stmt.getResultSet();

    while(rs.next())
    {
        txtWarning.append(rs.getString("Description") + "\n");
        numRecords++;
    }
    }
    catch (SQLException ex)
    {
        System.out.println(ex.getMessage());
    }

    if (numRecords  >0)
    {
        pnlTextArea.setVisible(true);
    }
    else
    {
        pnlTextArea.setVisible(false);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        lblPizzajpg = new javax.swing.JLabel();
        pnlBlueStripe = new javax.swing.JPanel();
        pnlGreenStripe = new javax.swing.JPanel();
        btnPizzaOrderTaking = new javax.swing.JButton();
        btnPriceChange = new javax.swing.JButton();
        btnMenuItems = new javax.swing.JButton();
        btnInvReOrder = new javax.swing.JButton();
        btnInvReceive = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlTextArea = new javax.swing.JPanel();
        txtWarning = new javax.swing.JTextArea();
        mnuMainMenu = new javax.swing.JMenuBar();
        mnuMainMenuFile = new javax.swing.JMenu();
        mnuNewLogIn = new javax.swing.JMenuItem();
        mnuMainMenuExit = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setAlwaysOnTop(true);

        jPanel1.setBackground(new java.awt.Color(249, 238, 205));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));

        lblPizzajpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkgPizza/tomato_pizza.jpg"))); // NOI18N
        lblPizzajpg.setText("lblPizzaPic");
        lblPizzajpg.setAlignmentY(0.0F);
        lblPizzajpg.setFocusable(false);
        lblPizzajpg.setIconTextGap(0);
        lblPizzajpg.setInheritsPopupMenu(false);

        pnlBlueStripe.setBackground(new java.awt.Color(0, 0, 141));
        pnlBlueStripe.setAlignmentX(0.0F);
        pnlBlueStripe.setAlignmentY(0.0F);
        pnlBlueStripe.setFocusable(false);
        pnlBlueStripe.setPreferredSize(new java.awt.Dimension(590, 30));

        javax.swing.GroupLayout pnlBlueStripeLayout = new javax.swing.GroupLayout(pnlBlueStripe);
        pnlBlueStripe.setLayout(pnlBlueStripeLayout);
        pnlBlueStripeLayout.setHorizontalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );
        pnlBlueStripeLayout.setVerticalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        pnlGreenStripe.setBackground(new java.awt.Color(0, 153, 0));
        pnlGreenStripe.setAlignmentY(0.4F);
        pnlGreenStripe.setPreferredSize(new java.awt.Dimension(420, 30));
        pnlGreenStripe.setRequestFocusEnabled(false);

        javax.swing.GroupLayout pnlGreenStripeLayout = new javax.swing.GroupLayout(pnlGreenStripe);
        pnlGreenStripe.setLayout(pnlGreenStripeLayout);
        pnlGreenStripeLayout.setHorizontalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );
        pnlGreenStripeLayout.setVerticalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        btnPizzaOrderTaking.setFont(new java.awt.Font("Garamond", 1, 12));
        btnPizzaOrderTaking.setText("Pizza Order Taking");
        btnPizzaOrderTaking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPizzaOrderTakingActionPerformed(evt);
            }
        });

        btnPriceChange.setFont(new java.awt.Font("Garamond", 1, 12));
        btnPriceChange.setText("Pricing Changes");
        btnPriceChange.setEnabled(false);
        btnPriceChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPriceChangeActionPerformed(evt);
            }
        });

        btnMenuItems.setFont(new java.awt.Font("Garamond", 1, 12));
        btnMenuItems.setText("Add/Remove Menu Items");
        btnMenuItems.setEnabled(false);
        btnMenuItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuItemsActionPerformed(evt);
            }
        });

        btnInvReOrder.setFont(new java.awt.Font("Garamond", 1, 12));
        btnInvReOrder.setText("Inventory Re-Ordering");
        btnInvReOrder.setEnabled(false);
        btnInvReOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvReOrderActionPerformed(evt);
            }
        });

        btnInvReceive.setFont(new java.awt.Font("Garamond", 1, 12));
        btnInvReceive.setText("Inventory Receiving");
        btnInvReceive.setEnabled(false);
        btnInvReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvReceiveActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CJS Pizza ");

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 18));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Main Menu");

        pnlTextArea.setBackground(new java.awt.Color(249, 238, 205));

        txtWarning.setBackground(new java.awt.Color(249, 238, 205));
        txtWarning.setColumns(20);
        txtWarning.setEditable(false);
        txtWarning.setFont(new java.awt.Font("Garamond", 1, 18));
        txtWarning.setForeground(new java.awt.Color(204, 0, 0));
        txtWarning.setRows(5);
        txtWarning.setWrapStyleWord(true);
        txtWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        txtWarning.setDisabledTextColor(new java.awt.Color(204, 0, 0));
        txtWarning.setFocusable(false);
        txtWarning.setHighlighter(null);
        txtWarning.setRequestFocusEnabled(false);
        txtWarning.setSelectedTextColor(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout pnlTextAreaLayout = new javax.swing.GroupLayout(pnlTextArea);
        pnlTextArea.setLayout(pnlTextAreaLayout);
        pnlTextAreaLayout.setHorizontalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTextAreaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtWarning, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)))
        );
        pnlTextAreaLayout.setVerticalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTextAreaLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(txtWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addComponent(pnlBlueStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnInvReOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMenuItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPriceChange, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInvReceive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPizzaOrderTaking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(259, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlBlueStripe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlGreenStripe, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(19, 19, 19)
                .addComponent(btnPizzaOrderTaking)
                .addGap(26, 26, 26)
                .addComponent(btnPriceChange)
                .addGap(26, 26, 26)
                .addComponent(btnMenuItems)
                .addGap(26, 26, 26)
                .addComponent(btnInvReOrder)
                .addGap(26, 26, 26)
                .addComponent(btnInvReceive)
                .addGap(18, 18, 18)
                .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mnuMainMenuFile.setText("File");
        mnuMainMenuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMainMenuFileActionPerformed(evt);
            }
        });
        mnuMainMenuFile.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                mnuMainMenuFileMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });

        mnuNewLogIn.setText("Log In as New User");
        mnuNewLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewLogInActionPerformed(evt);
            }
        });
        mnuMainMenuFile.add(mnuNewLogIn);

        mnuMainMenuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnuMainMenuExit.setText("Exit");
        mnuMainMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMainMenuExitActionPerformed(evt);
            }
        });
        mnuMainMenuFile.add(mnuMainMenuExit);

        mnuMainMenu.add(mnuMainMenuFile);

        setJMenuBar(mnuMainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPizzaOrderTakingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPizzaOrderTakingActionPerformed
        this.setVisible(false);
        OrderInfoUI myOrderInfo = new OrderInfoUI();
        myOrderInfo.setVisible(true);
    }//GEN-LAST:event_btnPizzaOrderTakingActionPerformed

    private void mnuMainMenuFileMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_mnuMainMenuFileMenuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuMainMenuFileMenuKeyPressed

    private void btnPriceChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPriceChangeActionPerformed
        this.setVisible(false);
        PricingUI myPricingUI = new PricingUI();
        myPricingUI.setVisible(true);
    }//GEN-LAST:event_btnPriceChangeActionPerformed

    private void btnMenuItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuItemsActionPerformed
        this.setVisible(false);
        AddDeleteUI1 myAddDeleteUI1 = new AddDeleteUI1();
        myAddDeleteUI1.setVisible(true);
    }//GEN-LAST:event_btnMenuItemsActionPerformed

    private void btnInvReOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvReOrderActionPerformed
        this.setVisible(false);
        InventoryOrderUI myInventoryOrder = new InventoryOrderUI();
        myInventoryOrder.setVisible(true);
    }//GEN-LAST:event_btnInvReOrderActionPerformed

    private void btnInvReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvReceiveActionPerformed
        this.setVisible(false);
        InventoryReceivingUI myInventoryReceiving = new InventoryReceivingUI();
        myInventoryReceiving.setVisible(true);
    }//GEN-LAST:event_btnInvReceiveActionPerformed

    private void mnuMainMenuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMainMenuFileActionPerformed
       
    }//GEN-LAST:event_mnuMainMenuFileActionPerformed

    private void mnuMainMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMainMenuExitActionPerformed
           int result = JOptionPane.showConfirmDialog(
    frame,
    "Are you sure you want to exit?",
    "CJS Software Solutions",
    JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
    }           
    }//GEN-LAST:event_mnuMainMenuExitActionPerformed

    private void mnuNewLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewLogInActionPerformed
        this.dispose();
        LogInUI myLogInUI = new LogInUI();
        myLogInUI.setVisible(true);
    }//GEN-LAST:event_mnuNewLogInActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenuUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvReOrder;
    private javax.swing.JButton btnInvReceive;
    private javax.swing.JButton btnMenuItems;
    private javax.swing.JButton btnPizzaOrderTaking;
    private javax.swing.JButton btnPriceChange;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPizzajpg;
    private javax.swing.JMenuBar mnuMainMenu;
    private javax.swing.JMenuItem mnuMainMenuExit;
    private javax.swing.JMenu mnuMainMenuFile;
    private javax.swing.JMenuItem mnuNewLogIn;
    private javax.swing.JPanel pnlBlueStripe;
    private javax.swing.JPanel pnlGreenStripe;
    private javax.swing.JPanel pnlTextArea;
    private javax.swing.JTextArea txtWarning;
    // End of variables declaration//GEN-END:variables

}

/*
Author:     Steve Blue
Date:       Oct 25, 2011
Program:
Purpose:
Mods:       
*/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InventoryReceivingUI.java
 *
 * Created on Oct 25, 2011, 8:09:23 AM
 */

package pkgPizza;

import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Steve
 */
public class InventoryReceivingUI extends javax.swing.JFrame
{
    private Component frame;

    private static Connection conn = DBSingle.getConn().getConnection();

    /** Creates new form InventoryReceivingUI */
    public InventoryReceivingUI()
    {
        initComponents();
        getContentPane().setBackground(Color.getHSBColor(249,238,205));
        fillOrderNum();
    }

    public final void fillOrderNum()
    {

        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT * FROM inventory_orders WHERE Open = True");
           ResultSet rs1 = stmt.getResultSet();

           cboOrderNum.removeAllItems();

           while(rs1.next())
            {
               InventoryOrders myInventoryOrders = new InventoryOrders();
               myInventoryOrders.setInvOrderNum(rs1.getInt("InventoryOrder_ID"));
               String strId = String.valueOf(myInventoryOrders.getInvOrderNum());
               cboOrderNum.addItem(strId);
               cboOrderNum.setSelectedIndex(-1);
            }          
           
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }


    }

        public void fillItemOrdered(int orderNum)
        {

        if (cboOrderNum.getSelectedIndex() >= 0)
        {
            try
            {
               Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               String mySql = ("SELECT ingredients.Ingredients_ID, ingredients.Description "
                       + "FROM inventory_orderline "
                       + "JOIN ingredients ON ingredients.ingredients_id = inventory_orderline.Ingredients_ID "
                       + "WHERE inventory_orderline.InventoryOrder_ID = " + orderNum );
               
               stmt.executeQuery(mySql);
               ResultSet rs1 = stmt.getResultSet();

               cboItemOrdered.removeAllItems();

               while(rs1.next())
                {
                   InventoryOrders myInventoryOrders = new InventoryOrders();
                   myInventoryOrders.setIngDescription(rs1.getString("Description"));
                   myInventoryOrders.setIngredID(rs1.getInt("Ingredients_ID"));
                   cboItemOrdered.addItem(myInventoryOrders);
                   myInventoryOrders = null;
                }

            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
            cboItemOrdered.setSelectedIndex(-1);
        }
    }

        public void clearForm()
        {
            fillOrderNum();
            System.out.println(cboOrderNum.getSelectedItem());
            if (cboOrderNum.getSelectedItem() != null) {
                
            
            String myOrderNumber = (String) cboOrderNum.getSelectedItem();
            fillItemOrdered(Integer.parseInt(myOrderNumber));
        } 
            txtItemQty.setText(null);
            btnCompleteOrder.setEnabled(false);
            btnReceiveItem.setEnabled(false);
        }

        public void enableReceive()
        {
            try
            {
                if ((cboOrderNum.getSelectedIndex() >= 0) && (Integer.parseInt(txtItemQty.getText()) > 0) && (cboOrderNum.getSelectedIndex() >=0))
                {
                    
                    btnReceiveItem.setEnabled(true);
                }
                else btnReceiveItem.setEnabled(false);
            }
            catch (Exception ex)
            {
                btnReceiveItem.setEnabled(false);
            }
         }

        public void receiveItem()
        {
             
           try
           {
               
               
               int orderQty = Integer.parseInt(txtItemQty.getText());
               
             
               InventoryOrders myInventoryOrders = (InventoryOrders) cboItemOrdered.getSelectedItem();
               String me = String.valueOf(cboItemOrdered.getSelectedIndex());
//               myInventoryOrders = (InventoryOrders) cboItemOrdered.getSelectedItem();
               String myOrderNumber = (String) cboOrderNum.getSelectedItem();

               Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               System.out.println("oq " + orderQty);
               System.out.println("ah " + myInventoryOrders.getIngredID());
               System.out.println("UPDATE ingredients SET OnHand = (OnHand + " + orderQty + ") WHERE Ingredients_ID = "
                    + myInventoryOrders.getIngredID() + ";");
               stmt.executeUpdate("UPDATE ingredients SET OnHand = (OnHand + " + orderQty + ") WHERE Ingredients_ID = "
                    + myInventoryOrders.getIngredID() + ";");
               stmt.executeQuery("SELECT QtyOrdered FROM inventory_orderline WHERE InventoryOrder_ID =" + myOrderNumber + " AND Ingredients_ID = " + myInventoryOrders.getIngredID() + ";");
               ResultSet rs1 = stmt.getResultSet();
               rs1.first();
               qtyOrdered = rs1.getInt("QtyOrdered");
               stmt.executeUpdate("UPDATE ingredients SET OnOrder = (OnOrder - " + qtyOrdered +") "
                    + "WHERE Ingredients_ID = " + myInventoryOrders.getIngredID());
               
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            txtItemQty.setText(null);
            int index = cboItemOrdered.getSelectedIndex();
            cboItemOrdered.removeItemAt(index);
            if (cboItemOrdered.getItemCount() == 0)
                {
                    btnCompleteOrder.setEnabled(true);
                }
            cboItemOrdered.setSelectedIndex(-1);

        }

        public void completeTransaction(int orderNum)
        {
            try
            {
               Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               String mySql = ("UPDATE inventory_orders SET Open = false WHERE InventoryOrder_ID = " + orderNum  ) ;
               stmt.executeUpdate(mySql);
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblHeading = new javax.swing.JLabel();
        lblSubHeading = new javax.swing.JLabel();
        cboOrderNum = new javax.swing.JComboBox();
        cboItemOrdered = new javax.swing.JComboBox();
        txtItemQty = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnReceiveItem = new javax.swing.JButton();
        btnCompleteOrder = new javax.swing.JButton();
        pnlGreenStripe = new javax.swing.JPanel();
        pnlBlueStripe = new javax.swing.JPanel();
        lblPizzajpg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuMainMenu = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 238, 205));

        lblHeading.setFont(new java.awt.Font("Garamond", 1, 24));
        lblHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeading.setText("CJS Pizza ");

        lblSubHeading.setFont(new java.awt.Font("Garamond", 1, 18));
        lblSubHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubHeading.setText("Inventory Receiving");

        cboOrderNum.setFont(new java.awt.Font("Garamond", 0, 18));
        cboOrderNum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboOrderNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOrderNumActionPerformed(evt);
            }
        });

        cboItemOrdered.setFont(new java.awt.Font("Garamond", 0, 18));
        cboItemOrdered.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboItemOrdered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboItemOrderedActionPerformed(evt);
            }
        });

        txtItemQty.setFont(new java.awt.Font("Garamond", 0, 18));
        txtItemQty.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtItemQtyCaretUpdate(evt);
            }
        });
        txtItemQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemQtyActionPerformed(evt);
            }
        });
        txtItemQty.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtItemQtyPropertyChange(evt);
            }
        });
        txtItemQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtItemQtyKeyTyped(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Garamond", 0, 18));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReceiveItem.setFont(new java.awt.Font("Garamond", 0, 18));
        btnReceiveItem.setText("Receive Item");
        btnReceiveItem.setEnabled(false);
        btnReceiveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiveItemActionPerformed(evt);
            }
        });

        btnCompleteOrder.setFont(new java.awt.Font("Garamond", 0, 18));
        btnCompleteOrder.setText("Complete Order");
        btnCompleteOrder.setEnabled(false);
        btnCompleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompleteOrderActionPerformed(evt);
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
            .addGap(0, 1519, Short.MAX_VALUE)
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
            .addGap(0, 1519, Short.MAX_VALUE)
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

        jLabel1.setFont(new java.awt.Font("Garamond", 0, 18));
        jLabel1.setText("Enter Amount Received:");

        jLabel2.setFont(new java.awt.Font("Garamond", 0, 18));
        jLabel2.setText("Order Number");

        jLabel3.setFont(new java.awt.Font("Garamond", 0, 18));
        jLabel3.setText("Item");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1519, Short.MAX_VALUE)
                    .addComponent(pnlBlueStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1519, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(626, 626, 626)
                .addComponent(lblHeading)
                .addContainerGap(625, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(376, 376, 376)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboOrderNum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(btnReceiveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCompleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(631, 631, 631))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboItemOrdered, 0, 187, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(586, 586, 586)
                .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(829, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(414, 414, 414)
                .addComponent(jLabel2)
                .addGap(361, 361, 361)
                .addComponent(jLabel3)
                .addContainerGap(688, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(586, 586, 586)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSubHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(835, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cboItemOrdered, cboOrderNum, txtItemQty});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlBlueStripe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubHeading)
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboOrderNum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboItemOrdered, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReceiveItem)
                    .addComponent(btnClear)
                    .addComponent(btnCompleteOrder))
                .addGap(145, 145, 145))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboItemOrdered, txtItemQty});

        jMenu1.setText("File");

        mnuMainMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        mnuMainMenu.setText("Main Menu");
        mnuMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMainMenuActionPerformed(evt);
            }
        });
        jMenu1.add(mnuMainMenu);

        mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
          int result = JOptionPane.showConfirmDialog(
    frame,
    "Are you sure you want to exit?",
    "CJS Software Solutions",
    JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
    }           
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMainMenuActionPerformed
        this.dispose();
        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.setVisible(true);
        mainMenu.enableButtons();

    }//GEN-LAST:event_mnuMainMenuActionPerformed

    private void cboOrderNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOrderNumActionPerformed
       if (cboOrderNum.getSelectedIndex() >= 0)
       {
           try
           {
                String myOrderNumber = (String) cboOrderNum.getSelectedItem();
                fillItemOrdered(Integer.parseInt(myOrderNumber));
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
           }  
       }
       enableReceive();
    }//GEN-LAST:event_cboOrderNumActionPerformed

    private void txtItemQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemQtyActionPerformed
//        enableReceive();
        btnReceiveItem.setEnabled(true);
        
    }//GEN-LAST:event_txtItemQtyActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        btnCompleteOrder.setEnabled(false);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnReceiveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiveItemActionPerformed
        receiveItem();
        
        btnReceiveItem.setEnabled(false);
        txtItemQty.setText(null);
//        int index = cboItemOrdered.getSelectedIndex();
//        cboItemOrdered.removeItemAt(index);
        if (cboItemOrdered.getItemCount() == 0)
        {
            btnCompleteOrder.setEnabled(true);
        }
        else
        {
            btnCompleteOrder.setEnabled(false);
        }
        cboItemOrdered.setSelectedIndex(-1);
        
        
        JOptionPane.showMessageDialog(frame,
                "Item Received.");


    }//GEN-LAST:event_btnReceiveItemActionPerformed

    private void btnCompleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompleteOrderActionPerformed
        String myOrderNumber = (String) cboOrderNum.getSelectedItem();
        completeTransaction(Integer.parseInt(myOrderNumber));
        clearForm();
        btnCompleteOrder.setEnabled(false);
        
        JOptionPane.showMessageDialog(frame,
                "Order Completed.");
    }//GEN-LAST:event_btnCompleteOrderActionPerformed

    private void cboItemOrderedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboItemOrderedActionPerformed
       if (cboItemOrdered.getSelectedIndex() >=0)
       {
            try
           {
            InventoryOrders myInventoryOrders = new InventoryOrders();
            myInventoryOrders = (InventoryOrders) cboItemOrdered.getSelectedItem();
               System.out.println("adfa " + myInventoryOrders.getIngredID());
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
           }
        }
    }//GEN-LAST:event_cboItemOrderedActionPerformed

    private void txtItemQtyPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtItemQtyPropertyChange

    }//GEN-LAST:event_txtItemQtyPropertyChange

    private void txtItemQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemQtyKeyTyped
        enableReceive();
    }//GEN-LAST:event_txtItemQtyKeyTyped

    private void txtItemQtyCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtItemQtyCaretUpdate
        try
        {
            if ((Integer.parseInt(txtItemQty.getText()) > 0))
            {
                enableReceive();
            }      
        }
       catch (Exception ex)
       {
           System.out.println(ex.getMessage());
       }

    }//GEN-LAST:event_txtItemQtyCaretUpdate

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryReceivingUI().setVisible(true);
            }
        });
    }

    int qtyOrdered;
    int orderNum;
    int itemQty;
    int itemArrayLength = 0;
    public int selectedOrderNum;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCompleteOrder;
    private javax.swing.JButton btnReceiveItem;
    private javax.swing.JComboBox cboItemOrdered;
    private javax.swing.JComboBox cboOrderNum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblPizzajpg;
    private javax.swing.JLabel lblSubHeading;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuMainMenu;
    private javax.swing.JPanel pnlBlueStripe;
    private javax.swing.JPanel pnlGreenStripe;
    private javax.swing.JTextField txtItemQty;
    // End of variables declaration//GEN-END:variables

}

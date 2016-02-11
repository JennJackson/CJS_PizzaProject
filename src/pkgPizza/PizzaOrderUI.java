/*/*
Caleb Finkenbiner
Oct 26, 2011
Program: CJS_PizzaProject/ PizzaOrderUI/
Purpose: Enter Values for pizzas ordered and subtract Items used from database
Mods
*//*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on Oct 2, 2011, 1:13:59 PM
 */

package pkgPizza;


import java.sql. *;
import java.util.*;
import javax.swing.JOptionPane;
import java.awt.Component;



/**
 *
 * @author Steve
 */
public class PizzaOrderUI extends javax.swing.JFrame {

    private static Connection conn = DBSingle.getConn().getConnection();
    private Component frame;
    final String SQ = "'";


    /** Creates new form NewJFrame */
    //default constructor
    public PizzaOrderUI() 
    {
        initComponents();
        
        cmbChooseCrust.setFocusable(false);
        fillPizzaSize();
        fillPizza();
        radTomatoSauce.setSelected(true);
        spnCheese.setValue(1);
        hideSpinners();
        hiddenSpinner1();
        hiddenSpinner2();
        hiddenSpinner3();
        pizzaNum = 1;
    }

    //constructor with passed arguments
    public PizzaOrderUI(int i, String s, int j)
    {
        initComponents();
        
        cmbChooseCrust.setFocusable(false);
        fillPizzaSize();
        fillPizza();
        radTomatoSauce.setSelected(true);
        spnCheese.setValue(1);
        hideSpinners();
        hiddenSpinner1();
        hiddenSpinner2();
        hiddenSpinner3();
        pizzaNum = 1;
        
        orderType = s;
        customerNum = i;
        orderNumber = j;

    }

        public void insertPizza()
        {
            //inserts the pizza into orderline table
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                int length = ingredList.size();
                int toppingQty = 1;

                //loops thru for eash ingredient in the array list, adding or update the database appropriately
                for (int i = 0; i < length; i++)
                {
                    if(i > 0)
                    {
                        //checks to see if current ingredient in array list duplicates the previous
                        //if it does then rather than inserting another row it updates ingredient qty to 2
                        if(ingredList.get(i-1) == ingredList.get(i))
                        {
                            stmt.executeUpdate("Update customer_orderline SET PizzaQty = 2;");
                        }
                        //when the ingredient is not a duplicate of the previous in the arraylist
                        //a new record for the current ingredient is inserted
                        else
                        {
                            stmt.executeUpdate("INSERT INTO customer_orderline (CustomerOrder_ID, Pizza_ID, Ingredients_ID, PizzaType_ID, "
                                + "IngredientQTY, Size_ID, CrustType_ID, PizzaQTY, LinePrice) VALUES (" + orderNumber+"," + pizzaNum + ","
                                + ingredList.get(i) + "," + pizzaTypeID + "," + toppingQty + "," + sizeID + "," + crustID + ","
                                + qtyPizzas+","+ linePrice+"); ");
                        }
                    }
                    //outer else represents the first element of the array and always inserts for element zero
                    //because there can be no previous element which it can duplicate
                    else
                    {
                            stmt.executeUpdate("INSERT INTO customer_orderline (CustomerOrder_ID, Pizza_ID, Ingredients_ID, PizzaType_ID, "
                                + "IngredientQTY, Size_ID, CrustType_ID, PizzaQTY, LinePrice) VALUES (" + orderNumber +"," + pizzaNum + ","
                                + ingredList.get(i) + "," + pizzaTypeID + "," + toppingQty + "," + sizeID + "," + crustID + ","
                                + qtyPizzas+","+ linePrice+"); ");
                    }
                }
                ingredList.clear();
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            //runs the below method to add the pizza info to the text area for viewing
            fillTextArea();
        }

        public void fillTextArea()
        {
            double dblLinePrice = 0;
            int intCrust = 0;
            int intSize = 0;
            int intPizzaType = 0;
            int intPizzaQty = 0;
            int intPizzaID = 0;
            String strCrust = "";
            String strPizzaType = "";
            String strSize = "";
            int pizzaIngredientID = 0;
            int pizzaIngredientQty =0;

            txtPizzaDescription.append("Pizza #" + pizzaNum + "\n");
            
            try
            {
                //query database for pizza info that was inserted by insertPizza method
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT distinct(Pizza_ID), Size_ID, CrustType_ID, PizzaType_ID, PizzaQTY, LinePrice From customer_orderline"
                        + " WHERE CustomerOrder_ID = " + orderNumber + ";");
                ResultSet rs = stmt.getResultSet();

                //while loops thru each pizza of the current order
                //adding a line of text for number of pizzas of this type, size, crust, pizza type and line price
                while(rs.next())
                {
                    dblLinePrice = rs.getDouble("LinePrice");
                    intCrust = rs.getInt("CrustType_ID");
                    intSize = rs.getInt("Size_ID");
                    intPizzaType = rs.getInt("PizzaType_ID");
                    intPizzaQty = rs.getInt("PizzaQTY");
                    intPizzaID = rs.getInt("Pizza_ID");

                }
                    Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt1.executeQuery("SELECT Size FROM size WHERE Size_ID = " + SQ + intSize + SQ + ";");
                    ResultSet rs1 = stmt1.getResultSet();
                    while(rs1.next())
                    {
                        strSize = rs1.getString("Size");
                    }
                    
                    Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt2.executeQuery("SELECT CrustType FROM crust_type WHERE CrustType_ID = " + intCrust + ";");
                    ResultSet rs2 = stmt2.getResultSet();
                    while(rs2.next())
                    {
                        strCrust = rs2.getString("CrustType");
                    }

                    Statement stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt3.executeQuery("SELECT PizzaType FROM pizza_prices WHERE PizzaType_ID = " + intPizzaType + ";");
                    ResultSet rs3 = stmt3.getResultSet();
                    while(rs3.next())
                    {
                        strPizzaType = rs3.getString("PizzaType");
                    }

                    txtPizzaDescription.append("(" + SQ + intPizzaQty + SQ + ") " + SQ + strSize + SQ + " ");
                    txtPizzaDescription.append(strCrust + " ");
                    txtPizzaDescription.append(strPizzaType + " ");
                    txtPizzaDescription.append("$" + dblLinePrice + "\n");

                    Statement stmt4 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt4.executeQuery("SELECT Ingredients_ID, IngredientQTY From customer_orderline"
                            + " WHERE CustomerOrder_ID = " + orderNumber + "AND Pizza_ID = " + intPizzaID + ";");
                    ResultSet rs4 = stmt4.getResultSet();

                    //loops thru each ingredient of the current pizza number
                    //prints the ingredient to text area
                    while(rs4.next())
                    {
                        pizzaIngredientID = rs4.getInt("Ingredients_ID");
                        pizzaIngredientQty = rs4.getInt("IngredientQTY");
                        Statement stmt5 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        stmt5.executeQuery("SELECT Description From ingredients WHERE Ingredients_ID = " + pizzaIngredientID + ";");
                        ResultSet rs5 = stmt5.getResultSet();

                        if(pizzaIngredientID == 2)
                        {
                            txtPizzaDescription.append("double ");
                        }

                        if (pizzaIngredientID == 100001)
                        {
                            txtPizzaDescription.append("extra ");
                        }
                        rs5.first();
                        txtPizzaDescription.append(rs5.getString("Description"));
                    }
                
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }


        public void linePrice()
        {
            //calculates the line price of the current line
            //sql statements retrieve the database information needed to calculate the price of a pizza
            //   based on the pizza's size, type, number of toppings, topping price

            //retrives base price for the cross of size and crust
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT Price FROM baseprices WHERE Size_ID = " + sizeID + " AND CrustType_ID = " + crustID + ";");
                ResultSet rs1 = stmt.getResultSet();
                rs1.first();
                basePrice = rs1.getDouble("Price");

            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            //retrieves topping price and number of free toppings for the associated specialty type
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT distinct(NumToppings) , ToppingPrices FROM pizza_prices WHERE PizzaType_ID = " + pizzaTypeID + ";");
                ResultSet rs1 = stmt.getResultSet();
                rs1.first();
                toppingPrice = rs1.getDouble("ToppingPrices");
                numFreeToppings = rs1.getInt("NumToppings");

            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }           
        }

        public void ingredientCheck()
        {
             int arrayLength = ingredList.size();
             int onHand =0;
             int amtNeeded = 0;

             for(int i = 0; i < arrayLength; i++)
             {
                try
                {
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    stmt.executeQuery("SELECT OnHand FROM ingredients WHERE Ingredients_ID = " + ingredList.get(i));
                    ResultSet rs1 = stmt.getResultSet();

                    while(rs1.next())
                    {
                        onHand = rs1.getInt("OnHand");
                    }
                }
                catch (SQLException ex)
                {
                        System.out.println(ex.getMessage());
                }

                try
                {
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeQuery("SELECT UsageAmount FROM ingredients_usage WHERE Ingredients_ID = " + ingredList.get(i)
                            + " AND Size_ID = " + sizeID);
                    ResultSet rs2 = stmt.getResultSet();

                    while(rs2.next())
                    {
                        amtNeeded = rs2.getInt("UsageAmount");
                    }
                        if ((amtNeeded * qtyPizzas) <= onHand || arrayLength == 0)
                        {
                            checkInventory = true;
                        }
                        else
                        {
                            int result = JOptionPane.showConfirmDialog
                            (frame,
                            "Inventory Records indicate that the ingredients to complete this pizza are not available.\n"
                                    + " do you wish to proceed with the order anyway?\n\n"
                                    + "click YES to proceed, click NO to edit the pizza",
                            "CJS - Inventory Warning",
                            JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION)
                            {
                            checkInventory = true;

                            }
                            else
                            {
                                checkInventory = false;
                                break;
                            }
                         }

                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            if (checkInventory)
            {
                insertPizza();
            }
        }

        public void addPizza()
        {
            if(txtNumOfPizzas.getText() == null ? "" == null : txtNumOfPizzas.getText().equals(""))
            {
                JOptionPane.showMessageDialog(frame, "Need Pizza Amount", "Warning", JOptionPane.OK_OPTION);
            }
            else
            //gathers qty of pizzas of this type (pizza id)
            qtyPizzas = Integer.parseInt(txtNumOfPizzas.getText());

            //gathers the size from combo box
            Size mySize = new Size();
            mySize = (Size) cmbChooseSize.getSelectedItem();
            sizeID = mySize.getSizeID();

            //gathers crust type from combo box
            Crust myCrust = new Crust();
            myCrust = (Crust) cmbChooseCrust.getSelectedItem();
            crustID = myCrust.getCrustID();

            //gathers specialty type from combo box
            Specialty mySpecialty = new Specialty();
            mySpecialty = (Specialty) cmbChoosePizza.getSelectedItem();
            pizzaTypeID = mySpecialty.getSpecialtyID();

            int sauce;
            if (radBBQ.isSelected())
                sauce = 100004;
            else
                sauce = 100003;

            getIngredients();
            linePrice();
            
            linePrice = (basePrice + toppingPrice * (numToppings - numFreeToppings)) * qtyPizzas;

            ingredientCheck();
        }

        public void editPizza(int i)
        {
            int ingredient;
            int numberOfPizzas;
            //the value of the pizza number to be edited is passed in
            //query the databaase for the pizza info to be used, for the chosen pizza
             try
            {Statement
                 stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stmt  .executeQuery("SELECT * FROM customer_orderline WHERE CustomerOrder_ID = "
                        + orderNumber + " AND Pizza_ID = " + i + ";");
                ResultSet rs1 = stmt.getResultSet();

                while(rs1.next())
                {
                    //set the  selected Item of the combo boxes
                    cmbChooseSize.setSelectedItem(sizeID);
                    cmbChooseCrust.setSelectedItem(crustID);
                    cmbChoosePizza.setSelectedItem(pizzaTypeID);

                    //set the text box value for pizza qty
                    numberOfPizzas = rs1.getInt("PizzaQTY");
                    txtNumOfPizzas.setText(numberOfPizzas + "");

                    //set the spinners for the pizza's ingredients
                    ingredient = rs1.getInt("Ingredient_ID");
                    switch(ingredient)
                    {
                         case 100001: spnCheese.setValue(2);
                         break;
                         case 100006: spnPepperoni.setValue(1);
                         break;
                         case 100007: spnSausage.setValue(1);
                         break;
                         case 100008: spnGroundBeef.setValue(1);
                         break;
                         case 100009: spnPhillySteak.setValue(1);
                         break;
                         case 100010: spnHam.setValue(1);
                         break;
                         case 100011: spnBacon.setValue(1);
                         break;
                         case 100012: spnChicken.setValue(1);
                         break;
                         case 100013: spnFetaCheese.setValue(1);
                         break;
                         case 100014: spnBannanaPepper.setValue(1);
                         break;
                         case 100015: spnBlackOlives.setValue(1);
                         break;
                         case 100016: spnGreenPeppers.setValue(1);
                         break;
                         case 100017: spnMushrooms.setValue(1);
                         break;
                         case 100018: spnPineapple.setValue(1);
                         break;
                         case 100019: spnOnions.setValue(1);
                         break;
                         case 100025: spnHidden1.setValue(1);
                         break;
                         case 100026: spnHidden2.setValue(1);
                         break;
                         case 100027: spnHidden3.setValue(1);
                         break;
                         default: spnPepperoni.setValue(0);
                    }
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            //delete the pizza from database
             try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeUpdate("DELETE FROM customer_orderline WHERE CustomerOrder_ID = " + orderNumber + " AND Pizza_ID = " + i + ";");

            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            //refill the text area after the pizza has been deleted
            fillTextArea();
            //pizza info is there and ready to be editted and "added" again
        }

        public void getIngredients()
        {
            int cheeSpin = (Integer) (spnCheese.getValue());
            if (cheeSpin == 2)
                ingredList.add(100001);

            int pepSpin = (Integer)(spnPepperoni.getValue());
            if (pepSpin >= 1)
            {
                ingredList.add(100006);
                if (pepSpin == 2)
                    ingredList.add(100006);
            }

            int sausSpin = (Integer) (spnSausage.getValue());
            if (sausSpin >= 1)
            {
                ingredList.add(100007);
                if (sausSpin == 2)
                    ingredList.add(100007);
            }

            int hamSpin = (Integer) (spnHam.getValue());
            if (hamSpin >= 1)
            {
                ingredList.add(100010);
                if (hamSpin == 2)
                    ingredList.add(100010);
            }

            int baconSpin = (Integer) (spnBacon.getValue());
            if (baconSpin >= 1)
            {
                ingredList.add(100011);
                if (baconSpin == 2)
                    ingredList.add(100011);
            }

            int gbSpin = (Integer) (spnGroundBeef.getValue());
            if (gbSpin >= 1)
            {
                ingredList.add(100008);
                if (gbSpin == 2)
                    ingredList.add(100008);
            }

            int chicSpin = (Integer) (spnChicken.getValue());
            if (chicSpin >= 1)
            {
                ingredList.add(100012);
                if (chicSpin == 2)
                    ingredList.add(100012);
            }

            int philSpin = (Integer) (spnPhillySteak.getValue());
            if (philSpin >= 1)
            {
                ingredList.add(100009);
                if (philSpin == 2)
                    ingredList.add(100009);
            }

            int banSpin = (Integer) (spnBannanaPepper.getValue());
            if (banSpin >= 1)
            {
                ingredList.add(100014);
                if (banSpin == 2)
                    ingredList.add(100014);
            }

            int grpepSpin = (Integer) (spnGreenPeppers.getValue());
            if (grpepSpin >= 1)
            {
                ingredList.add(100016);
                if (grpepSpin == 2)
                    ingredList.add(100016);
            }

            int mushSpin = (Integer) (spnMushrooms.getValue());
            if (mushSpin >= 1)
            {
                ingredList.add(100017);
                if (mushSpin == 2)
                    ingredList.add(100017);
            }

            int onionSpin = (Integer) (spnOnions.getValue());
            if (onionSpin >= 1)
            {
                ingredList.add(100019);
                if (onionSpin == 2)
                    ingredList.add(100019);
            }

            int pinSpin = (Integer) (spnPineapple.getValue());
            if (pinSpin >= 1)
            {
                ingredList.add(100018);
                if (pinSpin == 2)
                    ingredList.add(100018);
            }

            int blackSpin = (Integer) (spnBlackOlives.getValue());
            if (blackSpin >= 1)
            {
                ingredList.add(100015);
                if (blackSpin == 2)
                    ingredList.add(100015);
            }

            int fetaSpin = (Integer) (spnFetaCheese.getValue());
            if (fetaSpin >= 1)
            {
                ingredList.add(100013);
                if (fetaSpin == 2)
                    ingredList.add(100013);
            }

            int hid1Spin = (Integer) (spnHidden1.getValue());
            if (hid1Spin >= 1)
            {
                ingredList.add(100025);
                if (hid1Spin == 2)
                    ingredList.add(100025);
            }

            int hid2Spin = (Integer) (spnHidden2.getValue());
            if (hid2Spin >= 1)
            {
                ingredList.add(100026);
                if (hid2Spin == 2)
                    ingredList.add(100026);
            }

            int hid3Spin = (Integer) (spnHidden3.getValue());
            if (hid3Spin >= 1)
            {
                ingredList.add(100027);
                if (hid3Spin == 2)
                    ingredList.add(100027);
            }

            numToppings = ingredList.size();
            
            

        }

        public void hideSpinners()
        {
            spnHidden1.setVisible(false);
            spnHidden2.setVisible(false);
            spnHidden3.setVisible(false);
            lblHidden1.setVisible(false);
            lblHidden2.setVisible(false);
            lblHidden3.setVisible(false);
        }

        public void hiddenSpinner1()
        {
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT * FROM Ingredients WHERE ingredients_ID = 100025");
                ResultSet rs1 = stmt.getResultSet();
                while (rs1.next())
                {
                    if (rs1.getBoolean("Active"))
                    {
                        lblHidden1.setVisible(true);
                        spnHidden1.setVisible(true);
                        lblHidden1.setText(rs1.getString("Description"));
                    }

                }
            }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        }


        public void hiddenSpinner2()
        {
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT * FROM Ingredients WHERE ingredients_ID = 100026");
                ResultSet rs1 = stmt.getResultSet();
                while (rs1.next())
                {
                    if (rs1.getBoolean("Active"))
                    {
                        lblHidden2.setVisible(true);
                        spnHidden2.setVisible(true);
                        lblHidden2.setText(rs1.getString("Description"));
                    }

                }
            }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        }

        public void hiddenSpinner3()
        {
            try
            {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeQuery("SELECT * FROM Ingredients WHERE ingredients_ID = 100027");
                ResultSet rs1 = stmt.getResultSet();
                while (rs1.next())
                {
                    if (rs1.getBoolean("Active"))
                    {
                        lblHidden3.setVisible(true);
                        spnHidden3.setVisible(true);
                        lblHidden3.setText(rs1.getString("Description"));
                    }

                }
            }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        }
        
        public void clearSpinners()
        {
            // clears screen and sets screen to defaults
            radTomatoSauce.setSelected(true);
            spnCheese.setValue(1);
            spnPepperoni.setValue(0);
            spnSausage.setValue(0);
            spnHam.setValue(0);
            spnBacon.setValue(0);
            spnGroundBeef.setValue(0);
            spnChicken.setValue(0);
            spnPhillySteak.setValue(0);
            spnBannanaPepper.setValue(0);
            spnBlackOlives.setValue(0);
            spnGreenPeppers.setValue(0);
            spnMushrooms.setValue(0);
            spnOnions.setValue(0);
            spnPineapple.setValue(0);
            spnFetaCheese.setValue(0); 
            spnHidden1.setValue(0);
            spnHidden2.setValue(0);
            spnHidden3.setValue(0);
        }

        public void cancelOrder()
        {
            //get confirmation that user wants to cancel order
            int result = JOptionPane.showConfirmDialog
            (frame,
            "Possible Unsaved Information! \n Are you sure you wish to cancel the order?",
            "CJS - Cancel Order Confirmation",
            JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION)
            {
                //do the below only on confirmation
                //delete all pizzas for this order from database: orders and orderline tables
                try
                {
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate("DELETE FROM customer_orderline WHERE CustomerOrder_ID = " + orderNumber + ";");
                    stmt.executeUpdate("DELETE FROM customer_orders WHERE CustomerOrder_ID = " + orderNumber + ";");
                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
                //close/dispose of order form and return to orderinfoUI
                this.dispose();
                OrderInfoUI myOrderInfoUI = new OrderInfoUI();
                myOrderInfoUI.setVisible(true);
            }
            //if the user clicks no on confirmation box nothing happens
        }  

        public void close()
        {// sets action to close screen when changing to main menu
            this.dispose();
        }

    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpSauce = new javax.swing.ButtonGroup();
        pnlBackgroundColor = new javax.swing.JPanel();
        lblPizzajpg = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        pnlBlueStripe = new javax.swing.JPanel();
        cmbChooseSize = new javax.swing.JComboBox();
        cmbChooseCrust = new javax.swing.JComboBox();
        radTomatoSauce = new javax.swing.JRadioButton();
        radBBQ = new javax.swing.JRadioButton();
        spnCheese = new javax.swing.JSpinner();
        spnPepperoni = new javax.swing.JSpinner();
        spnSausage = new javax.swing.JSpinner();
        spnHam = new javax.swing.JSpinner();
        spnBacon = new javax.swing.JSpinner();
        spnGroundBeef = new javax.swing.JSpinner();
        spnChicken = new javax.swing.JSpinner();
        spnPhillySteak = new javax.swing.JSpinner();
        spnBannanaPepper = new javax.swing.JSpinner();
        spnBlackOlives = new javax.swing.JSpinner();
        spnGreenPeppers = new javax.swing.JSpinner();
        spnMushrooms = new javax.swing.JSpinner();
        spnPineapple = new javax.swing.JSpinner();
        spnOnions = new javax.swing.JSpinner();
        spnFetaCheese = new javax.swing.JSpinner();
        spnHidden1 = new javax.swing.JSpinner();
        spnHidden2 = new javax.swing.JSpinner();
        spnHidden3 = new javax.swing.JSpinner();
        lblCheese = new javax.swing.JLabel();
        lblPepperoni = new javax.swing.JLabel();
        lblSausage = new javax.swing.JLabel();
        lblHam = new javax.swing.JLabel();
        lblBacon = new javax.swing.JLabel();
        lblGroundBeef = new javax.swing.JLabel();
        lblChicken = new javax.swing.JLabel();
        lblPhillySteak = new javax.swing.JLabel();
        lblBannanPeppers = new javax.swing.JLabel();
        lblBlackOlives = new javax.swing.JLabel();
        lblGreenPeppers = new javax.swing.JLabel();
        lblMushrooms = new javax.swing.JLabel();
        lblPineapple = new javax.swing.JLabel();
        lblOnions = new javax.swing.JLabel();
        lblFetaCheese = new javax.swing.JLabel();
        lblHidden1 = new javax.swing.JLabel();
        lblHidden2 = new javax.swing.JLabel();
        lblHidden3 = new javax.swing.JLabel();
        cmbChoosePizza = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPizzaDescription = new javax.swing.JTextArea();
        btnEditPizza = new javax.swing.JButton();
        lblSmlBlueStripe = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        lblFinalTotal = new javax.swing.JLabel();
        lblFinalTotalDisplay = new javax.swing.JLabel();
        btnCompleteOrder = new javax.swing.JButton();
        btnAddPizza1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnNewPizza = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtNumOfPizzas = new javax.swing.JTextField();
        lblNumOfPizzas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnTotal = new javax.swing.JButton();
        pnlGreenStripe = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        mnuMainMenu = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBackgroundColor.setBackground(new java.awt.Color(249, 238, 205));
        pnlBackgroundColor.setMaximumSize(new java.awt.Dimension(32767, 729));

        lblPizzajpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkgPizza/tomato_pizza.jpg"))); // NOI18N
        lblPizzajpg.setText("ghgfhgfh");
        lblPizzajpg.setAlignmentY(0.0F);
        lblPizzajpg.setFocusable(false);
        lblPizzajpg.setIconTextGap(0);
        lblPizzajpg.setInheritsPopupMenu(false);

        btnClear.setFont(new java.awt.Font("Garamond", 1, 12));
        btnClear.setText("Clear");
        btnClear.setMaximumSize(new java.awt.Dimension(81, 23));
        btnClear.setMinimumSize(new java.awt.Dimension(81, 23));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        pnlBlueStripe.setBackground(new java.awt.Color(0, 0, 141));
        pnlBlueStripe.setAlignmentX(0.0F);
        pnlBlueStripe.setAlignmentY(0.0F);
        pnlBlueStripe.setFocusable(false);
        pnlBlueStripe.setPreferredSize(new java.awt.Dimension(590, 30));

        javax.swing.GroupLayout pnlBlueStripeLayout = new javax.swing.GroupLayout(pnlBlueStripe);
        pnlBlueStripe.setLayout(pnlBlueStripeLayout);
        pnlBlueStripeLayout.setHorizontalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1261, Short.MAX_VALUE)
        );
        pnlBlueStripeLayout.setVerticalGroup(
            pnlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        cmbChooseSize.setFont(new java.awt.Font("Garamond", 1, 12));
        cmbChooseSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbChooseSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChooseSizeActionPerformed(evt);
            }
        });

        cmbChooseCrust.setFont(new java.awt.Font("Garamond", 1, 12));
        cmbChooseCrust.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbChooseCrust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChooseCrustActionPerformed(evt);
            }
        });

        radTomatoSauce.setBackground(new java.awt.Color(249, 238, 205));
        btngrpSauce.add(radTomatoSauce);
        radTomatoSauce.setText("Tomato Sauce");
        radTomatoSauce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTomatoSauceActionPerformed(evt);
            }
        });

        radBBQ.setBackground(new java.awt.Color(249, 238, 205));
        btngrpSauce.add(radBBQ);
        radBBQ.setText("BBQ Sauce");

        spnCheese.setFont(new java.awt.Font("Garamond", 1, 12));
        spnCheese.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnCheese.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnPepperoni.setFont(new java.awt.Font("Garamond", 1, 12));
        spnPepperoni.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnPepperoni.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnSausage.setFont(new java.awt.Font("Garamond", 1, 12));
        spnSausage.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnSausage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnHam.setFont(new java.awt.Font("Garamond", 1, 12));
        spnHam.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnHam.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnBacon.setFont(new java.awt.Font("Garamond", 1, 12));
        spnBacon.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnBacon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnGroundBeef.setFont(new java.awt.Font("Garamond", 1, 12));
        spnGroundBeef.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnGroundBeef.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnChicken.setFont(new java.awt.Font("Garamond", 1, 12));
        spnChicken.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnChicken.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnPhillySteak.setFont(new java.awt.Font("Garamond", 1, 12));
        spnPhillySteak.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnPhillySteak.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnBannanaPepper.setFont(new java.awt.Font("Garamond", 1, 12));
        spnBannanaPepper.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnBannanaPepper.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnBlackOlives.setFont(new java.awt.Font("Garamond", 1, 12));
        spnBlackOlives.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnBlackOlives.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnGreenPeppers.setFont(new java.awt.Font("Garamond", 1, 12));
        spnGreenPeppers.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnGreenPeppers.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnMushrooms.setFont(new java.awt.Font("Garamond", 1, 12));
        spnMushrooms.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnMushrooms.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnPineapple.setFont(new java.awt.Font("Garamond", 1, 12));
        spnPineapple.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnPineapple.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnOnions.setFont(new java.awt.Font("Garamond", 1, 12));
        spnOnions.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnOnions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnFetaCheese.setFont(new java.awt.Font("Garamond", 1, 12));
        spnFetaCheese.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnFetaCheese.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnHidden1.setFont(new java.awt.Font("Garamond", 1, 12));
        spnHidden1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnHidden1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnHidden2.setFont(new java.awt.Font("Garamond", 1, 12));
        spnHidden2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnHidden2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        spnHidden3.setFont(new java.awt.Font("Garamond", 1, 12));
        spnHidden3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2, 1));
        spnHidden3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblCheese.setFont(new java.awt.Font("Garamond", 1, 12));
        lblCheese.setText("Cheese");

        lblPepperoni.setFont(new java.awt.Font("Garamond", 1, 12));
        lblPepperoni.setText("Pepperoni");

        lblSausage.setFont(new java.awt.Font("Garamond", 1, 12));
        lblSausage.setText("Sausage");

        lblHam.setFont(new java.awt.Font("Garamond", 1, 12));
        lblHam.setText("Ham");

        lblBacon.setFont(new java.awt.Font("Garamond", 1, 12));
        lblBacon.setText("Bacon");

        lblGroundBeef.setFont(new java.awt.Font("Garamond", 1, 12));
        lblGroundBeef.setText("Ground Beef");

        lblChicken.setFont(new java.awt.Font("Garamond", 1, 12));
        lblChicken.setText("Chicken");

        lblPhillySteak.setFont(new java.awt.Font("Garamond", 1, 12));
        lblPhillySteak.setText("Philly Steak");

        lblBannanPeppers.setFont(new java.awt.Font("Garamond", 1, 12));
        lblBannanPeppers.setText("Bannana Peppers");

        lblBlackOlives.setFont(new java.awt.Font("Garamond", 1, 12));
        lblBlackOlives.setText("Black Olives");

        lblGreenPeppers.setFont(new java.awt.Font("Garamond", 1, 12));
        lblGreenPeppers.setText("Green Peppers");

        lblMushrooms.setFont(new java.awt.Font("Garamond", 1, 12));
        lblMushrooms.setText("Mushrooms");

        lblPineapple.setFont(new java.awt.Font("Garamond", 1, 12));
        lblPineapple.setText("Pineapple");

        lblOnions.setFont(new java.awt.Font("Garamond", 1, 12));
        lblOnions.setText("Onions");

        lblFetaCheese.setFont(new java.awt.Font("Garamond", 1, 12));
        lblFetaCheese.setText("Feta Cheese");

        lblHidden1.setFont(new java.awt.Font("Garamond", 1, 12));
        lblHidden1.setText("Hidden 1");

        lblHidden2.setFont(new java.awt.Font("Garamond", 1, 12));
        lblHidden2.setText("Hidden 2");

        lblHidden3.setFont(new java.awt.Font("Garamond", 1, 12));
        lblHidden3.setText("Hidden 3");

        cmbChoosePizza.setFont(new java.awt.Font("Garamond", 1, 12));
        cmbChoosePizza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbChoosePizza.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbChoosePizzaItemStateChanged(evt);
            }
        });
        cmbChoosePizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChoosePizzaActionPerformed(evt);
            }
        });
        cmbChoosePizza.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbChoosePizzaInputMethodTextChanged(evt);
            }
        });

        txtPizzaDescription.setColumns(20);
        txtPizzaDescription.setRows(5);
        jScrollPane2.setViewportView(txtPizzaDescription);

        btnEditPizza.setFont(new java.awt.Font("Garamond", 1, 12));
        btnEditPizza.setText("Edit Pizza");
        btnEditPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPizzaActionPerformed(evt);
            }
        });

        lblSmlBlueStripe.setBackground(new java.awt.Color(0, 0, 141));
        lblSmlBlueStripe.setAlignmentX(0.0F);
        lblSmlBlueStripe.setAlignmentY(0.0F);
        lblSmlBlueStripe.setFocusable(false);
        lblSmlBlueStripe.setPreferredSize(new java.awt.Dimension(590, 30));

        javax.swing.GroupLayout lblSmlBlueStripeLayout = new javax.swing.GroupLayout(lblSmlBlueStripe);
        lblSmlBlueStripe.setLayout(lblSmlBlueStripeLayout);
        lblSmlBlueStripeLayout.setHorizontalGroup(
            lblSmlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        lblSmlBlueStripeLayout.setVerticalGroup(
            lblSmlBlueStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        txtComments.setColumns(20);
        txtComments.setRows(5);
        jScrollPane3.setViewportView(txtComments);

        lblDiscount.setFont(new java.awt.Font("Garamond", 1, 14));
        lblDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiscount.setText("Discount");
        lblDiscount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtDiscount.setText("$$$$$$");
        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });

        lblFinalTotal.setFont(new java.awt.Font("Garamond", 1, 12));
        lblFinalTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFinalTotal.setText("Order Total:");
        lblFinalTotal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblFinalTotalDisplay.setFont(new java.awt.Font("Garamond", 1, 12));
        lblFinalTotalDisplay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFinalTotalDisplay.setText("$");
        lblFinalTotalDisplay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnCompleteOrder.setFont(new java.awt.Font("Garamond", 1, 12));
        btnCompleteOrder.setText("Complete Order");
        btnCompleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompleteOrderActionPerformed(evt);
            }
        });

        btnAddPizza1.setFont(new java.awt.Font("Garamond", 1, 12));
        btnAddPizza1.setText("Add Current Pizza to Order");
        btnAddPizza1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPizza1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 48));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CJS Pizza - Order Screen");

        btnNewPizza.setFont(new java.awt.Font("Garamond", 1, 12));
        btnNewPizza.setText("Add New Pizza");
        btnNewPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPizzaActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Garamond", 1, 12));
        jButton2.setText("Cancel Order");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Fill Order");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblNumOfPizzas.setFont(new java.awt.Font("Garamond", 1, 12));
        lblNumOfPizzas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumOfPizzas.setText("No. of Pizzas of this Type");
        lblNumOfPizzas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Order Comments");

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 12));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Order Description");

        btnTotal.setFont(new java.awt.Font("Garamond", 1, 12));
        btnTotal.setText("Get Total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        pnlGreenStripe.setBackground(new java.awt.Color(0, 153, 0));
        pnlGreenStripe.setAlignmentY(0.4F);
        pnlGreenStripe.setPreferredSize(new java.awt.Dimension(50, 30));
        pnlGreenStripe.setRequestFocusEnabled(false);

        javax.swing.GroupLayout pnlGreenStripeLayout = new javax.swing.GroupLayout(pnlGreenStripe);
        pnlGreenStripe.setLayout(pnlGreenStripeLayout);
        pnlGreenStripeLayout.setHorizontalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1261, Short.MAX_VALUE)
        );
        pnlGreenStripeLayout.setVerticalGroup(
            pnlGreenStripeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBackgroundColorLayout = new javax.swing.GroupLayout(pnlBackgroundColor);
        pnlBackgroundColor.setLayout(pnlBackgroundColorLayout);
        pnlBackgroundColorLayout.setHorizontalGroup(
            pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spnPepperoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnCheese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPepperoni)
                                            .addComponent(lblCheese)))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnSausage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblSausage))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnHam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHam))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnOnions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblOnions))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnHidden1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHidden1)))
                                .addGap(57, 57, 57)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnChicken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblChicken))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnBacon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBacon))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnGroundBeef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblGroundBeef))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnPhillySteak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPhillySteak))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnFetaCheese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFetaCheese))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnHidden2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHidden2))
                                    .addComponent(txtNumOfPizzas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnHidden3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHidden3))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnBlackOlives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBlackOlives))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnPineapple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPineapple))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnGreenPeppers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblGreenPeppers))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnMushrooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblMushrooms))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(spnBannanaPepper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBannanPeppers))
                                    .addComponent(radBBQ)
                                    .addComponent(radTomatoSauce)))
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(btnAddPizza1))))
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbChooseCrust, javax.swing.GroupLayout.Alignment.LEADING, 0, 144, Short.MAX_VALUE)
                            .addComponent(cmbChoosePizza, javax.swing.GroupLayout.Alignment.LEADING, 0, 144, Short.MAX_VALUE)
                            .addComponent(cmbChooseSize, javax.swing.GroupLayout.Alignment.LEADING, 0, 144, Short.MAX_VALUE)
                            .addComponent(lblNumOfPizzas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(255, 255, 255)
                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(lblSmlBlueStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBackgroundColorLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addGap(150, 150, 150)
                                        .addComponent(btnCompleteOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(btnEditPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(150, 150, 150)
                                        .addComponent(btnNewPizza, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
                                .addGap(48, 48, 48)))
                        .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                            .addGap(186, 186, 186)
                            .addComponent(btnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(lblFinalTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblFinalTotalDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2825, 2825, 2825))
            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlGreenStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
                    .addComponent(pnlBlueStripe, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE))
                .addContainerGap(2731, Short.MAX_VALUE))
            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2748, Short.MAX_VALUE))
        );

        pnlBackgroundColorLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEditPizza, btnNewPizza, jButton2});

        pnlBackgroundColorLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddPizza1, btnClear});

        pnlBackgroundColorLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane3, lblSmlBlueStripe});

        pnlBackgroundColorLayout.setVerticalGroup(
            pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addComponent(pnlBlueStripe, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel1))
                            .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pnlGreenStripe, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPizzajpg, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jButton3)
                                .addGap(31, 31, 31)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(spnCheese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheese))
                                        .addGap(18, 18, 18)
                                        .addComponent(spnPepperoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(spnBacon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBacon)
                                            .addComponent(spnGreenPeppers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblGreenPeppers))
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblPepperoni)
                                            .addComponent(spnGroundBeef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblGroundBeef)
                                            .addComponent(spnMushrooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblMushrooms))))
                                .addGap(15, 15, 15)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spnSausage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnChicken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSausage)
                                    .addComponent(lblChicken)
                                    .addComponent(spnPineapple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPineapple))
                                .addGap(18, 18, 18)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spnHam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnPhillySteak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHam)
                                    .addComponent(lblPhillySteak)
                                    .addComponent(spnBlackOlives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBlackOlives))
                                .addGap(20, 20, 20)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spnOnions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOnions)
                                    .addComponent(spnBannanaPepper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBannanPeppers)
                                    .addComponent(spnFetaCheese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFetaCheese))
                                .addGap(17, 17, 17)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spnHidden1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHidden1)
                                    .addComponent(spnHidden2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHidden2)
                                    .addComponent(spnHidden3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHidden3))
                                .addGap(28, 28, 28)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddPizza1)))
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(143, 143, 143)
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblDiscount)
                                            .addComponent(lblFinalTotal)
                                            .addComponent(lblFinalTotalDisplay)
                                            .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addComponent(btnTotal)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblSmlBlueStripe, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnEditPizza)
                                            .addComponent(btnNewPizza))
                                        .addGap(29, 29, 29)
                                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton2)
                                            .addComponent(btnCompleteOrder)))
                                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(cmbChooseSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addComponent(radTomatoSauce)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radBBQ))
                            .addGroup(pnlBackgroundColorLayout.createSequentialGroup()
                                .addComponent(cmbChooseCrust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(cmbChoosePizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addGroup(pnlBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNumOfPizzas)
                                    .addComponent(txtNumOfPizzas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pnlBackgroundColorLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblBlackOlives, lblFetaCheese, lblGreenPeppers, lblHidden1, lblHidden2, lblHidden3, lblMushrooms, lblOnions, lblPineapple, spnHidden3});

        pnlBackgroundColorLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblBacon, lblBannanPeppers, lblCheese, lblChicken, lblGroundBeef, lblHam, lblPepperoni, lblPhillySteak, lblSausage, spnBannanaPepper});

        lblPizzajpg.getAccessibleContext().setAccessibleName("");

        jMenuBar1.add(jMenu2);

        jMenu1.setText("File");

        mnuMainMenu.setText("Main Menu");
        mnuMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMainMenuActionPerformed(evt);
            }
        });
        jMenu1.add(mnuMainMenu);

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
            .addComponent(pnlBackgroundColor, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackgroundColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbChooseSizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbChooseSizeActionPerformed
    {//GEN-HEADEREND:event_cmbChooseSizeActionPerformed
        // TODO add your handling code here:
       try
       {
        fillCrust();
       }
       catch (Exception ex)
       {
           System.out.println(ex.getMessage());
       }

       if(cmbChooseSize.getSelectedIndex() >= 0)
       {
        Size mySize = new Size();
        mySize = (Size) cmbChooseSize.getSelectedItem();
        size = mySize.getSizeID();
       }


    }//GEN-LAST:event_cmbChooseSizeActionPerformed
 
 public void fillPizzaSize()
 {
     String strPizzaSize;

     try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT * FROM size");
           ResultSet rs1 = stmt.getResultSet();

           cmbChooseSize.removeAllItems();

           while(rs1.next())
           {
               Size mySize = new Size();
               mySize.setSizeID(Integer.parseInt(rs1.getString("Size_ID")));

               mySize.setSize(rs1.getString("Size"));

               cmbChooseSize.addItem(mySize);
                mySize = null;
           }
           cmbChooseSize.setSelectedIndex(0);

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

 }

    @SuppressWarnings("static-access")
    private void mnuMainMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuMainMenuActionPerformed
    {//GEN-HEADEREND:event_mnuMainMenuActionPerformed
        // escapes to Main Menu

        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.setVisible(true);
        this.dispose();
     
                
    }//GEN-LAST:event_mnuMainMenuActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuExitActionPerformed
    {//GEN-HEADEREND:event_mnuExitActionPerformed
        // Exits program
        System.exit(0);
    }//GEN-LAST:event_mnuExitActionPerformed

    private void radTomatoSauceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_radTomatoSauceActionPerformed
    {//GEN-HEADEREND:event_radTomatoSauceActionPerformed
        // TODO add your handling code here:
        radTomatoSauce.setSelected(true);

    }//GEN-LAST:event_radTomatoSauceActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnClearActionPerformed
    {//GEN-HEADEREND:event_btnClearActionPerformed
        clearSpinners();
        txtNumOfPizzas.setText(null);
        ingredList.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void cmbChooseCrustActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbChooseCrustActionPerformed
    {//GEN-HEADEREND:event_cmbChooseCrustActionPerformed
        // TODO add your handling code here:
        
       if (cmbChooseCrust.getSelectedIndex() >= 0)
       {
        Crust myCrust = new Crust();
        myCrust = (Crust) cmbChooseCrust.getSelectedItem();
        crust = myCrust.getCrustID();
        }

       
    }//GEN-LAST:event_cmbChooseCrustActionPerformed

    private void cmbChoosePizzaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbChoosePizzaActionPerformed
    {//GEN-HEADEREND:event_cmbChoosePizzaActionPerformed
        if (cmbChoosePizza.getSelectedIndex() >= 0)
        {
            Specialty mySpecialty = new Specialty();
            mySpecialty = (Specialty) cmbChoosePizza.getSelectedItem();
            specialty = mySpecialty.getSpecialtyID();
        }
        fillSpinners();
    }//GEN-LAST:event_cmbChoosePizzaActionPerformed

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtDiscountActionPerformed
    {//GEN-HEADEREND:event_txtDiscountActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtDiscountActionPerformed

    private void cmbChoosePizzaItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_cmbChoosePizzaItemStateChanged
    {//GEN-HEADEREND:event_cmbChoosePizzaItemStateChanged
    
    }//GEN-LAST:event_cmbChoosePizzaItemStateChanged

    private void cmbChoosePizzaInputMethodTextChanged(java.awt.event.InputMethodEvent evt)//GEN-FIRST:event_cmbChoosePizzaInputMethodTextChanged
    {//GEN-HEADEREND:event_cmbChoosePizzaInputMethodTextChanged
      
    }//GEN-LAST:event_cmbChoosePizzaInputMethodTextChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
    
        fillSpinners();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddPizza1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPizza1ActionPerformed
        addPizza();
        btnAddPizza1.setEnabled(false);

    }//GEN-LAST:event_btnAddPizza1ActionPerformed

    private void btnEditPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPizzaActionPerformed
        EditPizzaUI myEditPizzaUI = new EditPizzaUI();
        myEditPizzaUI.setVisible(true);
    }//GEN-LAST:event_btnEditPizzaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cancelOrder();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        getSubTotal();
    }//GEN-LAST:event_btnTotalActionPerformed

    private void btnCompleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompleteOrderActionPerformed
        completeOrder();
    }//GEN-LAST:event_btnCompleteOrderActionPerformed

    private void btnNewPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPizzaActionPerformed
        //clear qty text field
        txtNumOfPizzas.setText(null);
        //clear spinners
        clearSpinners();
        //reload combo boxes
        fillPizzaSize();
        fillCrust();
        fillPizza();        
        //clear ingred array
        ingredList.clear();
        //re-enable the add pizza button
        btnAddPizza1.setEnabled(true);
        //increments the pizza number for the next pizza
        pizzaNum++;
    }//GEN-LAST:event_btnNewPizzaActionPerformed
    @SuppressWarnings("static-access")
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public void fillPizza()
    {
      String strPizzaType;

     if (cmbChoosePizza.getSelectedIndex()>= 0)
    {

        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           stmt.executeQuery("SELECT distinct(PizzaType), PizzaType_ID FROM pizza_prices");
           ResultSet rs1 = stmt.getResultSet();

           cmbChoosePizza.removeAllItems();

           while(rs1.next())
           {
               Specialty mySpecialty = new Specialty();
               mySpecialty.setSpecialtyID(rs1.getInt("PizzaType_ID"));
               mySpecialty.setSpecialty(rs1.getString("PizzaType"));
               cmbChoosePizza.addItem(mySpecialty);
               mySpecialty = null;
               
           }
           cmbChoosePizza.setSelectedIndex(0);

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void fillCrust()
 {
     String strCrust;
         if (cmbChooseSize.getSelectedIndex()>= 0)
         {
     try
        {



               Size mySize = (Size) cmbChooseSize.getSelectedItem();

               int crustTypeId = mySize.getSizeID(); // int = 32 bits
               String strQ = "select crust_type.CrustType_Id , crust_type.CrustType ";
               strQ = strQ + "from crust_type , baseprices ";
               strQ = strQ + "where crust_type.CrustType_ID = baseprices.CrustType_Id and ";
               strQ = strQ + "baseprices.Price > 0 and baseprices.Size_ID =  " + crustTypeId;
               Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               stmt.executeQuery(strQ);
               ResultSet rs1 = stmt.getResultSet();

               cmbChooseCrust.removeAllItems();

               while(rs1.next())
               {
                   Crust myCrust = new Crust();
                   myCrust.setCrustID(Integer.parseInt(rs1.getString("CrustType_ID")));
  
                   myCrust.setCrust(rs1.getString("CrustType"));
 
                   cmbChooseCrust.addItem(myCrust);
                   myCrust = null;
               }
               cmbChooseCrust.setSelectedIndex(0);
           }

        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
     }

 }

    ////////////////////////////////////////////////////////////////////////////
  public void fillSpinners()
  {
        clearSpinners();
        try
        {
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           sqlStatement = "select Ingredients_ID from pizza_prices where PizzaType_ID = " + specialty;
           stmt.executeQuery(sqlStatement);
           ResultSet rs1 = stmt.getResultSet();

            while(rs1.next())
            {
                PizzaPrices myPizzaPrices = new PizzaPrices();
                myPizzaPrices.setIngredientsID((rs1.getInt("Ingredients_ID")));
                ingredientID = (myPizzaPrices.getIngredientsID());

                 switch(ingredientID)
                 {
                     case 100001: spnCheese.setValue(2);
                     break;
                     case 100003: radTomatoSauce.setSelected(true);
                     break;
                     case 100004:radBBQ.setSelected(true);
                     break;
                     case 100006: spnPepperoni.setValue(1);
                     break;
                     case 100007: spnSausage.setValue(1);
                     break;
                     case 100008: spnGroundBeef.setValue(1);
                     break;
                     case 100009: spnPhillySteak.setValue(1);
                     break;
                     case 100010: spnHam.setValue(1);
                     break;
                     case 100011: spnBacon.setValue(1);
                     break;
                     case 100012: spnChicken.setValue(1);
                     break;
                     case 100013: spnFetaCheese.setValue(1);
                     break;
                     case 100014: spnBannanaPepper.setValue(1);
                     break;
                     case 100015: spnBlackOlives.setValue(1);
                     break;
                     case 100016: spnGreenPeppers.setValue(1);
                     break;
                     case 100017: spnMushrooms.setValue(1);
                     break;
                     case 100018: spnPineapple.setValue(1);
                     break;
                     case 100019: spnOnions.setValue(1);
                     break;
                     case 100025: spnHidden1.setValue(1);
                     break;
                     case 100026: spnHidden2.setValue(1);
                     break;
                     case 100027: spnHidden3.setValue(1);
                     break;
                     default: spnPepperoni.setValue(0);
                 }

                 if(specialty == 600003)
                 {
                     radBBQ.setSelected(true);
                 }
            }
        }


          catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void getSubTotal()
    {
        //query the database for lineprices of all pizzas on the current order

        double runningTotal;
        int gotRecord = 0;
        try
        {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeQuery("SELECT distinct(LinePrice) FROM customer_orderline WHERE CustomerOrder_ID = " + orderNumber
                    + "AND Pizza_ID = " + pizzaNum + ";");
            ResultSet rs1 = stmt.getResultSet();

            //sum the line prices
            while(rs1.next())
            {
            runningTotal = rs1.getDouble("LinePrice");
            subTotal = subTotal + runningTotal;
            gotRecord++;
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        //check to be sure there are pizzas for which there are prices to avoid error
        //subtract discount from the total
        if(gotRecord >0)
        {
            subTotal = subTotal - Integer.parseInt(txtDiscount.getText());
        }
        else
        {
            subTotal = 0;
        }
    }
  
    public void completeOrder()
    {
        String comments = txtComments.getText();
        double discount = Double.parseDouble(txtDiscount.getText());
        
        //update to customer orders table to include comments and discount
        try
        {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate("UPDATE customer_orders SET Comments = " + comments + "WHERE CustomerOrder_ID = " + orderNumber);
            stmt.executeUpdate("UPDATE customer_orders SET Discount = " + discount + "WHERE CustomerOrder_ID = " + orderNumber);

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        //run updateInventory method to update Onhand values in database
        updateInventory();

        //open orderinfoUI form
        OrderInfoUI myOrderInfoUI = new OrderInfoUI();
        myOrderInfoUI.setVisible(true);

        //dispose of this form so that next use of it is a new (cleared) instantiation
        this.dispose();

    }

    public void updateInventory()
    {
        int ingID;
        int pizzaType;
        int sauce;
        int sauceSize;
        int cheeseSize;
        try
        {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //query database for pizza size for each pizza in the order
            stmt.executeQuery ("Select distinct(Pizza_ID), Size_ID, PizzaQTY, PizzaType_ID FROM customer_orderline WHERE CustomerOrderNumber = "
                    + orderNumber + ";");
            ResultSet rs1 = stmt.getResultSet();

            //subtract for Pizza type to determine type of sauce used
            //use rs1 for size, pizzaQty,PizzaType
            //query in rs2 for amount of sauce usage per pizza then calculate for the quantity
            //update the sauce inventory in ingredients table
            while(rs1.next())
            {
                sauceSize = rs1.getInt("Size_ID");
                pizzaType = rs1.getInt("PizzaType_ID");
                if(pizzaType == 600003)
                {
                    sauce = 100003;
                }
                else
                {
                    sauce = 100004;
                }
                stmt.executeQuery ("Select UsageAmount FROM ingredients_usage WHERE Ingredients_ID = " + sauce + " AND Size_ID = " + sauceSize + ";");
                ResultSet rs2 = stmt.getResultSet();
                rs2.first();
                int sauceUsage = rs2.getInt("UsageAmount") * rs1.getInt("PizzaQTY");
                stmt.executeUpdate("UPDATE ingredients SET OnHand = (OnHand - " + sauceUsage + "} WHERE Ingredients_ID = " + sauce + ";");
            }

            //subtract base cheese amount from inventor
            while (rs1.next())
            {
                cheeseSize = rs1.getInt("Size_ID");
                stmt.executeQuery ("Select UsageAmount FROM ingredients_usage WHERE Ingredients_ID = 100001 AND Size_ID = " + cheeseSize + ";");
                int cheeseUsage = rs1.getInt("UsageAmount");
                cheeseUsage = cheeseUsage * rs1.getInt("PizzaQTY");
                stmt.executeUpdate("UPDATE ingredients SET OnHand = (OnHand - " + cheeseUsage + "} WHERE Ingredients_ID = " + 100001 + ";");
            }

            //subtract box if ToGo or Delivery
            //switch thru assinging the ingredient id for the box size to
            //update database to subtract the appropriate number of boxes
            while(rs1.next())
            {
                int boxSize = rs1.getInt("Size_ID");
                if (orderType.equals("ToGo") | orderType.equals("Delivery"))
                {
                    switch(boxSize)
                    {
                         case 400001: ingID = 100020;
                         break;
                         case 400002: ingID = 100021;
                         break;
                         case 400003: ingID = 100022;
                         break;
                         case 400004: ingID = 100023;
                         break;
                         case 400005: ingID = 100024;
                         break;
                         default: ingID = 100025;
                    }
                int boxUsage = rs1.getInt("PizzaQTY");
                stmt.executeUpdate("UPDATE Ingredients SET OnHand = (OnHand - " + boxUsage + ") WHERE Ingredients_ID = " + ingID);
                }
            }

            //subtract from OnHand for every ingredient in every pizza
            stmt.executeQuery ("Select * FROM customer_orderline WHERE CustomerOrderNumber = " + orderNumber + ";");
            ResultSet rs3 = stmt.getResultSet();

            while(rs3.next())
            {
                int itemID = rs3.getInt("Ingredients_ID");
                int pizzaSizeID = rs3.getInt("Size_ID");
                int itemQty = rs3.getInt("IngredientQTY");
                int pizzaQty = rs3.getInt("PizzaQTY");
                //get the usage amount for each ingredient
                stmt.executeQuery ("Select UsageAmount FROM ingredients_usage WHERE Ingredients_ID = " + itemID
                        + " AND Size_ID = " + pizzaSizeID + ";");
                ResultSet rs4 = stmt.getResultSet();
                rs4.first();
                int perPizzaIngredUsage = rs4.getInt("UsageAmount");
                int totalIngredUsage = perPizzaIngredUsage * itemQty * pizzaQty;
                stmt.executeUpdate("Update ingredients set OnHand = (OnHand - " + totalIngredUsage
                        + "} WHERE Ingredients_ID = " + itemID + ";");
            }

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        


    }





  
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PizzaOrderUI().setVisible(true);
            }
        });
    }
    boolean checkInventory = false;
    double subTotal = 0;
    int qtyPizzas;
    int pizzaNum = 1;
    int orderNumber;
    int numToppings;
    double numFreeToppings;
    double toppingPrice;
    double basePrice;
    int crustID;
    int sizeID;
    double linePrice;
    ArrayList ingredList = new ArrayList();
    int crust;
    int size;
    int ingredientID;
    String sqlStatement;
    int specialtyID;
    int specialty;
    String orderType;
    int customerNum;
    int pizzaTypeID;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPizza1;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCompleteOrder;
    private javax.swing.JButton btnEditPizza;
    private javax.swing.JButton btnNewPizza;
    private javax.swing.JButton btnTotal;
    private javax.swing.ButtonGroup btngrpSauce;
    private javax.swing.JComboBox cmbChooseCrust;
    private javax.swing.JComboBox cmbChoosePizza;
    private javax.swing.JComboBox cmbChooseSize;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBacon;
    private javax.swing.JLabel lblBannanPeppers;
    private javax.swing.JLabel lblBlackOlives;
    private javax.swing.JLabel lblCheese;
    private javax.swing.JLabel lblChicken;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblFetaCheese;
    private javax.swing.JLabel lblFinalTotal;
    private javax.swing.JLabel lblFinalTotalDisplay;
    private javax.swing.JLabel lblGreenPeppers;
    private javax.swing.JLabel lblGroundBeef;
    private javax.swing.JLabel lblHam;
    private javax.swing.JLabel lblHidden1;
    private javax.swing.JLabel lblHidden2;
    private javax.swing.JLabel lblHidden3;
    private javax.swing.JLabel lblMushrooms;
    private javax.swing.JLabel lblNumOfPizzas;
    private javax.swing.JLabel lblOnions;
    private javax.swing.JLabel lblPepperoni;
    private javax.swing.JLabel lblPhillySteak;
    private javax.swing.JLabel lblPineapple;
    private javax.swing.JLabel lblPizzajpg;
    private javax.swing.JLabel lblSausage;
    private javax.swing.JPanel lblSmlBlueStripe;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuMainMenu;
    private javax.swing.JPanel pnlBackgroundColor;
    private javax.swing.JPanel pnlBlueStripe;
    private javax.swing.JPanel pnlGreenStripe;
    private javax.swing.JRadioButton radBBQ;
    private javax.swing.JRadioButton radTomatoSauce;
    private javax.swing.JSpinner spnBacon;
    private javax.swing.JSpinner spnBannanaPepper;
    private javax.swing.JSpinner spnBlackOlives;
    private javax.swing.JSpinner spnCheese;
    private javax.swing.JSpinner spnChicken;
    private javax.swing.JSpinner spnFetaCheese;
    private javax.swing.JSpinner spnGreenPeppers;
    private javax.swing.JSpinner spnGroundBeef;
    private javax.swing.JSpinner spnHam;
    private javax.swing.JSpinner spnHidden1;
    private javax.swing.JSpinner spnHidden2;
    private javax.swing.JSpinner spnHidden3;
    private javax.swing.JSpinner spnMushrooms;
    private javax.swing.JSpinner spnOnions;
    private javax.swing.JSpinner spnPepperoni;
    private javax.swing.JSpinner spnPhillySteak;
    private javax.swing.JSpinner spnPineapple;
    private javax.swing.JSpinner spnSausage;
    private javax.swing.JTextArea txtComments;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtNumOfPizzas;
    private javax.swing.JTextArea txtPizzaDescription;
    // End of variables declaration//GEN-END:variables

}

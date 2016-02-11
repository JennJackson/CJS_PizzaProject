/*
Caleb Finkenbiner
Oct 26, 2011
Program: CJS_PizzaProject
Purpose: Create a pizza_prices class for PizzaOrderUI
Mods
*/








/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgPizza;

/**
 *

 */
public class PizzaPrices
{
   private int pizzaTypeID;
   private int ingredientsID;
   private String pizzaType;

    /**
     * @return the pizzaTypeID
     */
    public int getPizzaTypeID()
    {
        return pizzaTypeID;
    }

    /**
     * @param pizzaTypeID the pizzaTypeID to set
     */
    public void setPizzaTypeID(int pizzaTypeID)
    {
        this.pizzaTypeID = pizzaTypeID;
    }

    /**
     * @return the ingredientsID
     */
    public int getIngredientsID()
    {
        return ingredientsID;
    }

    /**
     * @param ingredientsID the ingredientsID to set
     */
    public void setIngredientsID(int ingredientsID)
    {
        this.ingredientsID = ingredientsID;
    }

    /**
     * @return the pizzaType
     */
    public String getPizzaType()
    {
        return pizzaType;
    }

    /**
     * @param pizzaType the pizzaType to set
     */
    public void setPizzaType(String pizzaType)
    {
        this.pizzaType = pizzaType;
    }
     @Override
       public String toString()
       {
           return this.getPizzaType();
       }
}

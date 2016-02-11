/*************
Jennifer Jackson
2011-09-22
Program
Purpose
Mods
*************/




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgPizza;



public class Crust {
    private int crustID;
    private String crust;

    /**
     * @return the crustID
     */
    public int getCrustID()
    {
        return crustID;
    }

    /**
     * @param crustID the crustID to set
     */
    public void setCrustID(int crustID)
    {
        this.crustID = crustID;
    }

    /**
     * @return the crust
     */
    public String getCrust()
    {
        return crust;
    }

    /**
     * @param crust the crust to set
     */
    public void setCrust(String crust)
    {
        this.crust = crust;
    }

     @Override
       public String toString()
       {
           return this.getCrust();
       }


}

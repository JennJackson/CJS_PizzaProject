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

package pkgPizza;

/**
 *
 * @author Steve
 */
public class InventoryOrders
{

    private int invOrderNum;
    private String ingDescription;
    private int ingredID;

    /**
     * @return the invOrderNum
     */
    public int getInvOrderNum()
    {
        return invOrderNum;
    }

    /**
     * @param invOrderNum the invOrderNum to set
     */
    public void setInvOrderNum(int invOrderNum)
    {
        this.invOrderNum = invOrderNum;


    }

    /**
     * @return the ingDescription
     */
    public String getIngDescription() {
        return ingDescription;
    }

    /**
     * @param ingDescription the ingDescription to set
     */
    public void setIngDescription(String ingDescriptionn) {
        this.ingDescription = ingDescriptionn;
    }

        @Override
    public String toString()
    {
        return this.getIngDescription();
    }

    /**
     * @return the ingredID
     */
    public int getIngredID() {
        return ingredID;
    }

    /**
     * @param ingredID the ingredID to set
     */
    public void setIngredID(int ingredID) {
        this.ingredID = ingredID;
    }


}

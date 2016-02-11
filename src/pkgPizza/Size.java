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


public class Size {
 // Declare Variables
 private int sizeID;
 private String size;

    /**
     * @return the sizeID
     */
    public int getSizeID()
    {
        return sizeID;
    }

    /**
     * @param sizeID the sizeID to set
     */
    public void setSizeID(int sizeID)
    {
        this.sizeID = sizeID;
    }

    /**
     * @return the size
     */
    public String getSize()
    {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return this.getSize();
    }




}

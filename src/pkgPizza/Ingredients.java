/*
Author:     Steve Blue
Date:       Oct 14, 2011
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
public class Ingredients {
   private int id;
   private String description;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString()
    {
        return this.getDescription();
    }
}

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


public class Specialty {
    private int specialtyID;
    private String specialty;

    /**
     * @return the specialtyID
     */
    public int getSpecialtyID()
    {
        return specialtyID;
    }

    /**
     * @param specialtyID the specialtyID to set
     */
    public void setSpecialtyID(int specialtyID)
    {
        this.specialtyID = specialtyID;
    }

    /**
     * @return the specialty
     */
    public String getSpecialty()
    {
        return specialty;
    }

    /**
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

     @Override
   public String toString()
   {
       return this.getSpecialty();
   }

}

/*
Author:     Steve Blue
Date:       Oct 16, 2011
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
public class EmployeePasswords
{
    private int EmployeeID;
    private String Password;
    private boolean SysPermissions = false;

    /**
     * @return the EmployeeID
     */
    public int getEmployeeID() {
        return EmployeeID;
    }

    /**
     * @param EmployeeID the EmployeeID to set
     */
    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the SysPermissions
     */
    public boolean getSysPermissions() {
        return isSysPermissions();
    }

    /**
     * @param SysPermissions the SysPermissions to set
     */
    public void setSysPermissions(int SysPermissions) {
        this.setSysPermissions(SysPermissions);
    }

    /**
     * @return the SysPermissions
     */
    public boolean isSysPermissions() {
        return SysPermissions;
    }

    /**
     * @param SysPermissions the SysPermissions to set
     */
    public void setSysPermissions(boolean SysPermissions) {
        this.SysPermissions = SysPermissions;
    }

}

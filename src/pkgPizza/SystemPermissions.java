/*
Author:     Steve Blue
Date:       Oct 21, 2011
Program:
Purpose:
Mods:       
*/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgPizza;

import java.sql.*;



/**
 *
 * @author Steve
 */
public class SystemPermissions
{
    int intID;
    String strPwd;
    boolean passWordCheck;
    boolean systemPermissions;

    private static Connection conn = DBSingle.getConn().getConnection();

    public SystemPermissions(int i, String s)
    {
        intID = i;
        strPwd = s;
    }

    public SystemPermissions()
    {
        
    }

    public void passWordCheck()
    {
        try
        {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeQuery("SELECT * FROM employee_passwords");
            ResultSet rs1 = stmt.getResultSet();

           while(rs1.next())
           {
               EmployeePasswords myEmployeePasswords = new EmployeePasswords();
               myEmployeePasswords.setEmployeeID(Integer.parseInt(rs1.getString("Employee_ID")));
               myEmployeePasswords.setPassword((rs1.getString("EmployeePassword")));
               myEmployeePasswords.setSysPermissions(rs1.getBoolean("SystemPermissions"));


               if(intID == myEmployeePasswords.getEmployeeID() &&
                       (strPwd.equals(myEmployeePasswords.getPassword())))
               {
                   passWordCheck = true;
                   if (myEmployeePasswords.getSysPermissions())
                   {
                        systemPermissions = true;
                   }
                   else
                   {
                       System.out.println("system permissions: " + myEmployeePasswords.getSysPermissions());
                   }
               }
               else
               {
                    //System.out.println("password check: " + passWordCheck);
               }
            }

        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    }


}

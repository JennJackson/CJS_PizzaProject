/*
Author:     Steve Blue
Date:       Oct 10, 2011
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

public final class DBSingle {
    private String username;
    private String password;
    private String database;
    private String driver;
    private static Connection conn;

    private DBSingle() {}

    private static class InstanceHolder
    {
        public static final DBSingle instance = new DBSingle();
    }

    public static DBSingle getConn() {
        return InstanceHolder.instance;
    }

    public void connectToDB() throws ClassNotFoundException, SQLException
    {
        if(username == null)
            username = "root";
        if(password == null)
            password = "class1111";
        if(database == null)
            database = "jdbc:odbc:PizzaProject";
            //database = "jdbc:mysql://localhost:3306/scheduledatabase";
        if(driver == null)
            driver = "sun.jdbc.odbc.JdbcOdbcDriver";

        Class.forName(getDriver());

        if(conn != null) {
            // same as saying conn=conn;
        } else {
            conn = DriverManager.getConnection(database, username, password);
        }
        if(!conn.isClosed()) {
            System.out.println("Connection Succeeded");
        }
    }

    public String getDriver() {
        return driver;
    }

    public Connection getConnection() {
        if(conn == null)
        {
            try {
                connectToDB();
            } catch (ClassNotFoundException ex) {
                System.out.println("Class Not Found");
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("SQL Exception");
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }
}


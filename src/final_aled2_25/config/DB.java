/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author aleja
 */

public class DB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/taller?useSSL=false&serverTimezone=UTC";
        String user = "root";   // tu usuario MySQL
        String pass = "";       // tu contraseña MySQL
        return DriverManager.getConnection(url, user, pass);
    }
}

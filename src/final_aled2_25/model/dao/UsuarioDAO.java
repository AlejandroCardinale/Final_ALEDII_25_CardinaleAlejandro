/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import final_aled2_25.model.entity.Usuario;
import final_aled2_25.config.DB;
import java.sql.*;

/**
 *
 * @author aleja
 */
public class UsuarioDAO {
    
    public Usuario buscarPorUsername (String username) {
        
        Usuario u = null;

        String sql = """
                     SELECT u.id, u.username, u.password, r.nombre AS rol
                     FROM usuario u
                     JOIN usuario_rol ur ON ur.usuario_id = u.id
                     JOIN rol r ON r.id = ur.rol_id
                     WHERE u.username = ?
                     """;

        try ( Connection cn = DB.getConnection(); 
              PreparedStatement ps = cn.prepareStatement (sql)) {
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setRol(rs.getString("rol"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }
}
    
    
    
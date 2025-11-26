/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aleja
 */
        
package final_aled2_25.model.dao;

import final_aled2_25.config.DB;
import final_aled2_25.model.dao.ChapaItemDAO;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
 
public class JdbcChapaItemDAO implements ChapaItemDAO {
    @Override public void insert(String dom, String desc, BigDecimal horas, BigDecimal precioHora, BigDecimal subtotal) {
        String sql = "INSERT INTO chapa_item(vehiculo_dominio, descripcion, horas, precioHora, subtotal) VALUES(?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dom);
            ps.setString(2, desc);
            ps.setBigDecimal(3, horas);
            ps.setBigDecimal(4, precioHora);
            ps.setBigDecimal(5, subtotal);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public void delete(int id) {
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM chapa_item WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public List<Object[]> findByVehiculo(String dom) {
        List<Object[]> out = new ArrayList<>();
        String sql = "SELECT id, descripcion, horas, precioHora, subtotal FROM chapa_item WHERE vehiculo_dominio=? ORDER BY id DESC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dom);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(new Object[]{
                    rs.getInt("id"), rs.getString("descripcion"),
                    rs.getBigDecimal("horas"), rs.getBigDecimal("precioHora"), rs.getBigDecimal("subtotal")
                });
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}

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
import final_aled2_25.model.dao.MecanicaItemDAO;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
 
public class JdbcMecanicaItemDAO implements MecanicaItemDAO {
    @Override public void insert(String dom, int repuestoId, BigDecimal cant, BigDecimal precioUnit, BigDecimal subtotal) {
        String sql = "INSERT INTO mecanica_item(vehiculo_dominio,repuesto_id,cantidad,precioUnit,subtotal) VALUES(?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dom);
            ps.setInt(2, repuestoId);
            ps.setBigDecimal(3, cant);
            ps.setBigDecimal(4, precioUnit);
            ps.setBigDecimal(5, subtotal);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public void delete(int id) {
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM mecanica_item WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public List<Object[]> findByVehiculo(String dom) {
        List<Object[]> out = new ArrayList<>();
        String sql = """
            SELECT mi.id, CONCAT(r.codigo,' - ',r.nombre) AS repuesto, mi.precioUnit, mi.cantidad, mi.subtotal
            FROM mecanica_item mi
            JOIN repuesto r ON r.id = mi.repuesto_id
            WHERE mi.vehiculo_dominio=?
            ORDER BY mi.id DESC
        """;
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dom);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(new Object[]{
                    rs.getInt("id"), rs.getString("repuesto"),
                    rs.getBigDecimal("precioUnit"), rs.getBigDecimal("cantidad"), rs.getBigDecimal("subtotal")
                });
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}
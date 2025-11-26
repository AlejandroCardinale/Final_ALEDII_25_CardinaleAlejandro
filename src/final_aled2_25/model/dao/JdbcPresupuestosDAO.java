/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import final_aled2_25.model.dao.PresupuestosDAO;
import final_aled2_25.config.DB;

/**
 *
 * @author @author aleja
 */
public class JdbcPresupuestosDAO implements PresupuestosDAO{
    
    @Override
    public List<String> vehiculosConItems(){
        String sql = """
                     SELECT DISTINCT vehiculo_dominio FROM chapa_item
                     UNION
                     SELECT DISTINCT vehiculo_dominio FROM mecanica_item
                     ORDER BY 1
                     """;
        
        List<String> out = new ArrayList<>();
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while (rs.next()) out.add(rs.getString(1));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    
    @Override
    public List<Object[]> itemsPorVehiculo(String dom){
        String sql = """
                     SELECT 'CHAPA' AS tipo, descripcion, 
                     horas AS cantidad, precioHora AS precio, subtotal
                     FROM chapa_item WHERE vehiculo_dominio = ? 
                     UNION ALL 
                     SELECT 'MECANICA' AS tipo,
                     CONCAT(r.codigo, ' - ', r.nombre) AS descripcion,
                     mi.cantidad AS cantidad, mi.precioUnit AS precio, mi.subtotal
                     FROM mecanica_item mi
                     JOIN repuesto r ON r.id = mi.repuesto_id
                     WHERE mi.vehiculo_dominio = ?
                     ORDER BY tipo, descripcion
                     """;
        List<Object[]> out = new ArrayList<>();
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
                ps.setString(1, dom);
                ps.setString(2, dom);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        out.add(new Object[]{
                        rs.getString("tipo"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("cantidad"),
                        rs.getBigDecimal("precio"),
                        rs.getBigDecimal("subtotal")
                    });
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    
    @Override
    public BigDecimal totalChapa(String dom){
        String sql = "SELECT IFNULL(SUM(subtotal),0) FROM chapa_item WHERE vehiculo_dominio =?";
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
                ps.setString(1, dom);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next(); 
                    return rs.getBigDecimal(1);}
                
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
    }
    
    @Override
    public BigDecimal totalMecanica(String dom){
        String sql = "SELECT IFNULL(SUM(subtotal),0) FROM mecanica_item WHERE vehiculo_dominio =?";
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
                ps.setString(1, dom);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next(); 
                    return rs.getBigDecimal(1);}
                
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
    }
    
    @Override
    public void asegurarPresupuesto(String dom){
        String sql = "INSERT IGNORE INTO presupuesto(vehiculo_dominio) VALUES (?)";
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
                ps.setString(1, dom);
                ps.executeUpdate();
  
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void actualizarTotales(String dom, BigDecimal tChapa, BigDecimal tMec){
        String sql = """
                     UPDATE presupuesto
                     SET totalChapa=?, totalMecanica=?, totalGeneral=IFNULL(?,0)+IFNULL(?,0)
                     WHERE vehiculo_dominio=?
                     """;
        try(Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
                    ps.setBigDecimal(1,tChapa);
                    ps.setBigDecimal(2,tMec);
                    ps.setBigDecimal(3,tChapa);
                    ps.setBigDecimal(4,tMec);
                    ps.setString(5, dom);
                    ps.executeUpdate();
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
    }
    
    @Override
    public String obtenerEstado(String dom){
        String sql = "SELECT estado FROM presupuesto WHERE vehiculo_dominio=?";
        try (Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,dom);
        try(ResultSet rs = ps.executeQuery()){
            if(rs.next()) return rs.getString(1);
            return null;
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void marcarCompleto(String dom){
        String sql = "UPDATE presupuesto SET estado='COMPLETO', fechaCierre= NOW() WHERE vehiculo_dominio=?";
        try (Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,dom);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

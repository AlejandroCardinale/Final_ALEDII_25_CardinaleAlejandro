/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import final_aled2_25.config.DB;
import final_aled2_25.model.entity.Vehiculo;
import java.sql.*;
import java.util.*;

/**
 *
 * @author aleja
 */
public class JdbcVehiculoDAO  implements VehiculoDAO{ //implementacion del DAO
    
    @Override 
    public void insert (Vehiculo v){
        String sql = "INSERT INTO vehiculo(dominio,anio,marca,modelo,color) VALUES(?,?,?,?,?)";
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, v.getDominio());
            //if(v.getAnio()==null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, v.getAnio());
            ps.setInt(2,v.getAnio());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setString(5, v.getColor());
            ps.executeUpdate();
            
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }   
    @Override
    public void update (Vehiculo v){
        String sql = "UPDATE vehiculo SET anio=?, marca=?, modelo=?, color=? WHERE dominio=?";
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement(sql)){
            //if(v.getAnio()==null) ps.setNull(1, Types.INTEGER); else ps.setInt(2, v.getAnio());
            ps.setInt(1,v.getAnio());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getColor());
            ps.setString(5, v.getDominio());
            ps.executeUpdate();
            
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void delete (String dominio){
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement("DELETE FROM vehiculo WHERE dominio=?")){
            ps.setString(1, dominio);
            ps.executeUpdate();
    }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<Vehiculo> findAll() {
        List<Vehiculo> out = new ArrayList<>();
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement("SELECT * FROM vehiculo ORDER BY dominio");
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                Vehiculo v = new Vehiculo();
                v.setDominio(rs.getString("dominio"));
                int anio = rs.getInt("anio"); v.setAnio(rs.wasNull()? null: anio);
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setColor(rs.getString("color"));
                out.add(v);
            }
            
    }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    return out;
    }
    @Override
    public boolean exists (String dominio){
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement("SELECT 1 FROM vehiculo WHERE dominio=?")){
            ps.setString(1, dominio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
    }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

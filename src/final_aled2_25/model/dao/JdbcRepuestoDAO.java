/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import final_aled2_25.config.DB;
import final_aled2_25.model.entity.Repuesto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
/**
 *
 * @author aleja
 */
public class JdbcRepuestoDAO implements RepuestoDAO{
    
    @Override
    public List<Repuesto> findAll(){
        List<Repuesto> out = new ArrayList<>();
            try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement("SELECT * FROM repuesto ORDER BY codigo");
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                Repuesto r = new Repuesto();
                r.setId(rs.getInt("id"));
                r.setCodigo(rs.getString("codigo"));
                r.setNombre(rs.getString("nombre"));
                r.setPrecio(rs.getBigDecimal("precio"));
                out.add(r);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    
    @Override
    public Repuesto findById(int id){
        try(Connection con = DB.getConnection(); 
                PreparedStatement ps = con.prepareStatement("SELECT * FROM repuesto WHERE id=?")){
            ps.setInt(1, id);
            try (ResultSet rs= ps.executeQuery()){
                if(!rs.next()) return null;
                Repuesto r = new Repuesto();
                r.setId(rs.getInt("id"));
                r.setCodigo(rs.getString("codigo"));
                r.setNombre(rs.getString("nombre"));
                r.setPrecio(rs.getBigDecimal("precio"));
                return r;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

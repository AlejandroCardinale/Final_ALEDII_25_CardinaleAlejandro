/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import final_aled2_25.model.entity.Vehiculo;

/**
 *
 * @author aleja
 */
public interface VehiculoDAO {
    
    void insert (Vehiculo v);
    void update (Vehiculo v);
    void delete (String dominio);
    java.util.List<Vehiculo> findAll();
    boolean exists(String dominio);
    
}

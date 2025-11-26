/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aleja
 */
package final_aled2_25.model.dao;

import java.util.List;
 
public interface MecanicaItemDAO {
    void insert(String dominio, int repuestoId, java.math.BigDecimal cantidad, java.math.BigDecimal precioUnit, java.math.BigDecimal subtotal);
    void delete(int id);
    java.util.List<Object[]> findByVehiculo(String dominio); // [id, repuesto, precioUnit, cantidad, subtotal]
}
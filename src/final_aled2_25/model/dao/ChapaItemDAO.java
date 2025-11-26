/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import java.util.List;

/**
 *
 * @author aleja
 */
public interface ChapaItemDAO {
    void insert (String dominio, String descripcion, java.math.BigDecimal horas, java.math.BigDecimal precioHora, java.math.BigDecimal subtotal);
    void delete(int id);
    java.util.List<Object[]> findByVehiculo(String dominio); //[id, descripcion, horas, precioHora, subtotal]

}

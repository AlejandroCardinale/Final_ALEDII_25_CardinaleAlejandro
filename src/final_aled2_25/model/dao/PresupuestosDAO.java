/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;

import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author aleja
 */
public interface PresupuestosDAO {
    
    List<String> vehiculosConItems();
    List<Object[]> itemsPorVehiculo(String dominio);
    
    BigDecimal totalChapa(String dominio);
    BigDecimal totalMecanica(String dominio);
    
    void asegurarPresupuesto(String dominio);
    void actualizarTotales(String dominio, BigDecimal tChapa, BigDecimal tMec);
    String obtenerEstado(String dominio);
    void marcarCompleto(String dominio);
    
}

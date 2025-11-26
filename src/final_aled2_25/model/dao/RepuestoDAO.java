/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.dao;
import java.util.List;
import final_aled2_25.model.entity.Repuesto;

/**
 *
 * @author aleja
 */
public interface RepuestoDAO {
    java.util.List<Repuesto> findAll();
    Repuesto findById(int id);
}

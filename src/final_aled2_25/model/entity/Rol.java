/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.model.entity;

/**
 *
 * @author aleja
 */
public class Rol {
    private int id;
    private String nombre; //ADMIN|CHAPISTA|MECANICO
    public Rol() {}    
    public Rol (int id, String nombre) {
        this.id = id; 
        this.nombre= nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override 
    public String toString(){
        return nombre;
    }
}

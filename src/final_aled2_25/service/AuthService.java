/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_aled2_25.service;
import final_aled2_25.model.dao.UsuarioDAO;
import final_aled2_25.model.entity.Usuario;


/**
 *
 * @author aleja
 */
public class AuthService {
    private final UsuarioDAO dao = new UsuarioDAO();
    
    public Usuario login(String username, String password) throws Exception {
        if (username.isBlank() || password.isBlank()){
            throw new Exception ("Debe ingresar usuario y contraseña, para continuar");
        }
        
        Usuario u = dao.buscarPorUsername(username);
        if (u==null) throw new Exception ("Usuario o contraseña incorrectos");
        if (!u.getPassword().equals(password)) throw new Exception ("Usuario o Contraseña incorrectos");
        return u;
    }
    
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acesso.DAO;


import Acesso.DTO.DatosDTO;
import java.util.List;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mk
 */
public class Conexion {
    
    private final String DB="interpro";
    private final String usr="root";
    private final String pwd="123";
    String url="jdbc:mysql://localhost:3306/"+ DB +"?useUniCode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    Connection conexion = null;
    
    List<DatosDTO> listadatos = new ArrayList<>();
    public void abrirconexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usr, pwd);
        } catch (ClassNotFoundException ex){
        
        } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarconexion(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean LeerDatos(){
        boolean estado = true;
        PreparedStatement ps;
        DatosDTO datosdto;
        try {
            ps = conexion.prepareStatement("select * from Datos");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                datosdto = new DatosDTO();
                datosdto.setId(rs.getInt("id"));
                datosdto.setNombre(rs.getString("Nombre"));
                datosdto.setEdad(rs.getInt("edad"));
                datosdto.setCorreo(rs.getString("Correo"));
                listadatos.add(datosdto);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            estado = false;
        }
        
        return estado;
    }
    
    public boolean GuardarDatos(DatosDTO datosDTO){
        try {
            PreparedStatement ps;
            ps = conexion.prepareStatement("insert into Datos(Nombre,edad,Correo) values(?,?,?)");
            ps.setString(1, datosDTO.getNombre());
            ps.setInt(2, datosDTO.getEdad());
            ps.setString(3, datosDTO.getCorreo());
            ps.execute();
        
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }  
        return true;
    }
    
    public boolean ActualizaDatos(DatosDTO datosDTO){
        PreparedStatement ps;
        try {
            ps = conexion.prepareStatement("update Datos set Nombre = ?, edad = ?, Correo = ? where id = ?");
            ps.setString(1, datosDTO.getNombre());
            ps.setInt(2, datosDTO.getEdad());
            ps.setString(3, datosDTO.getCorreo());
            ps.setInt(4, datosDTO.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public boolean BorrarDatos (int id){
        PreparedStatement ps;
        try {
            ps = conexion.prepareStatement("delete from Datos where id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public List<DatosDTO> Listado(){
        return listadatos;
        
    }

}

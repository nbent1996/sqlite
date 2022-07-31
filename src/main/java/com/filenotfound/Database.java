package com.filenotfound;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class Database {

    
    /*Atributos*/
    private static Database instancia;
    private static String url = "src/main/java/com/filenotfound/database.db";
    private static Conexion conexion;
    /*Atributos*/
 /*Constructores*/

 /*Constructores*/
 /*Comportamiento*/
    public static Database getInstancia() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }
    public static Conexion getConexion(){
        if(conexion == null){
            try {
                conexion = conectar(url);
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conexion;
    }
    public static Conexion conectar(String url) throws SQLException, NamingException {
        try {
            Connection c = null;
            Statement s = null;
            c = DriverManager.getConnection("jdbc:sqlite:"+url);

            c.setAutoCommit(false);
            s = c.createStatement();
            return new Conexion(s, c);
        } catch (SQLException ex) {
            int codigo = ex.getErrorCode();
            String errorTexto = "Codigo de Error: " + codigo + " // Mensaje: " + ex.getMessage();
            System.out.println(errorTexto);
            if (codigo == 0) {
                throw new SQLException(errorTexto);
            }
        }
        return null;
    }
    public boolean desconectar(Conexion conexion){
        try {
            if (conexion.getConexion() != null) {
                conexion.getConexion().close();
                conexion.setConexion(null);
                conexion.setStmt(null);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public int actualizar(String sql) throws SQLException, NamingException {
        try {
            Conexion DTOConexion = getConexion();
            int retorno = DTOConexion.getStmt().executeUpdate(sql);
            desconectar(DTOConexion);
            return retorno;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    

    public ResultSet consultar(String sql) throws Exception, SQLException {
        try {
            Conexion DTOConexion = getConexion();
            ResultSet rs = DTOConexion.getStmt().executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            throw ex;
        }
        
    }

    /*Comportamiento*/
 /*Setters y Getters*/

    public static String getUrl() {
        return url;
    }
    /*Setters y Getters*/


}

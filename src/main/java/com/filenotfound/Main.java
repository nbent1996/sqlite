package com.filenotfound;

import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
        try {
            Database db = Database.getInstancia();
            ResultSet rsConsulta = db.consultar("SELECT * FROM Factura");
            System.out.println(rsConsulta.getDouble("monto"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

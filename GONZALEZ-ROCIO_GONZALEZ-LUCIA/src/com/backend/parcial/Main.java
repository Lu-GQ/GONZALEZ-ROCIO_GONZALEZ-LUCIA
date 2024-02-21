package com.backend.parcial;

import com.backend.parcial.dbconnection.H2Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection con = H2Connection.getConnection();
        Statement stmt = con.createStatement();

        //Codigo para consultar todos los registros de la tabla TEST
        String sql = "select * from TEST";
        ResultSet rd = stmt.executeQuery(sql);

        //Código para recorrer el resultado de la consulta
        while(rd.next()) {
            System.out.println(rd.getInt(1) + rd.getString(2));
        }
    }
}
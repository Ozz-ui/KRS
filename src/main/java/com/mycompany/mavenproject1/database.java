package com.mycompany.mavenproject1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicos
 */
public class database {
    final String host = "jdbc:mysql://localhost:3306/krs";
    final String username = "root";
    final String password = "";
    
    public Connection koneksi(){
        Connection connection = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(host, username, password);
        }catch (ClassNotFoundException e){
            System.err.println("Class Not Found : " + e.getMessage());
        }catch (SQLException e){
            System.err.println("SQL Error : " + e.getMessage());
        }
    return connection;
    // try & catch untuk mengecek apakah ada error, catch untuk menangkap error
    }
    
    // -- READ --   
//    Object readDB (String kolom, String table, String kondisi){
//        Connection con = koneksi();
//        
//        if(con != null){
//            try {
//                String query = "SELECT * FROM dosen";
//                Statement st = con.createStatement();
//                ResultSet rs = st.executeQuery(query);
////                con.close();
//                
//                return rs;
//            }catch (SQLException e){
//                System.err.println("SQL Error : " + e.getMessage());
//            }
//        }
//        String query = "SELECT " + kolom + "FROM " + table + "WHERE " + kondisi + ";";
//        
//        return null;
//    }
    
    // -- INSERT --   
//     public boolean insertDB(String table, String kolom, String values) {
//        Connection con = koneksi();
//        if (con != null) {
//            try {
//                String query = "INSERT INTO " + table + " (" + kolom + ") VALUES (" + values + ");";
//                Statement st = con.createStatement();
//                st.executeUpdate(query);
//                con.close();
//                return true; // Berhasil
//            } catch (SQLException e) {
//                System.err.println("SQL Error: " + e.getMessage());
//            }
//        }
//        return false; // Gagal
//    }
     
    // --- UPDATE ---
//    public boolean updateDB(String table, String setValues, String kondisi) {
//        Connection con = koneksi();
//        if (con != null) {
//            try {
//                String query = "UPDATE " + table + " SET " + setValues + " WHERE " + kondisi + ";";
//                Statement st = con.createStatement();
//                st.executeUpdate(query);
//                con.close();
//                return true;
//            } catch (SQLException e) {
//                System.err.println("SQL Error: " + e.getMessage());
//            }
//        }
//        return false;
//    }
//     
     // --- DELETE ---
//    public boolean deleteDB(String table, String kondisi) {
//        Connection con = koneksi();
//        if (con != null) {
//            try {
//                String query = "DELETE FROM " + table + " WHERE " + kondisi + ";";
//                Statement st = con.createStatement();
//                st.executeUpdate(query);
//                con.close();
//                return true;
//            } catch (SQLException e) {
//                System.err.println("SQL Error: " + e.getMessage());
//            }
//        }
//        return false;
//    }
}



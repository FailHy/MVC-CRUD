/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package inventaris;


/**
 *
 * @author user
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DatabaseModel {
    private static final String URL = "jdbc:mysql://localhost:3306/inventarisbuku?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "pail";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }



    public void tambahdata(int nomorbuku, String judulbuku, String pengarang, int tahunterbit) {
        if (!cekData(nomorbuku)) { // Memeriksa apakah nomor buku sudah ada di database
            String query = "INSERT INTO data (nomorbuku, judulbuku, pengarang, tahunterbit) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, nomorbuku);
                pstmt.setString(2, judulbuku);
                pstmt.setString(3, pengarang);
                pstmt.setInt(4, tahunterbit);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data dengan Nomor Buku " + nomorbuku + " sudah ada di database.");
        }
    }
    
    public List<Object[]> bacadata() {
        List<Object[]> listData = new ArrayList<>();
        String query = "SELECT * FROM data";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int nomorbuku = rs.getInt("nomorbuku");
                String judulbuku = rs.getString("judulbuku");
                String pengarang = rs.getString("pengarang");
                int tahunterbit = rs.getInt("tahunterbit");

                Object[] datanya = {nomorbuku, judulbuku, pengarang, tahunterbit};
                listData.add(datanya);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void updatedata(int nomorbuku, String judulbuku, String pengarang, int tahunterbit) {
        if (cekData(nomorbuku)) { // Memeriksa apakah nomor buku sudah ada di database sebelum melakukan update
            String query = "UPDATE data SET judulbuku = ?, pengarang = ?, tahunterbit = ? WHERE nomorbuku = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, judulbuku);
                pstmt.setString(2, pengarang);
                pstmt.setInt(3, tahunterbit);
                pstmt.setInt(4, nomorbuku);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data dengan Nomor Buku " + nomorbuku + " tidak ditemukan di database.");
        }
    }

    public void hapusdata(int nomorbuku) {
        String query = "DELETE FROM data WHERE nomorbuku = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, nomorbuku);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int banyakdata(){
        
        String query = "SELECT TABLE_ROWS AS baris FROM `information_schema`.`tables` WHERE `table_schema` = 'inventarisbuku'";
        
        int returndata = 0 ;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int banyak = rs.getInt("baris");
                returndata = banyak;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returndata ;
    }
    
    public boolean cekData(int nomorbuku) {
        
        JOptionPane jp = new JOptionPane();
        
        String query = "SELECT COUNT(*) AS count FROM data WHERE nomorbuku = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, nomorbuku);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Jika count > 0, berarti data ada di database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Jika terjadi exception atau tidak ada data
    }
}
package com.perpustakaan.dao;

import com.perpustakaan.config.Database;
import com.perpustakaan.model.Anggota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnggotaDAO {
    public List<Anggota> getAll() throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Anggota(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("alamat"),
                    rs.getString("telepon")
                ));
            }
        }
        return list;
    }

    public void insert(Anggota anggota) throws SQLException {
        String sql = "INSERT INTO anggota (nama, alamat, telepon) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, anggota.getNama());
            pstmt.setString(2, anggota.getAlamat());
            pstmt.setString(3, anggota.getTelepon());
            pstmt.executeUpdate();
        }
    }

    public void update(Anggota anggota) throws SQLException {
        String sql = "UPDATE anggota SET nama=?, alamat=?, telepon=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, anggota.getNama());
            pstmt.setString(2, anggota.getAlamat());
            pstmt.setString(3, anggota.getTelepon());
            pstmt.setInt(4, anggota.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM anggota WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

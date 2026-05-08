package com.perpustakaan.dao;

import com.perpustakaan.config.Database;
import com.perpustakaan.model.Buku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {
    public List<Buku> getAll() throws SQLException {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("penerbit"),
                    rs.getInt("tahun")
                ));
            }
        }
        return list;
    }

    public void insert(Buku buku) throws SQLException {
        String sql = "INSERT INTO buku (judul, pengarang, penerbit, tahun) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buku.getJudul());
            pstmt.setString(2, buku.getPengarang());
            pstmt.setString(3, buku.getPenerbit());
            pstmt.setInt(4, buku.getTahun());
            pstmt.executeUpdate();
        }
    }

    public void update(Buku buku) throws SQLException {
        String sql = "UPDATE buku SET judul=?, pengarang=?, penerbit=?, tahun=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buku.getJudul());
            pstmt.setString(2, buku.getPengarang());
            pstmt.setString(3, buku.getPenerbit());
            pstmt.setInt(4, buku.getTahun());
            pstmt.setInt(5, buku.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM buku WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

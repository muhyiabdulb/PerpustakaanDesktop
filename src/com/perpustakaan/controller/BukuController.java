package com.perpustakaan.controller;

import com.perpustakaan.dao.BukuDAO;
import com.perpustakaan.model.Buku;
import java.sql.SQLException;
import java.util.List;

public class BukuController {
    private BukuDAO dao;

    public BukuController() {
        this.dao = new BukuDAO();
    }

    public List<Buku> getAllBuku() throws SQLException {
        return dao.getAll();
    }

    public void addBuku(String judul, String pengarang, String penerbit, int tahun) throws SQLException {
        Buku buku = new Buku(0, judul, pengarang, penerbit, tahun);
        dao.insert(buku);
    }

    public void updateBuku(int id, String judul, String pengarang, String penerbit, int tahun) throws SQLException {
        Buku buku = new Buku(id, judul, pengarang, penerbit, tahun);
        dao.update(buku);
    }

    public void deleteBuku(int id) throws SQLException {
        dao.delete(id);
    }
}

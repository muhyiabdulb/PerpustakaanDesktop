package com.perpustakaan.controller;

import com.perpustakaan.dao.AnggotaDAO;
import com.perpustakaan.model.Anggota;
import java.sql.SQLException;
import java.util.List;

public class AnggotaController {
    private AnggotaDAO dao;

    public AnggotaController() {
        this.dao = new AnggotaDAO();
    }

    public List<Anggota> getAllAnggota() throws SQLException {
        return dao.getAll();
    }

    public void addAnggota(String nama, String alamat, String telepon) throws SQLException {
        Anggota anggota = new Anggota(0, nama, alamat, telepon);
        dao.insert(anggota);
    }

    public void updateAnggota(int id, String nama, String alamat, String telepon) throws SQLException {
        Anggota anggota = new Anggota(id, nama, alamat, telepon);
        dao.update(anggota);
    }

    public void deleteAnggota(int id) throws SQLException {
        dao.delete(id);
    }
}

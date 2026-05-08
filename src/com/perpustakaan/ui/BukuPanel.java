package com.perpustakaan.ui;

import com.perpustakaan.dao.BukuDAO;
import com.perpustakaan.model.Buku;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BukuPanel extends JPanel {
    private BukuDAO dao = new BukuDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtJudul, txtPengarang, txtPenerbit, txtTahun;
    private int selectedId = -1;

    public BukuPanel() {
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Judul:"));
        txtJudul = new JTextField(); formPanel.add(txtJudul);
        formPanel.add(new JLabel("Pengarang:"));
        txtPengarang = new JTextField(); formPanel.add(txtPengarang);
        formPanel.add(new JLabel("Penerbit:"));
        txtPenerbit = new JTextField(); formPanel.add(txtPenerbit);
        formPanel.add(new JLabel("Tahun:"));
        txtTahun = new JTextField(); formPanel.add(txtTahun);

        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");
        JButton btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Judul", "Pengarang", "Penerbit", "Tahun"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> saveBuku());
        btnUpdate.addActionListener(e -> updateBuku());
        btnDelete.addActionListener(e -> deleteBuku());
        btnClear.addActionListener(e -> clearForm());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) tableModel.getValueAt(row, 0);
                txtJudul.setText((String) tableModel.getValueAt(row, 1));
                txtPengarang.setText((String) tableModel.getValueAt(row, 2));
                txtPenerbit.setText((String) tableModel.getValueAt(row, 3));
                txtTahun.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Buku> list = dao.getAll();
            for (Buku b : list) {
                tableModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPengarang(), b.getPenerbit(), b.getTahun()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void saveBuku() {
        try {
            Buku b = new Buku(0, txtJudul.getText(), txtPengarang.getText(), txtPenerbit.getText(), Integer.parseInt(txtTahun.getText()));
            dao.insert(b);
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }
    }

    private void updateBuku() {
        if (selectedId == -1) return;
        try {
            Buku b = new Buku(selectedId, txtJudul.getText(), txtPengarang.getText(), txtPenerbit.getText(), Integer.parseInt(txtTahun.getText()));
            dao.update(b);
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }

    private void deleteBuku() {
        if (selectedId == -1) return;
        try {
            dao.delete(selectedId);
            loadData();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtJudul.setText("");
        txtPengarang.setText("");
        txtPenerbit.setText("");
        txtTahun.setText("");
        selectedId = -1;
        table.clearSelection();
    }
}

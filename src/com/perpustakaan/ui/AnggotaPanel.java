package com.perpustakaan.ui;

import com.perpustakaan.dao.AnggotaDAO;
import com.perpustakaan.model.Anggota;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AnggotaPanel extends JPanel {
    private AnggotaDAO dao = new AnggotaDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNama, txtTelepon;
    private JTextArea txtAlamat;
    private int selectedId = -1;

    public AnggotaPanel() {
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; txtNama = new JTextField(20); formPanel.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; txtAlamat = new JTextArea(3, 20); 
        formPanel.add(new JScrollPane(txtAlamat), gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Telepon:"), gbc);
        gbc.gridx = 1; txtTelepon = new JTextField(20); formPanel.add(txtTelepon, gbc);

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
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Alamat", "Telepon"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> saveAnggota());
        btnUpdate.addActionListener(e -> updateAnggota());
        btnDelete.addActionListener(e -> deleteAnggota());
        btnClear.addActionListener(e -> clearForm());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) tableModel.getValueAt(row, 0);
                txtNama.setText((String) tableModel.getValueAt(row, 1));
                txtAlamat.setText((String) tableModel.getValueAt(row, 2));
                txtTelepon.setText((String) tableModel.getValueAt(row, 3));
            }
        });

        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Anggota> list = dao.getAll();
            for (Anggota a : list) {
                tableModel.addRow(new Object[]{a.getId(), a.getNama(), a.getAlamat(), a.getTelepon()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void saveAnggota() {
        try {
            Anggota a = new Anggota(0, txtNama.getText(), txtAlamat.getText(), txtTelepon.getText());
            dao.insert(a);
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
        }
    }

    private void updateAnggota() {
        if (selectedId == -1) return;
        try {
            Anggota a = new Anggota(selectedId, txtNama.getText(), txtAlamat.getText(), txtTelepon.getText());
            dao.update(a);
            loadData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }

    private void deleteAnggota() {
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
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        selectedId = -1;
        table.clearSelection();
    }
}

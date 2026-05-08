package com.perpustakaan.ui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Sistem Informasi Perpustakaan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manajemen Buku", new BukuPanel());
        tabbedPane.addTab("Manajemen Anggota", new AnggotaPanel());

        add(tabbedPane);
    }
}

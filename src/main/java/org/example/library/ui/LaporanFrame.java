package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.model.Buku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LaporanFrame extends JFrame {

    public LaporanFrame() {
        setTitle("Laporan Peminjaman");
        setSize(600, 300);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Judul","Peminjam","Tanggal Kembali"}, 0);

        JTable table = new JTable(model);

        for (Buku b : AppConfig.daftarBuku) {
            if (b.dipinjam) {
                model.addRow(new Object[]{
                        b.judul, b.peminjam, b.kembali
                });
            }
        }

        add(new JScrollPane(table));
    }
}
package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.model.Buku;
import org.example.library.util.DataManager;
import org.example.library.util.UIUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Comparator;

public class BukuFrame extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public BukuFrame() {
        setTitle("Data Buku");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(
                new String[]{"ID","Judul","Penulis","Tahun","Status"}, 0);

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        refreshTable();

        JPanel searchPanel = new JPanel(new BorderLayout(10,10));
        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField tfSearch = new JTextField();
        searchPanel.add(new JLabel("Cari Buku:"), BorderLayout.WEST);
        searchPanel.add(tfSearch, BorderLayout.CENTER);

        tfSearch.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void search() {
                        String text = tfSearch.getText();
                        sorter.setRowFilter(text.isEmpty() ? null :
                                RowFilter.regexFilter("(?i)" + text));
                    }

                    public void insertUpdate(javax.swing.event.DocumentEvent e){ search(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e){ search(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e){ search(); }
                });

        JButton tambah = UIUtil.createButton("Tambah");
        JButton edit = UIUtil.createButton("Edit");
        JButton hapus = UIUtil.createButton("Hapus");
        JButton pinjam = UIUtil.createButton("Pinjam");
        JButton kembali = UIUtil.createButton("Kembalikan");
        JButton back = UIUtil.createButton("Kembali");

        tambah.setEnabled(AppConfig.isAdmin);
        edit.setEnabled(AppConfig.isAdmin);
        hapus.setEnabled(AppConfig.isAdmin);
        pinjam.setEnabled(!AppConfig.isAdmin);
        kembali.setEnabled(!AppConfig.isAdmin);

        tambah.addActionListener(e ->
                new FormBukuFrame(this, null).setVisible(true));

        edit.addActionListener(e -> editBuku());
        hapus.addActionListener(e -> hapusBuku());
        pinjam.addActionListener(e -> pinjamBuku());
        kembali.addActionListener(e -> kembalikanBuku());

        back.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(tambah);
        bottom.add(edit);
        bottom.add(hapus);
        bottom.add(pinjam);
        bottom.add(kembali);
        bottom.add(back);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    void refreshTable() {
        model.setRowCount(0);
        AppConfig.daftarBuku.sort(Comparator.comparing(b -> b.judul));

        for (Buku b : AppConfig.daftarBuku) {
            model.addRow(new Object[]{
                    b.id, b.judul, b.penulis, b.tahun,
                    b.dipinjam ? "Dipinjam" : "Tersedia"
            });
        }
        DataManager.saveData();
    }

    private int selectedIndex() {
        int v = table.getSelectedRow();
        return (v < 0) ? -1 : table.convertRowIndexToModel(v);
    }

    private void editBuku() {
        int i = selectedIndex();
        if (i >= 0)
            new FormBukuFrame(this, AppConfig.daftarBuku.get(i)).setVisible(true);
    }

    private void hapusBuku() {
        int i = selectedIndex();
        if (i >= 0) {
            AppConfig.daftarBuku.remove(i);
            refreshTable();
        }
    }

    private void pinjamBuku() {
        int i = selectedIndex();
        if (i < 0) return;

        Buku b = AppConfig.daftarBuku.get(i);
        if (b.dipinjam) return;

        String tgl = JOptionPane.showInputDialog(
                "Pinjam sampai (YYYY-MM-DD)");
        b.dipinjam = true;
        b.peminjam = "Siswa";
        b.kembali = LocalDate.parse(tgl);

        refreshTable();
    }

    private void kembalikanBuku() {
        int i = selectedIndex();
        if (i < 0) return;

        Buku b = AppConfig.daftarBuku.get(i);
        b.dipinjam = false;
        b.peminjam = "-";
        b.kembali = null;

        refreshTable();
    }
}
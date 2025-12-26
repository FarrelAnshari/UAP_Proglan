package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.model.Buku;
import org.example.library.util.DataManager;
import org.example.library.util.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Judul", "Penulis", "Tahun", "Status"}, 0);

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        refreshTable();

        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField tfSearch = new JTextField();
        searchPanel.add(new JLabel("Cari Buku:"), BorderLayout.WEST);
        searchPanel.add(tfSearch, BorderLayout.CENTER);

        tfSearch.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void search() {
                        String text = tfSearch.getText();
                        if (text.trim().isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        search();
                    }

                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        search();
                    }

                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        search();
                    }
                });

        // ===== BUTTON =====
        JButton tambah = UIUtil.createButton("Tambah");
        JButton edit = UIUtil.createButton("Edit");
        JButton hapus = UIUtil.createButton("Hapus");
        JButton pinjam = UIUtil.createButton("Pinjam");
        JButton kembalikan = UIUtil.createButton("Kembalikan");
        JButton back = UIUtil.createButton("Kembali");

        // ===== ROLE ACCESS =====
        tambah.setEnabled(AppConfig.isAdmin);
        edit.setEnabled(AppConfig.isAdmin);
        hapus.setEnabled(AppConfig.isAdmin);

        pinjam.setEnabled(!AppConfig.isAdmin);
        kembalikan.setEnabled(!AppConfig.isAdmin);

        // ===== ACTION =====
        tambah.addActionListener(e ->
                new FormBukuFrame(this, null).setVisible(true));

        edit.addActionListener(e -> editBuku());
        hapus.addActionListener(e -> hapusBuku());
        pinjam.addActionListener(e -> pinjamBuku());
        kembalikan.addActionListener(e -> kembalikanBuku());

        back.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(tambah);
        bottomPanel.add(edit);
        bottomPanel.add(hapus);
        bottomPanel.add(pinjam);
        bottomPanel.add(kembalikan);
        bottomPanel.add(back);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= METHOD =================

    public void refreshTable() {
        model.setRowCount(0);
        AppConfig.daftarBuku.sort(
                Comparator.comparing(b -> b.judul));

        for (Buku b : AppConfig.daftarBuku) {
            model.addRow(new Object[]{
                    b.id,
                    b.judul,
                    b.penulis,
                    b.tahun,
                    b.dipinjam ? "Dipinjam" : "Tersedia"
            });
        }
        DataManager.saveData();
    }

    private int getSelectedIndex() {
        int viewIndex = table.getSelectedRow();
        if (viewIndex < 0) return -1;
        return table.convertRowIndexToModel(viewIndex);
    }

    private void editBuku() {
        int index = getSelectedIndex();
        if (index >= 0) {
            new FormBukuFrame(
                    this,
                    AppConfig.daftarBuku.get(index)
            ).setVisible(true);
        }
    }

    private void hapusBuku() {
        int index = getSelectedIndex();
        if (index >= 0) {
            AppConfig.daftarBuku.remove(index);
            refreshTable();
        }
    }

    private void pinjamBuku() {
        int index = getSelectedIndex();
        if (index < 0) return;

        Buku b = AppConfig.daftarBuku.get(index);
        if (b.dipinjam) return;

        String tgl = JOptionPane.showInputDialog(
                this,
                "Pinjam sampai (YYYY-MM-DD)");

        if (tgl == null || tgl.isEmpty()) return;

        b.dipinjam = true;
        b.peminjam = "Siswa";
        b.kembali = LocalDate.parse(tgl);

        refreshTable();
    }

    private void kembalikanBuku() {
        int index = getSelectedIndex();
        if (index < 0) return;

        Buku b = AppConfig.daftarBuku.get(index);
        b.dipinjam = false;
        b.peminjam = "-";
        b.kembali = null;

        refreshTable();
    }
}

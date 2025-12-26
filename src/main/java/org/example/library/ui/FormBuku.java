package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.model.Buku;
import org.example.library.util.UIUtil;

import javax.swing.*;
import java.awt.*;

public class FormBukuFrame extends JFrame {

    private JTextField id = new JTextField();
    private JTextField judul = new JTextField();
    private JTextField penulis = new JTextField();
    private JTextField tahun = new JTextField();

    private BukuFrame parent;
    private Buku edit;

    public FormBukuFrame(BukuFrame parent, Buku edit) {
        this.parent = parent;
        this.edit = edit;

        setTitle("Form Buku");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2,10,10));

        ((JComponent)getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("ID"));
        add(id);
        add(new JLabel("Judul"));
        add(judul);
        add(new JLabel("Penulis"));
        add(penulis);
        add(new JLabel("Tahun"));
        add(tahun);

        JButton simpan = UIUtil.createButton("Simpan");
        add(new JLabel());
        add(simpan);

        if (edit != null) {
            id.setText(edit.id);
            judul.setText(edit.judul);
            penulis.setText(edit.penulis);
            tahun.setText(String.valueOf(edit.tahun));
        } else {
            id.setText("B" + (AppConfig.daftarBuku.size() + 1));
        }

        simpan.addActionListener(e -> simpanBuku());
    }

    private void simpanBuku() {
        if (edit == null) {
            AppConfig.daftarBuku.add(new Buku(
                    id.getText(),
                    judul.getText(),
                    penulis.getText(),
                    Integer.parseInt(tahun.getText())
            ));
        } else {
            edit.id = id.getText();
            edit.judul = judul.getText();
            edit.penulis = penulis.getText();
            edit.tahun = Integer.parseInt(tahun.getText());
        }

        parent.refreshTable();
        dispose();
    }
}
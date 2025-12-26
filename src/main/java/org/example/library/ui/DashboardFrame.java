package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.util.DataManager;
import org.example.library.util.UIUtil;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel panel = new GradientPanel(
                AppConfig.isAdmin ? "ADMIN" : "SISWA"
        );

        panel.setLayout(new GridLayout(4, 1, 15, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(40, 80, 40, 80)
        );

        JButton data = UIUtil.createButton("📚 Data Buku");
        JButton laporan = UIUtil.createButton("📊 Laporan");
        JButton keluar = UIUtil.createButton("❌ Keluar");

        // ===== ACTION =====
        data.addActionListener(e -> {
            new BukuFrame().setVisible(true);
            dispose();
        });

        laporan.setEnabled(AppConfig.isAdmin);
        laporan.addActionListener(e -> {
            new LaporanFrame().setVisible(true);
            dispose();
        });

        keluar.addActionListener(e -> {
            DataManager.saveData();
            System.exit(0);
        });

        panel.add(new JLabel());
        panel.add(data);
        panel.add(laporan);
        panel.add(keluar);

        add(panel);
    }
}

package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.util.UIUtil;

import javax.swing.*;
import java.awt.*;

public class LoginRoleFrame extends JFrame {

    public LoginRoleFrame() {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel panel = new GradientPanel("PILIH LOGIN");
        panel.setLayout(new GridLayout(3,1,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(50,60,50,60));

        JButton admin = UIUtil.createButton("Admin");
        JButton siswa = UIUtil.createButton("Siswa");

        admin.addActionListener(e -> {
            AppConfig.isAdmin = true;
            new LoginPasswordFrame().setVisible(true);
            dispose();
        });

        siswa.addActionListener(e -> {
            AppConfig.isAdmin = false;
            new LoginPasswordFrame().setVisible(true);
            dispose();
        });

        panel.add(new JLabel());
        panel.add(admin);
        panel.add(siswa);
        add(panel);
    }
}
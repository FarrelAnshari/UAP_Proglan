package org.example.library.ui;

import org.example.library.config.AppConfig;
import org.example.library.util.UIUtil;

import javax.swing.*;
import java.awt.*;

public class LoginPasswordFrame extends JFrame {

    public LoginPasswordFrame() {
        setTitle("Password");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel panel = new GradientPanel("PASSWORD");
        panel.setLayout(new GridLayout(3,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        JPasswordField pass = new JPasswordField();
        JButton masuk = UIUtil.createButton("Masuk");

        masuk.addActionListener(e -> {
            String input = new String(pass.getPassword());
            if ((AppConfig.isAdmin && input.equals(AppConfig.PASS_ADMIN)) ||
                    (!AppConfig.isAdmin && input.equals(AppConfig.PASS_SISWA))) {
                new DashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password salah");
            }
        });

        panel.add(new JLabel("Masukkan Password"));
        panel.add(pass);
        panel.add(masuk);
        add(panel);
    }
}
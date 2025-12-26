package org.example.library.ui;

import javax.swing.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        setSize(400, 250);
        setLocationRelativeTo(null);
        add(new GradientPanel("SISTEM PERPUSTAKAAN"));
        setVisible(true);

        new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            dispose();
            new LoginRoleFrame().setVisible(true);
        }).start();
    }
}
package org.example.library;

import org.example.library.ui.SplashScreen;
import org.example.library.util.DataManager;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DataManager.loadData();
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}

package org.example.library.util;

import javax.swing.*;
import java.awt.*;

public class UIUtil {
    public static JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return b;
    }
}

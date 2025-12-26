package org.example.library.ui;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private final String text;

    public GradientPanel(String text) {
        this.text = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(new GradientPaint(
                0, 0, new Color(33,150,243),
                0, getHeight(), new Color(156,39,176)));

        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = fm.getAscent() + 15;

        g2.drawString(text, x, y);
    }
}
package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener, ActionListener {
    Timer t = new Timer(5, this);
    int x = 0, y = 0, velX = 0, velY = 0, speed = 5;

    public Game() {
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 100, 60);
    }

    public void actionPerformed(ActionEvent e) {
        x += velX;
        y += velY;
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ESCAPE) System.exit(0);

        velX = 0;
        velY = 0;
        if (code == KeyEvent.VK_A) velX -= speed;
        if (code == KeyEvent.VK_D) velX += speed;
        if (code == KeyEvent.VK_W) velY -= speed;
        if (code == KeyEvent.VK_S) velY += speed;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {
        velX = 0;
        velY = 0;
    }
}

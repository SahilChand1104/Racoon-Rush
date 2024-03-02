package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {
    private EnumMap<Move, Boolean> movesPressed = new EnumMap<>(Move.class);
    private Point vectorSum = new Point(0, 0);

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Move move = Move.fromKeyCode(e.getKeyCode());
        if (move != null) {
            movesPressed.put(move, true);
            vectorSum.translate(move.getDirection().x, move.getDirection().y);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Move move = Move.fromKeyCode(e.getKeyCode());
        if (move != null) {
            movesPressed.put(move, false);
            vectorSum.translate(-move.getDirection().x, -move.getDirection().y);
        }
    }

    public Point getVectorSum() {
        return new Point(vectorSum);
    }
}

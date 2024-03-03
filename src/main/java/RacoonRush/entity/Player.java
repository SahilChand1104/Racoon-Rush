package RacoonRush.entity;

import RacoonRush.game.GamePanel;
import RacoonRush.game.KeyHandler;
import RacoonRush.game.Move;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        if (keyH.get(Move.UP)) {
            y -= speed;
        } else if (keyH.get(Move.DOWN)) {
            y += speed;
        } else if (keyH.get(Move.LEFT)) {
            x -= speed;
        } else if (keyH.get(Move.RIGHT)) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}

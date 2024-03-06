package RacoonRush.entity;

import RacoonRush.game.GamePanel;
import RacoonRush.game.KeyHandler;
import RacoonRush.game.Move;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void getPlayerImage() {
        try {
            down = ImageIO.read(getClass().getResourceAsStream("/entity/racoon_v2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        BufferedImage image;

        // Currently only down image is available
        image = down;

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}

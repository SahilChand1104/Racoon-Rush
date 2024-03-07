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

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Centered in the middle of the map
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 8 - gp.tileSize / 2;
        worldY = gp.tileSize * 6 - gp.tileSize / 2;
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
            worldY -= speed;
        } else if (keyH.get(Move.DOWN)) {
            worldY += speed;
        } else if (keyH.get(Move.LEFT)) {
            worldX -= speed;
        } else if (keyH.get(Move.RIGHT)) {
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image;

        // Currently only down image is available
        image = down;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

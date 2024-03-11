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

    private boolean isMoving;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        images = new BufferedImage[4][2];

        // Centered in the middle of the map
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        hitbox = new Rectangle(gp.tileSize / 6, gp.tileSize / 3, gp.tileSize * 2 / 3, gp.tileSize * 2 / 3);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 8 - gp.tileSize / 2;
        worldY = gp.tileSize * 6 - gp.tileSize / 2;
        speed = 4;

        dir = Move.DOWN;
    }

    public void getPlayerImage() {
        try {
            images[0][0] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_down_0.png")); // down_0
            images[0][1] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_down_1.png")); // down_1
            images[1][0] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_up_0.png")); // up_0
            images[1][1] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_up_1.png")); // up_1
            images[2][0] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_left_0.png")); // left_0
            images[2][1] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_left_1.png")); // left_1
            images[3][0] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_right_0.png")); // right_0
            images[3][1] = ImageIO.read(getClass().getResourceAsStream("/entity/player/player_right_1.png")); // right_1

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int leftColumn() {
        return (worldX + hitbox.x) / gp.tileSize;
    }

    public int rightColumn() {
        return (worldX + hitbox.x + hitbox.width) / gp.tileSize;
    }

    public int topRow() {
        return (worldY + hitbox.y) / gp.tileSize;
    }

    public int bottomRow() {
        return (worldY + hitbox.y + hitbox.height) / gp.tileSize;
    }

    public void update() {
        if (!keyH.get(Move.UP) && !keyH.get(Move.DOWN) && !keyH.get(Move.LEFT) && !keyH.get(Move.RIGHT)) {
            isMoving = false;
            //dir = Move.DOWN;
            return;
        }

        if (keyH.get(Move.UP) && gp.isNotColliding(this, Move.UP)) {
            isMoving = true;
            dir = Move.UP;
            worldY -= speed;
        } else if (keyH.get(Move.DOWN) && gp.isNotColliding(this, Move.DOWN)) {
            isMoving = true;
            dir = Move.DOWN;
            worldY += speed;
        } else if (keyH.get(Move.LEFT) && gp.isNotColliding(this, Move.LEFT)) {
            isMoving = true;
            dir = Move.LEFT;
            worldX -= speed;
        } else if (keyH.get(Move.RIGHT) && gp.isNotColliding(this, Move.RIGHT)) {
            isMoving = true;
            dir = Move.RIGHT;
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2, int animationFrame) {
        BufferedImage image;

        switch (dir) {
            case UP:
                if (isMoving) {
                    image = images[1][animationFrame];
                } else {
                    image = images[1][0];
                }
                //image = images[1][animationFrame];
                break;
            case DOWN:
                if (isMoving) {
                    image = images[0][animationFrame];
                } else {
                    image = images[0][0];
                }
                //image = images[0][animationFrame];
                break;
            case LEFT:
                if (isMoving) {
                    image = images[2][animationFrame];
                } else {
                    image = images[2][0];
                }
                //image = images[2][animationFrame];
                break;
            case RIGHT:
                if (isMoving) {
                    image = images[3][animationFrame];
                } else {
                    image = images[3][0];
                }
                //image = images[3][animationFrame];
                break;
            default:
                image = images[0][0];
                break;
        }

        //System.out.println("Player is facing " + dir.toString());

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

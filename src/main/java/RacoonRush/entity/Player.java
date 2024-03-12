package RacoonRush.entity;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.KeyHandler;
import RacoonRush.game.Move;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private final GamePanel gp;
    private final Config config;

    public final int screenX, screenY;

    private boolean isMoving;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.config = gp.getConfig();

        images = new BufferedImage[4][2];

        // Centered in the middle of the map
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;

        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);

        setDefaultValues();
        getPlayerImages();
    }

    public void setDefaultValues() {
        worldX = config.tileSize() * (config.maxScreenCol() / 2) - config.tileSize() / 2;
        worldY = config.tileSize() * (config.maxScreenRow() / 2) - config.tileSize() / 2;
        speed = 4;

        dir = Move.DOWN;
    }

    public void getPlayerImages() {
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
        return (worldX + hitbox.x) / config.tileSize();
    }

    public int rightColumn() {
        return (worldX + hitbox.x + hitbox.width) / config.tileSize();
    }

    public int topRow() {
        return (worldY + hitbox.y) / config.tileSize();
    }

    public int bottomRow() {
        return (worldY + hitbox.y + hitbox.height) / config.tileSize();
    }

    public void update() {
        KeyHandler keyHandler = gp.getKeyHandler();
        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            isMoving = false;
            //dir = Move.DOWN;
            return;
        }

        // If the player is colliding with a wall, move the player back
        if (!gp.isNotColliding(this, dir)) {
            switch (dir) {
                case UP:
                    worldY += speed;
                    break;
                case DOWN:
                    worldY -= speed;
                    break;
                case LEFT:
                    worldX += speed;
                    break;
                case RIGHT:
                    worldX -= speed;
                    break;
            }
        }

        if (keyHandler.get(Move.UP) && gp.isNotColliding(this, Move.UP)) {
            isMoving = true;
            dir = Move.UP;
            worldY -= speed;
        } else if (keyHandler.get(Move.DOWN) && gp.isNotColliding(this, Move.DOWN)) {
            isMoving = true;
            dir = Move.DOWN;
            worldY += speed;
        } else if (keyHandler.get(Move.LEFT) && gp.isNotColliding(this, Move.LEFT)) {
            isMoving = true;
            dir = Move.LEFT;
            worldX -= speed;
        } else if (keyHandler.get(Move.RIGHT) && gp.isNotColliding(this, Move.RIGHT)) {
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

        g2.drawImage(image, screenX, screenY, config.tileSize(), config.tileSize(), null);
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public int getScreenX() {
        return screenX;
    }
    public int getScreenY() {
        return screenY;
    }
}

package RaccoonRush.util;

import java.awt.Rectangle;

/**
 * This record represents the configuration for the game.
 * It is a compact form of class that holds immutable data.
 * The fields include originalTileSize, scale, maxScreenCol, maxScreenRow, maxWorldCol, maxWorldRow, and FPS.
 * The record automatically generates public final fields and accessors for the data, as well as appropriate implementations of equals(), hashCode(), and toString().
 * @param originalTileSize The original size of the tile.
 * @param scale The scale factor for the game.
 * @param maxScreenCol The maximum number of columns on the screen.
 * @param maxScreenRow The maximum number of rows on the screen.
 * @param maxWorldCol The maximum number of columns in the world.
 * @param maxWorldRow The maximum number of rows in the world.
 * @param FPS The frames per second for the game.
 * @param animationFPS The frames per second for animations.
 * @param playerSpeed The speed of the player.
 * @param enemySpeed The speed of enemies.
 */
public record Config(int originalTileSize, int scale, int maxScreenCol, int maxScreenRow, int maxWorldCol, int maxWorldRow, int FPS, int animationFPS, int playerSpeed, int enemySpeed, int enemyDamage, Move entityDirection) {

    /**
     * This accessor returns the tile size for the game.
     * It is calculated by multiplying originalTileSize by scale.
     * @return The tile size.
     */
    public int tileSize() {
        return originalTileSize * scale;
    }

    /**
     * This accessor returns the screen width for the game.
     * It is calculated by multiplying tileSize() by maxScreenCol.
     * @return The screen width.
     */
    public int screenWidth() {
        return tileSize() * maxScreenCol;
    }

    /**
     * This accessor returns the screen height for the game.
     * It is calculated by multiplying tileSize() by maxScreenRow.
     * @return The screen height.
     */
    public int screenHeight() {
        return tileSize() * maxScreenRow;
    }

    /**
     * This accessor returns the world width for the game.
     * It is calculated by multiplying tileSize() by maxWorldCol.
     * @return The world width.
     */
    public int worldWidth() {
        return tileSize() * maxWorldCol;
    }

    /**
     * This accessor returns the world height for the game.
     * It is calculated by multiplying tileSize() by maxWorldRow.
     * @return The world height.
     */
    public int worldHeight() {
        return tileSize() * maxWorldRow;
    }

    /**
     * This accessor returns the hitbox of entities for the game.
     * @return The hitbox of an entity.
     */
    public Rectangle entityHitbox() {
        return new Rectangle(tileSize() / 6, tileSize() / 3, tileSize() * 2 / 3, tileSize() * 2 / 3);
    }

    /**
     * This accessor returns the player's default world x position for the game.
     * @return The player's default world x position.
     */
    public int playerDefaultWorldX() {
        return tileSize() * (maxWorldCol - 2);
    }

    /**
     * This accessor returns the player's default world y position for the game.
     * @return The player's default world y position.
     */
    public int playerDefaultWorldY() {
        return tileSize() * (maxWorldRow - 2);
    }

    /**
     * This accessor returns the player's invincibility duration for the game.
     * It is calculated by multiplying FPS by 2.
     * @return The player's invincibility duration.
     */
    public int playerInvincibilityDuration() {
        return FPS() * 2;
    }

    /**
     * This accessor returns the enemy's ability duration for the game.
     * It is calculated by multiplying FPS by 2.
     * @return The enemy ability duration.
     */
    public int enemyAbilityDuration() {
        return FPS() * 2;
    }

    /**
     * This accessor returns the enemy's ability cooldown duration for the game.
     * It is calculated by multiplying FPS by 10.
     * @return The enemy's ability cooldown duration.
     */
    public int enemyAbilityCooldownDuration() {
        return FPS() * 10;
    }

    /**
     * This accessor returns the max amount of pizza frames for the game.
     * It is calculated by multiplying FPS by 3.
     * @return The max amount of pizza frames.
     */
    public int maxPizzaFrames() {
        return FPS() * 3;
    }
}
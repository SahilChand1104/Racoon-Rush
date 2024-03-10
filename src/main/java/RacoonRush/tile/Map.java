package RacoonRush.tile;

import RacoonRush.game.GamePanel;

public class Map {

    /**
     *  The GamePanel object used by the MapManager, MapLoader and BGManager classes
     */
    private final GamePanel gp;

    /**
     *  Constructor for the Map class
     *  @param gp The GamePanel object used by the MapManager, MapLoader and BGManager classes
     */
    public Map(GamePanel gp) {
        this.gp = gp;
    }

    /**
     *  Returns the screen coordinate of a tile
     *  @param index The index of the tile
     *  @param world The world coordinate of the tile
     *  @param screen The screen coordinate of the tile
     *  @return The screen coordinate of the tile
     */
    public int getScreenCoordinate(int index, int world, int screen) {
        return index * gp.tileSize - world + screen;
    }


    // Getters for the GamePanel object - Simplify by making this class extend GamePanel??
    public int getPlayerWorldX() {
        return gp.getPlayerWorldX();
    }

    public int getPlayerWorldY() {
        return gp.getPlayerWorldY();
    }

    public int getPlayerScreenX() {
        return gp.getPlayerScreenX();
    }

    public int getPlayerScreenY() {
        return gp.getPlayerScreenY();
    }

    public int getScreenWidth() {
        return gp.getScreenWidth();
    }

    public int getScreenHeight() {
        return gp.getScreenHeight();
    }

    public int getTileSize() {
        return gp.getTileSize();
    }

    public int getMaxWorldRow() {
        return gp.getMaxWorldRow();
    }
    public int getMaxWorldCol() {
        return gp.getMaxWorldCol();
    }
}

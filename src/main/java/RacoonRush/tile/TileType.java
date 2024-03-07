package RacoonRush.tile;

public enum TileType {
    FLOOR(0), WALL(1);
    private final int value;
    TileType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public TileType getTileType(int value) {
        for (TileType tileType : TileType.values()) {
            if (tileType.getValue() == value) {
                return tileType;
            }
        }
        return null;
    }
}

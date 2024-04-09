package RaccoonRush.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import RaccoonRush.map.tile.Item;
import RaccoonRush.map.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SoundTest {
    private SoundManager soundManager;

    @BeforeEach
    public void setup() {
        soundManager = new SoundManager();
        assertNotNull(soundManager);
    }

    @Test
    public void testSoundLoading() {
        // Test individual sound loading
        for (int i = 0; i < 6; i++) {
            soundManager.playSound(i);
        }
    }

    @Test
    public void testSoundLoop() {
        soundManager.playSoundLoop(0);
    }

    @Test
    public void testSoundStop() {
        for (int i = 0; i < 6; i++) {
            soundManager.stopSound(i);
        }
    }

    @Test
    public void testPlaySpecificSound() {
        soundManager.playSound(0);
    }

    @Test
    public void collectDonut() {
        soundManager.collectItemSound(new Item(null, TileType.DONUT));
    }

    @Test
    public void collectPizza() {
        soundManager.collectItemSound(new Item(null, TileType.PIZZA));
    }

    @Test
    public void collectLeftover() {
        soundManager.collectItemSound(new Item(null, TileType.LEFTOVER));
    }
}

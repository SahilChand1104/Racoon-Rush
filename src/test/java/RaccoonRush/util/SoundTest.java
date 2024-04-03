package RaccoonRush.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SoundTest {
    @Test
    public void testSoundLoading() {
        SoundManager soundManager = new SoundManager();
        assertNotNull(soundManager);
        // Test individual sound loading
        for (int i = 0; i < 6; i++) {
            soundManager.playSound(i);
        }
    }

    @Test
    public void testSoundManagerCreation() {
        SoundManager soundManager = new SoundManager();
        assertNotNull(soundManager);
    }

    @Test
    public void testSoundLoop() {
        SoundManager soundManager = new SoundManager();
        assertNotNull(soundManager);
        soundManager.playSoundLoop(0);
    }

    @Test
    public void testSoundStop() {
        SoundManager soundManager = new SoundManager();
        assertNotNull(soundManager);
        for (int i = 0; i < 6; i++) {
            soundManager.stopSound(i);
        }
    }

    @Test
    public void testPlaySpecificSound() {
        SoundManager soundManager = new SoundManager();
        assertNotNull(soundManager);
        soundManager.playSound(0);
    }
}

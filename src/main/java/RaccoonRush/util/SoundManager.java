package RaccoonRush.util;

import RaccoonRush.map.tile.Item;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager class is used to load and play sound effects and background music in the game.
 * This class uses the javax.sound.sampled package to play the sound files.
 * The following sound files are used in the game and can be called using their respective indexes:
 * 0 - Music.wav (Background music)
 * 1 - Eating a Donut.wav (Sound effect for collecting a donut)
 * 2 - Eating Leftovers.wav (Sound effect for collecting leftovers)
 * 3 - Raccoon enemy.wav (Sound effect for raccoon enemy)
 * 4 - Footsteps.wav (Sound effect for player footsteps)
 * 5 - Splashing.wav (Sound effect for water splashing)
 */
public class SoundManager {
    private final Map<Integer, Clip> sounds;
    private final String[] soundFiles = {
            "/Sounds/Music.wav",
            "/Sounds/Eating a Donut.wav",
            "/Sounds/Eating Leftovers.wav",
            "/Sounds/Raccoon enemy.wav",
            "/Sounds/Footsteps.wav",
            "/Sounds/Splashing.wav"
    };

    /**
     * Constructor for the SoundManager class.
     * Initializes the map to store loaded sound clips.
     */
    public SoundManager() {
        sounds = new HashMap<>();
    }

    /**
     * Load a sound clip from a sound file.
     * @param i the index of the sound file to load
     * @return the loaded Clip object
     * @throws IOException                  if an I/O error occurs
     * @throws UnsupportedAudioFileException if the audio file is not supported
     * @throws LineUnavailableException     if a line cannot be opened
     */
    private Clip loadSound(int i) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        URL soundFileURL = getClass().getResource(soundFiles[i]);
        if (soundFileURL == null) {
            throw new IOException("Sound file not found: " + soundFiles[i]);
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileURL);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    /**
     * Get the clip for a sound file.
     * If the clip is not already loaded, it is loaded and cached.
     * @param i the index of the sound file
     * @return the Clip object for the specified sound file
     */
    private synchronized Clip getClip(int i) {
        return sounds.computeIfAbsent(i, idx -> {
            try {
                return loadSound(idx);
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Method to play sound
     * @param i the index of the sound file
     */
    public void playSound(int i) {
        Clip clip = getClip(i);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Method to play sound on loop indefinitely, used for background music
     * @param i the index of the sound file to loop indefinitely
     */
    public void playSoundLoop(int i) {
        Clip clip = getClip(i);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Method to stop sound
     * @param i the index of the sound file to stop
     */
    public void stopSound(int i) {
        Clip clip = getClip(i);
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Method to play sound effect based on the item that was collected
     * @param item the item that was collected
     */
    public void collectItemSound(Item item) {
        switch (item.getType()) {
            case DONUT, PIZZA -> playSound(1);
            case LEFTOVER -> playSound(2);
        }
    }
}

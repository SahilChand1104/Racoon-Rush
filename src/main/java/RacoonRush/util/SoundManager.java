package RacoonRush.util;

import RacoonRush.map.tile.Item;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;


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
    private final ArrayList<Clip> sounds;

    /**
     * Constructor for the Sound class. It loads all sounds into the clips ArrayList.
     */
    public SoundManager() {
        sounds = new ArrayList<>();
        loadAllSounds();
    }

    /**
     * This method loads all the sound files into the clips ArrayList.
     */
    private void loadAllSounds() {
        String[] soundFiles = new String[] {
            "/Sounds/Music.wav",
            "/Sounds/Eating a Donut.wav",
            "/Sounds/Eating Leftovers.wav",
            "/Sounds/Raccoon enemy.wav",
            "/Sounds/Footsteps.wav",
            "/Sounds/Splashing.wav"
        };

        for (String soundFile : soundFiles) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(soundFile));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                sounds.add(clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to play sound
     * @param i the index of the sound file
     */
    public void playSound(int i) {
        sounds.get(i).setFramePosition(0);
        sounds.get(i).start();
    }

    /**
     * Method to play sound on loop indefinitely, used for background music
     * @param i the index of the sound file to loop indefinitely
     */
    public void playSoundLooped(int i) {
        sounds.get(i).loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Method to stop sound
     * @param i the index of the sound file to stop
     */
    public void stopSound(int i) {
        sounds.get(i).stop();
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

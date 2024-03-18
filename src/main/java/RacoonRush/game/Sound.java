package RacoonRush.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


/**
 * Sound class is used to play sound effects and background music in the game.
 * It uses the javax.sound.sampled package to play the sound files.
 */
public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    /**
     * Constructor for the Sound class. It initializes the soundURL array with the sound files.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/Music.wav");
        soundURL[1] = getClass().getResource("/Sounds/Eating a Donut.wav");
        soundURL[2] = getClass().getResource("/Sounds/Eating Leftovers.wav");
        soundURL[3] = getClass().getResource("/Sounds/Raccoon enemy.wav");
        soundURL[4] = getClass().getResource("/Sounds/Footsteps.wav");
        soundURL[5] = getClass().getResource("/Sounds/Splashing.wav");

    }

    /**
     * Method to set the sound file to be played.
     * @param i The index of the sound file in the soundURL array.
     */
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception ignored){
        }
    }

    /**
     * Method to play the sound file.
     */
    public void play(){
        clip.start();
    }

    /**
     * Method to play the sound file in a loop.
     */
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Method to stop the sound file.
     */
    public void stop(){
        clip.stop();
    }

}

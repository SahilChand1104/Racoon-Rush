package RacoonRush.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/Music.wav");
        soundURL[1] = getClass().getResource("/Sounds/Eating a Donut.wav");
        soundURL[2] = getClass().getResource("/Sounds/Eating Leftovers.wav");
        soundURL[3] = getClass().getResource("/Sounds/Raccoon enemy.wav");
        soundURL[4] = getClass().getResource("/Sounds/Footsteps.wav");
        soundURL[5] = getClass().getResource("/Sounds/Splashing.wav");

    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception ignored){
        }
    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}

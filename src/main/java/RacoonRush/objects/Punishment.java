package RacoonRush.objects;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Punishment extends Object {
    public Punishment() {
        name = "Punishment";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/punishment/garbage_v1.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

    public void update(GamePanel gp) {
        gp.player.score -= 100;
        // Alert player of score gain
    }
}
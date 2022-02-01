package home.developer;

import home.developer.service.SoundPlayer;
import home.developer.service.TargetCatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class Runner {
    @Autowired
    private TargetCatcher catcher;

    @Autowired
    private SoundPlayer soundPlayer;


    public void run() {
        int color = new Color(252,1,5,255).getRGB();
        Point point = new Point();

        while (true) {
            if (catcher.isReadyForFishing(point, color)) {
                System.out.println("Start fishing");
                soundPlayer.applicationMusicStart();
                while (true) {
                    if (!catcher.isReadyForFishing(point, color)) {
                        soundPlayer.applicationMusicFinish();
                        System.out.println("Finish fishing");
                    }
                    break;
                }
                break;
            }
        }
    }


}

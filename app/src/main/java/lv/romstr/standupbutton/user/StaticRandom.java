package lv.romstr.standupbutton.user;

import java.util.Random;

/**
 * Created by Roman on 06.04.16..
 */
public class StaticRandom {

    private static Random random;

    public static Random rand() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

}

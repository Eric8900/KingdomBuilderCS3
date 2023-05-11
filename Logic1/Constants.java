package Logic1;

import java.awt.*;
import Graphics.*;

public class Constants {
    public static int PLAYERWIDTH = KingdomFrame.WIDTH - ((int) ((double) KingdomFrame.WIDTH/2.7*2)) - 5;
    public static int PLAYERHEIGHT = KingdomFrame.HEIGHT/5;
    public static int PLAYERSX = (int) ((double) KingdomFrame.WIDTH/2.7*2);
    public static class Colors{
        public static Color blue = new Color(55, 80, 140);
        public final static Color whiteFade = new Color(255, 255, 255, 150);
        public final static Color cyan = new Color(16, 98, 115);
        public static Color green = new Color(117, 217, 179, 127);
        public final static Color gold = new Color(255, 215, 0);
        public final static Color silver = new Color(192, 192, 192);
        public final static Color bronze = new Color(205, 127, 50);
        public final static Color cautionRed = new Color(255, 10, 10);
        public final static Color infoOrange = new Color(255, 200, 0);
        public static void updateColors() {
            int i = GameState.currentTheme;
            //default
            if (i == 0) {
                green = new Color(117, 217, 179, 127);
                blue = new Color(55, 80, 140);
            }
            //medieval
            if (i == 1) {
                green = new Color(201, 123, 20, 127);
                blue = new Color(184, 39, 39, 200);
            }
            //oregon trail
            if (i == 2) {
                green = new Color(255, 232, 117, 100);
                blue = new Color(2, 177, 70, 200);
            }
            //constantinople
            if (i == 3) {
                green = new Color(7, 72, 125, 100);
                blue = new Color(177, 12, 12, 200);
            }
        }
    }
}

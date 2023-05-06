package Logic1;

import java.awt.*;
import Graphics.*;

public class Constants {
    public static int PLAYERWIDTH = KingdomFrame.WIDTH - ((int) ((double) KingdomFrame.WIDTH/2.7*2)) - 5;
    public static int PLAYERHEIGHT = KingdomFrame.HEIGHT/5;
    public static int PLAYERSX = (int) ((double) KingdomFrame.WIDTH/2.7*2);
    public static final class Colors{
        public final static Color blue = new Color(55, 80, 140);
        public final static Color whiteFade = new Color(255, 255, 255, 150);
        public final static Color cyan = new Color(16, 98, 115);
        public final static Color green = new Color(117, 217, 179, 127);
        public final static Color gold = new Color(255, 215, 0);
        public final static Color silver = new Color(192, 192, 192);
        public final static Color bronze = new Color(205, 127, 50);
        public final static Color cautionRed = new Color(255, 10, 10);
        public final static Color infoOrange = new Color(255, 200, 0);
    }
}

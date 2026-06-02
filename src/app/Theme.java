package app;

import java.awt.*;

public class Theme {

    public static boolean DARK_MODE = true;

    public static Color BACKGROUND =
            new Color(3, 12, 35);

    public static Color SIDEBAR =
            new Color(9, 19, 44);

    public static Color CARD =
            new Color(14, 25, 55);

    public static Color BORDER =
            new Color(46, 58, 102);

    public static Color TEXT =
            new Color(240,240,245);

    public static Color SUBTEXT =
            new Color(150,160,190);

    public static Color PURPLE =
            new Color(148,98,255);

    public static Color BLUE =
            new Color(50,150,230);

    public static Color SUCCESS =
            new Color(52,211,153);

    public static void setDarkTheme() {

        DARK_MODE = true;

        BACKGROUND = new Color(3,12,35);
        SIDEBAR = new Color(9,19,44);
        CARD = new Color(14,25,55);
        BORDER = new Color(46,58,102);

        TEXT = new Color(240,240,245);
        SUBTEXT = new Color(150,160,190);
    }

    public static void setLightTheme() {

        DARK_MODE = false;

        BACKGROUND = new Color(240,242,247);
        SIDEBAR = new Color(225,230,240);
        CARD = Color.WHITE;
        BORDER = new Color(190,190,200);

        TEXT = new Color(20,20,25);
        SUBTEXT = new Color(90,90,100);
    }
}
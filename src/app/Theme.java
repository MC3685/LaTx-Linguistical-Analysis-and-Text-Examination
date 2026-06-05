package app;

import java.awt.*;

public class Theme {

    public static boolean DARK_MODE = true;

    // ── Core Layout ───────────────────────────────────────────────────────────
    public static Color BACKGROUND;
    public static Color BACKGROUND_GRADIENT_TOP;
    public static Color BACKGROUND_GRADIENT_BOTTOM;

    public static Color SIDEBAR;
    public static Color CARD;
    public static Color BORDER;

    // ── Typography ────────────────────────────────────────────────────────────
    public static Color TEXT;
    public static Color SUBTEXT;

    public static Color TITLE;
    public static Color SUBTITLE;

    // ── Accent System ─────────────────────────────────────────────────────────
    public static Color ACCENT_PURPLE;
    public static Color ACCENT_BLUE;

    public static Color ACCENT_PURPLE_SOFT;
    public static Color ACCENT_BLUE_SOFT;

    // ── Cards ─────────────────────────────────────────────────────────────────
    public static Color GLASS_CARD;
    public static Color GLASS_CARD_ALT;
    public static Color GLASS_HIGHLIGHT;
    public static Color CARD_LABEL;

    // ── Background FX ─────────────────────────────────────────────────────────
    public static Color ORB_PURPLE;
    public static Color ORB_CYAN;
    public static Color ORB_MAGENTA;
    public static Color DIVIDER;

    // ── Text Areas ────────────────────────────────────────────────────────────
    public static Color TEXT_AREA_TEXT;
    public static Color TEXT_AREA_CARET;
    public static Color TEXT_AREA_SELECTION;

    // ── Buttons ───────────────────────────────────────────────────────────────
    public static Color BUTTON_TEXT;

    public static Color BUTTON_IMPORT_IDLE;
    public static Color BUTTON_IMPORT_PRESSED;

    public static Color BUTTON_IMPORT_GRADIENT_START;
    public static Color BUTTON_IMPORT_GRADIENT_END;

    public static Color BUTTON_IMPORT_BORDER_START;
    public static Color BUTTON_IMPORT_BORDER_END;

    public static Color BUTTON_ANALYZE_START;
    public static Color BUTTON_ANALYZE_END;

    public static Color BUTTON_ANALYZE_HOVER_START;
    public static Color BUTTON_ANALYZE_HOVER_END;

    public static Color BUTTON_ANALYZE_PRESSED_START;
    public static Color BUTTON_ANALYZE_PRESSED_END;

    public static Color BUTTON_ANALYZE_GLOW;

    // ── Semantic Status ───────────────────────────────────────────────────────
    public static Color SUCCESS;    // positive / confirmed
    public static Color NEGATIVE;   // negative / warning (e.g. sentiment, errors)

    // ── Misc (kept for compatibility) ─────────────────────────────────────────
    public static Color SCROLLBAR_THUMB;
    public static Color BLUE;
    public static Color PURPLE;

    static { setDarkTheme(); }

    // ─────────────────────────────────────────────────────────────────────────
    public static void setDarkTheme() {

        DARK_MODE = true;

        BACKGROUND                = new Color(3,   12,  35);
        BACKGROUND_GRADIENT_TOP   = new Color(3,   12,  35);
        BACKGROUND_GRADIENT_BOTTOM= new Color(8,   18,  48);

        SIDEBAR                   = new Color(9,   19,  44);
        CARD                      = new Color(14,  25,  55);
        BORDER                    = new Color(46,  58,  102);

        TEXT                      = new Color(240, 240, 245);
        SUBTEXT                   = new Color(150, 160, 190);

        TITLE                     = new Color(200, 160, 255);
        SUBTITLE                  = new Color(150, 140, 190);

        ACCENT_PURPLE             = new Color(148, 98,  255);
        ACCENT_BLUE               = new Color(50,  150, 230);

        ACCENT_PURPLE_SOFT        = new Color(130, 80,  255, 120);
        ACCENT_BLUE_SOFT          = new Color(80,  200, 255, 40);

        GLASS_CARD                = new Color(18,  12,  40,  200);
        GLASS_CARD_ALT            = new Color(18,  14,  38,  220);
        GLASS_HIGHLIGHT           = new Color(255, 255, 255, 18);
        CARD_LABEL                = new Color(160, 120, 255, 200);

        ORB_PURPLE                = new Color(88,  28,  220, 80);
        ORB_CYAN                  = new Color(6,   182, 212, 60);
        ORB_MAGENTA               = new Color(168, 85,  247, 50);
        DIVIDER                   = new Color(120, 80,  255, 40);

        TEXT_AREA_TEXT            = new Color(210, 200, 240);
        TEXT_AREA_CARET           = new Color(160, 100, 255);
        TEXT_AREA_SELECTION       = new Color(100, 60,  200, 120);

        BUTTON_TEXT               = Color.WHITE;

        BUTTON_IMPORT_IDLE        = new Color(255, 255, 255, 8);
        BUTTON_IMPORT_PRESSED     = new Color(100, 50,  200, 180);
        BUTTON_IMPORT_GRADIENT_START = new Color(100, 50,  210, 160);
        BUTTON_IMPORT_GRADIENT_END   = new Color(30,  160, 220, 140);
        BUTTON_IMPORT_BORDER_START   = new Color(150, 100, 255, 160);
        BUTTON_IMPORT_BORDER_END     = new Color(50,  180, 255, 100);

        BUTTON_ANALYZE_START         = new Color(100, 40,  220);
        BUTTON_ANALYZE_END           = new Color(20,  160, 230);
        BUTTON_ANALYZE_HOVER_START   = new Color(130, 60,  255);
        BUTTON_ANALYZE_HOVER_END     = new Color(30,  200, 255);
        BUTTON_ANALYZE_PRESSED_START = new Color(60,  20,  160);
        BUTTON_ANALYZE_PRESSED_END   = new Color(0,   120, 180);
        BUTTON_ANALYZE_GLOW          = new Color(160, 100, 255, 120);

        SUCCESS                   = new Color(52,  211, 153);
        NEGATIVE                  = new Color(239, 68,  68);

        SCROLLBAR_THUMB           = new Color(130, 80,  255, 120);
        BLUE                      = new Color(50,  150, 230);
        PURPLE                    = new Color(148, 98,  255);
    }

    // ─────────────────────────────────────────────────────────────────────────
    public static void setLightTheme() {

        DARK_MODE = false;

        BACKGROUND                = new Color(240, 242, 247);
        BACKGROUND_GRADIENT_TOP   = new Color(245, 243, 255);
        BACKGROUND_GRADIENT_BOTTOM= new Color(255, 255, 255);

        SIDEBAR                   = new Color(225, 230, 240);
        CARD                      = Color.WHITE;
        BORDER                    = new Color(190, 190, 200);

        TEXT                      = new Color(20,  20,  25);
        SUBTEXT                   = new Color(90,  90,  100);

        TITLE                     = new Color(110, 70,  220);
        SUBTITLE                  = new Color(105, 105, 145);

        ACCENT_PURPLE             = new Color(120, 80,  230);
        ACCENT_BLUE               = new Color(30,  140, 220);

        ACCENT_PURPLE_SOFT        = new Color(120, 80,  230, 90);
        ACCENT_BLUE_SOFT          = new Color(30,  140, 220, 50);

        GLASS_CARD                = new Color(255, 255, 255, 220);
        GLASS_CARD_ALT            = new Color(250, 250, 255, 235);
        GLASS_HIGHLIGHT           = new Color(255, 255, 255, 90);
        CARD_LABEL                = new Color(110, 70,  220, 220);

        ORB_PURPLE                = new Color(120, 80,  230, 35);
        ORB_CYAN                  = new Color(30,  140, 220, 30);
        ORB_MAGENTA               = new Color(180, 90,  255, 25);
        DIVIDER                   = new Color(120, 80,  230, 30);

        TEXT_AREA_TEXT            = new Color(30,  30,  40);
        TEXT_AREA_CARET           = new Color(120, 80,  230);
        TEXT_AREA_SELECTION       = new Color(160, 140, 255, 120);

        BUTTON_TEXT               = Color.WHITE;

        BUTTON_IMPORT_IDLE        = new Color(120, 80,  230, 12);
        BUTTON_IMPORT_PRESSED     = new Color(120, 80,  230, 100);
        BUTTON_IMPORT_GRADIENT_START = new Color(140, 110, 255, 120);
        BUTTON_IMPORT_GRADIENT_END   = new Color(100, 180, 255, 120);
        BUTTON_IMPORT_BORDER_START   = new Color(120, 80,  230, 120);
        BUTTON_IMPORT_BORDER_END     = new Color(30,  140, 220, 100);

        BUTTON_ANALYZE_START         = new Color(120, 80,  230);
        BUTTON_ANALYZE_END           = new Color(30,  140, 220);
        BUTTON_ANALYZE_HOVER_START   = new Color(145, 110, 255);
        BUTTON_ANALYZE_HOVER_END     = new Color(80,  190, 255);
        BUTTON_ANALYZE_PRESSED_START = new Color(95,  65,  180);
        BUTTON_ANALYZE_PRESSED_END   = new Color(20,  120, 190);
        BUTTON_ANALYZE_GLOW          = new Color(120, 80,  230, 80);

        SUCCESS                   = new Color(22,  163, 114);
        NEGATIVE                  = new Color(220, 38,  38);

        SCROLLBAR_THUMB           = new Color(120, 80,  230, 100);
        BLUE                      = new Color(30,  140, 220);
        PURPLE                    = new Color(120, 80,  230);
    }
}
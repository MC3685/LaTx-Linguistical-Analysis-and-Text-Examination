package components;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public final class FontLoader {

    private FontLoader() {
    }

    public static Font load(String fileName, float size) {
        try {
            InputStream stream = FontLoader.class.getResourceAsStream(fileName);

            Font font = Font.createFont(Font.TRUETYPE_FONT, stream);

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font.deriveFont(size);

        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }
}
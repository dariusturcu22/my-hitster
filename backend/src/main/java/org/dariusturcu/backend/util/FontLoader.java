package org.dariusturcu.backend.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FontLoader {
    @PostConstruct
    public void loadFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = {
                "/fonts/Kanit-Regular.ttf",
                "/fonts/Kanit-Bold.ttf",
                "/fonts/Kanit-Italic.ttf"
        };
        for (String path : fonts) {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is == null) throw new IllegalStateException("Font not found in classpath: " + path);
                Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                ge.registerFont(font);
            } catch (FontFormatException | IOException e) {
                throw new IllegalStateException("Failed to load font: " + path, e);
            }
        }
    }
}

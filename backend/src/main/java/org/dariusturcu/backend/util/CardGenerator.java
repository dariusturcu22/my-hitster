package org.dariusturcu.backend.util;

import org.dariusturcu.backend.model.song.Song;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardGenerator {
    private static final int CARD_SIZE = 800;
    private static final int CARDS_PER_ROW = 3;
    private static final int ROWS_PER_PAGE = 4;
    private static final int PAGE_WIDTH = CARD_SIZE * CARDS_PER_ROW;
    private static final int PAGE_HEIGHT = CARD_SIZE * ROWS_PER_PAGE;
    private static final int MARGIN_X = (PAGE_WIDTH - CARD_SIZE * CARDS_PER_ROW) / 2;
    private static final int MARGIN_Y = (PAGE_HEIGHT - CARD_SIZE * ROWS_PER_PAGE) / 2;


    public static BufferedImage generateInfoPage(List<Song> songs) {
        BufferedImage page = new BufferedImage(PAGE_WIDTH, PAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = page.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, PAGE_WIDTH, PAGE_HEIGHT);

        for (int i = 0; i < songs.size(); i++) {
            int x = MARGIN_X + (i % CARDS_PER_ROW) * CARD_SIZE;
            int y = MARGIN_Y + (i / CARDS_PER_ROW) * CARD_SIZE;
            drawFrontCard(graphics2D, x, y, CARD_SIZE, songs.get(i));
        }

        graphics2D.dispose();
        return page;
    }

    private static void drawFrontCard(Graphics2D graphics2D, int x, int y, int size, Song song) {
        Color color1 = decodeColorSafe(song.getGradientColor1());
        Color color2 = decodeColorSafe(song.getGradientColor2());

        GradientPaint gradientPaint = new GradientPaint(x, y, color1, x, y + size, color2);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(x, y, size, size);

        graphics2D.setColor(Color.WHITE);
        String fontName = "Kanit";

        Font artistFont = new Font(fontName, Font.PLAIN, 60);
        Font yearFont = new Font(fontName, Font.BOLD, 250);
        Font titleFont = new Font(fontName, Font.ITALIC, 55);

        // Artist
        graphics2D.setFont(artistFont);
        drawCentered(graphics2D, song.getArtist(), x, y + (int) (size * 0.18), size, 70);

        // Year
        graphics2D.setFont(yearFont);
        FontMetrics yearMetrics = graphics2D.getFontMetrics(yearFont);
        int yearY = y + (size / 2) + (yearMetrics.getAscent() / 3);
        drawCentered(graphics2D, String.valueOf(song.getReleaseYear()), x, yearY, size, 50);

        // Title
        graphics2D.setFont(titleFont);
        drawCentered(graphics2D, song.getTitle(), x, y + (int) (size * 0.88), size, 65);

        // Border
        graphics2D.setColor(new Color(0, 0, 0, 40));
        graphics2D.drawRect(x, y, size, size);
    }

    private static void drawCentered(Graphics2D graphics2D, String text, int x, int y, int maxWidth, int lineHeight) {
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        List<String> lines = new ArrayList<>();

        for (String word : words) {
            String test = line.isEmpty() ? word : line + " " + word;
            if (fontMetrics.stringWidth(test) > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(test);
            }
        }
        if (!line.isEmpty()) lines.add(line.toString());

        int totalHeight = lines.size() * lineHeight;
        int startY = y - totalHeight / 2;

        for (String l : lines) {
            int textWidth = fontMetrics.stringWidth(l);
            graphics2D.drawString(l, x + (maxWidth - textWidth) / 2, startY);
            startY += lineHeight;
        }
    }

    private static Color decodeColorSafe(String hex) {
        try {
            return Color.decode("#" + hex);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }
}

package org.dariusturcu.backend.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.dariusturcu.backend.model.song.Song;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Component
public class QRGenerator {
    private static final int CARD_SIZE = 800;
    private static final int CARDS_PER_ROW = 3;
    private static final int ROWS_PER_PAGE = 4;
    private static final int PAGE_WIDTH = CARD_SIZE * CARDS_PER_ROW;
    private static final int PAGE_HEIGHT = CARD_SIZE * ROWS_PER_PAGE;
    private static final int QR_SIZE = 500;
    private static final int MARGIN_X = (PAGE_WIDTH - CARD_SIZE * CARDS_PER_ROW) / 2;  // 40
    private static final int MARGIN_Y = (PAGE_HEIGHT - CARD_SIZE * ROWS_PER_PAGE) / 2;  // 54

    public static BufferedImage generateQRPage(List<Song> songs) {
        BufferedImage page = new BufferedImage(PAGE_WIDTH, PAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = page.createGraphics();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, PAGE_WIDTH, PAGE_HEIGHT);

        for (int i = 0; i < songs.size(); i++) {
            int row = i / CARDS_PER_ROW;
            int col = i % CARDS_PER_ROW;

            int backColumn = (CARDS_PER_ROW - 1) - col;

            int x = MARGIN_X + backColumn * CARD_SIZE;
            int y = MARGIN_Y + row * CARD_SIZE;

            drawQRCard(graphics2D, x, y, CARD_SIZE, songs.get(i));
        }

        graphics2D.dispose();
        return page;
    }

    private static void drawQRCard(Graphics2D graphics2D, int x, int y, int cardSize, Song song) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            String qrContent = song.getYoutubeId();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    qrContent,
                    BarcodeFormat.QR_CODE,
                    QR_SIZE,
                    QR_SIZE
            );

            BufferedImage qrCode = MatrixToImageWriter.toBufferedImage(bitMatrix);

            int offset = (cardSize - QR_SIZE) / 2;
            graphics2D.drawImage(qrCode, x + offset, y + offset, null);

            graphics2D.setColor(new Color(200, 200, 200));
            graphics2D.drawRect(x, y, cardSize, cardSize);
        } catch (WriterException e) {
            graphics2D.setColor(Color.RED);
            graphics2D.drawString("QR Error: " + song.getId(), x + 20, y + 50);
        }
    }
}

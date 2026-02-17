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
    private static final int QR_SIZE = 500;

    public static BufferedImage generateQRPage(List<Song> songs) {
        int width = CARD_SIZE * CARDS_PER_ROW;
        int rows = (int) Math.ceil((double) songs.size() / CARDS_PER_ROW);
        int height = CARD_SIZE * rows;

        BufferedImage page = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = page.createGraphics();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

        for (int i = 0; i < songs.size(); i++) {
            int row = i / CARDS_PER_ROW;
            int col = i % CARDS_PER_ROW;

            int backColumn = (CARDS_PER_ROW - 1) - col;

            int x = backColumn * CARD_SIZE;
            int y = row * CARD_SIZE;

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

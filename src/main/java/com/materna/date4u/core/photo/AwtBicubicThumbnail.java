package com.materna.date4u.core.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.Future;


// Bei mehreren Beans von einem Basistyp, muss angegeben werden, welcher Typ injiziert werden soll
// Über den Namen der Bean und @Qualifier(BEAN_NAME) wäre eine Möglichkeit.
@Service(AwtBicubicThumbnail.BEAN_NAME)
public class AwtBicubicThumbnail implements Thumbnail {
    static final String BEAN_NAME = "qualityThumbnailRenderer";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static BufferedImage create(BufferedImage source,
                                        int width, int height) {
        double thumbRatio = (double) width / height;
        double imageRatio = (double) source.getWidth() / source.getHeight();
        if (thumbRatio < imageRatio)
            height = (int) (width / imageRatio);
        else
            width = (int) (height * imageRatio);
        BufferedImage thumb = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = thumb.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(source, 0, 0, width, height, null);
        g2.dispose();
        return thumb;
    }

    @Override
    public Future<byte[]> thumbnail(byte[] imageBytes) {
        try (InputStream is = new ByteArrayInputStream(imageBytes);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(create(ImageIO.read(is), 200, 200), "jpg", baos);
            log.info("thumbnail");
            return new AsyncResult<>(baos.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
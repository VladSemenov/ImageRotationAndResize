package ImageProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vladislav on 26.04.16.
 */
public class ImageProcessor {
    private BufferedImage image;
    private int[] pixels;

    public ImageProcessor(BufferedImage image) {
        this.image = image;

        int offset = 0;
        this.pixels = new int[this.image.getHeight() * this.image.getWidth()];
        for (int y = 0; y < this.image.getHeight(); y++) {
            for (int x = 0; x < this.image.getWidth(); x++) {
                this.pixels[offset++] = this.image.getRGB(x, y);
            }
        }
    }

    public void resizeImage(int newWidth, int newHeight) {

        int height = this.image.getHeight();
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        double scale = (double) height / newHeight;

        for (int y = 0; y < newHeight - 1; y++) {
            for (int x = 0; x < newWidth - 1; x++) {
                newImage.setRGB(x, y, this.image.getRGB((int)(x * scale),(int)(y * scale)));
            }
        }

        this.image = newImage;
    }

    public void rotateImage(int alpha) {

        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        int al;
        al = alpha;
        if (al < 0) {
            al = 0 - al;
        }
        if (al > 270) {
            al = 360 - al;
        }
        if (al > 180) {
            al = al - 180;
        }
        if (al > 90) {
            al = 180 - al;
        }

        int newWidth = rotatedImage.getWidth();
        int newHeight = rotatedImage.getHeight();

        double a, b;
        b = ((double) newHeight / newWidth);
        a = ((double) newWidth / newHeight);
        a = Math.atan(a);
        b = Math.atan(b);
        a = Math.sin(al * Math.PI / 180 + a);
        b = Math.sin(al * Math.PI / 180 + b);
        int D = 1;
        int w1 = (int) Math.round(D * a);
        int h1 = (int) Math.round(D * b);

        if (w1 < newHeight && h1 < newHeight || h1 < newWidth && w1 < newWidth || h1 > D || w1 > D) {
            rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        } else {
            rotatedImage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
        }

        for (int y1 = 0; y1 < rotatedImage.getHeight(); y1++) {
            for (int x1 = 0; x1 < rotatedImage.getWidth(); x1++) {
                Color c = new Color(255, 255, 255, 0);
                rotatedImage.setRGB(x1, y1, c.getRGB());

            }
        }

        int xc = newWidth / 2;
        int yc = newHeight / 2;
        int dy = (rotatedImage.getHeight() - newHeight) / 2;
        int dx = (rotatedImage.getWidth() - newWidth) / 2;
        for (int y1 = 0 - dy; y1 < rotatedImage.getHeight() - dy; y1++) {
            for (int x1 = 0 - dx; x1 < rotatedImage.getWidth() - dx; x1++) {
                int x0 = (int) ((Math.cos(alpha * Math.PI / 180) * (x1 - xc)
                        + Math.sin(alpha * Math.PI / 180) * (y1 - yc)) + xc);
                int y0 = (int) (-(Math.sin(alpha * Math.PI / 180) * (x1 - xc)
                        - Math.cos(alpha * Math.PI / 180) * (y1 - yc)) + yc);
                if ((x0 > -1) && (x0 < newWidth) && (y0 > -1) && (y0 < newHeight)) {
                    rotatedImage.setRGB(x1 + dx, y1 + dy, image.getRGB(x0, y0));
                }

            }
        }
        this.image = rotatedImage;
    }
    public BufferedImage getImage(){
        return this.image;
    }
}

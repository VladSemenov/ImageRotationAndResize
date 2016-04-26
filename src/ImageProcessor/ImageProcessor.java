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
        int width = this.image.getWidth();
        int heigth = this.image.getHeight();
//        int[] temp = new int[newWidth * newHeight];
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        int a;
        int b;
        int c;
        int d;
        int x;
        int y;
        int index;
        float x_ratio = ((float) (width - 1)) / newWidth;
        float y_ratio = ((float) (heigth - 1)) / newHeight;
        float x_diff, y_diff, blue, red, green;
        int offset = 0;
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;
                index = (y * width + x);
                a = pixels[index];
                b = pixels[index + 1];
                c = pixels[index + width];
                d = pixels[index + width + 1];

                // Y = A(1-w)(1-h) + B(w)(1-h) + C(h)(1-w) + D(wh)
                red = ((a >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 16) & 0xff) * (x_diff) * (1 - y_diff)
                        + ((c >> 16) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 16) & 0xff) * (x_diff * y_diff);

                green = ((a >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 8) & 0xff) * (x_diff) * (1 - y_diff)
                        + ((c >> 8) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 8) & 0xff) * (x_diff * y_diff);

                blue = (a & 0xff) * (1 - x_diff) * (1 - y_diff) + (b & 0xff) * (x_diff) * (1 - y_diff)
                        + (c & 0xff) * (y_diff) * (1 - x_diff) + (d & 0xff) * (x_diff * y_diff);

                int currentPixel = ((((int) red) << 16) & 0xff0000) | ((((int) green) << 8) & 0xff00) | ((int) blue);
                newImage.setRGB(j, i, currentPixel);
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
        System.out.println("width:" + this.image.getWidth() + "height:" + this.image.getHeight());
        return this.image;
    }
}

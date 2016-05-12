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

        int width = this.image.getWidth();
        int height = this.image.getHeight();

        BufferedImage rotatedImage;

        if (alpha < 0) {
            alpha = 0 - alpha;
        }
        if (alpha > 270) {
            alpha = 360 - alpha;
        }
        if (alpha > 180) {
            alpha = alpha - 180;
        }
        if (alpha > 90) {
            alpha = 180 - alpha;
        }

        double heightToWidth = ((double) height / width);
        double widthToHeight = ((double) width / height);
        int newWidth = (int) (width / (Math.sin(alpha * Math.PI / 180 + Math.atan(heightToWidth))));
        int newHeight = (int) (height / (Math.sin(alpha * Math.PI / 180 + Math.atan(widthToHeight))));
        System.out.println("width=" + width + ",newWidth=" + newWidth);
        System.out.println("height=" + height + ",height=" + newHeight);

        rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < rotatedImage.getHeight(); y++) {
            for (int x = 0; x < rotatedImage.getWidth(); x++) {
                Color c = new Color(255, 255, 255, 0);
                rotatedImage.setRGB(x, y, c.getRGB());

            }
        }

        int centerX = width / 2;
        int centerY = height / 2;

        int dX = (rotatedImage.getWidth() - width) / 2;
        int dY = (rotatedImage.getHeight() - height) / 2;

        for (int y = 0 - dY; y < rotatedImage.getHeight() - dY; y++) {
            for (int x = 0 - dX; x < rotatedImage.getWidth() - dX; x++) {

                int x0 = (int) ((Math.cos(alpha * Math.PI / 180) * (x - centerX)
                        + Math.sin(alpha * Math.PI / 180) * (y - centerY)) + centerX);
                int y0 = (int) (-(Math.sin(alpha * Math.PI / 180) * (x - centerX)
                        - Math.cos(alpha * Math.PI / 180) * (y - centerY)) + centerY);

                if ((x0 > -1) && (x0 < width) && (y0 > -1) && (y0 < height)) {
                    rotatedImage.setRGB(x + dX, y + dY, image.getRGB(x0, y0));
                }

            }
        }
        this.image = rotatedImage;
    }
    public BufferedImage getImage(){
        return this.image;
    }
}

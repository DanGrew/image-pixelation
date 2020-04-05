package uk.dangrew.image.pixelation.all;

import javafx.scene.paint.Color;

/**
 * {@link ImageProperties} provides a convenient method for mocking {@link uk.dangrew.kode.friendly.javafx.FriendlyImage}
 * outputs.
 */
public class ImageProperties {

    private double width;
    private double height;
    private Color[][] pixelGrid;

    public ImageProperties() {
        withPixelDimensions(0, 0);
    }

    public ImageProperties withPixelDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixelGrid = new Color[width][height];

        return this;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ImageProperties withPixelColor(int x, int y, Color colour) {
        this.pixelGrid[x][y] = colour;
        return this;
    }

    public ImageProperties colourAll(Color colour) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixelGrid[i][j] = colour;
            }
        }
        return this;
    }

    public Color getColourAt(int x, int y) {
        return pixelGrid[x][y];
    }
}

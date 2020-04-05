package uk.dangrew.image.pixelation.all;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.URL;

/**
 * {@link ImagePixelationConfiguration} provides the configuration parameters for the process intended to be shared
 * around the system so that different aspects can register interest and modify.
 */
public class ImagePixelationConfiguration {

    private final ObjectProperty<URL> imageLocationProperty;
    private final ObjectProperty<Integer> outputPixelWidthProperty;
    private final ObjectProperty<Integer> overlapPixelWidthProperty;
    private final ColourPaletteConfiguration colourPaletteConfiguration;

    public ImagePixelationConfiguration() {
        this.imageLocationProperty = new SimpleObjectProperty<>();
        this.outputPixelWidthProperty = new SimpleObjectProperty<>(20);
        this.overlapPixelWidthProperty = new SimpleObjectProperty<>(0);
        this.colourPaletteConfiguration = new ColourPaletteConfiguration();
    }

    public ImagePixelationConfiguration withOutputPixelWidth(int outputPixelWidth) {
        this.outputPixelWidthProperty.set(outputPixelWidth);
        return this;
    }

    public int getOutputPixelSize() {
        return outputPixelWidthProperty.get();
    }

    public ObjectProperty<Integer> getOutputPixelSizeProperty() {
        return outputPixelWidthProperty;
    }

    public ImagePixelationConfiguration withOverlapPixelWidth(int overlapPixelWidth) {
        this.overlapPixelWidthProperty.set(overlapPixelWidth);
        return this;
    }

    public int getOverlapPixelSize() {
        return overlapPixelWidthProperty.get();
    }

    public ObjectProperty<Integer> getOverlapPixelSizeProperty() {
        return overlapPixelWidthProperty;
    }

    public ImagePixelationConfiguration withImage(URL url) {
        this.imageLocationProperty.set(url);
        return this;
    }

    public ObjectProperty<URL> getImageLocationProperty() {
        return imageLocationProperty;
    }

    public ColourPaletteConfiguration getColourPaletteConfiguration() {
        return colourPaletteConfiguration;
    }
}

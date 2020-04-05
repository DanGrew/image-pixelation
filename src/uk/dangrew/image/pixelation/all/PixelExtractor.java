package uk.dangrew.image.pixelation.all;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import uk.dangrew.kode.friendly.javafx.FriendlyImage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@link PixelExtractor} is responsible for extracting the relevant pixel {@link Color}s from an image given the
 * asociated configuration.
 */
public class PixelExtractor {

    private final ImagePixelationConfiguration configuration;

    public PixelExtractor(ImagePixelationConfiguration configuration) {
        this.configuration = configuration;
    }

    public Collection<Color> extractColours(FriendlyImage image, int outputX, int outputY) {
        int horizontalRange = configuration.getOutputPixelSize() + 2 * configuration.getOverlapPixelSize();
        int verticalRange = configuration.getOutputPixelSize() + 2 * configuration.getOverlapPixelSize();

        int startX = calculateStartCoordinate(outputX, image.getWidth());
        int startY = calculateStartCoordinate(outputY, image.getHeight());

        int endX = calculateEndCoordinate(startX, horizontalRange, image.getWidth());
        int endY = calculateEndCoordinate(startY, verticalRange, image.getHeight());

        PixelReader pixelReader = image.getPixelReader();

        Collection<Color> colours = new ArrayList<>();
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                colours.add(pixelReader.getColor(i, j));
            }
        }

        return colours;
    }

    /**
     * Calculates the start coordinate defined by output grid position multiplied by output pixel size (so that
     * everything from the top left corner in the original image is considered) minus the overlap size.
     *
     * @param gridPosition the output grid position.
     * @param maximum      the maximum that the start coordinate can be in relation to the image.
     * @return the start coordinate.
     */
    private int calculateStartCoordinate(int gridPosition, double maximum) {
        int absoluteStart = gridPosition * configuration.getOutputPixelSize() - configuration.getOverlapPixelSize();
        int clampedStart = clamp(absoluteStart, (int) maximum);
        return clampedStart;
    }

    /**
     * Calculates the end coordinate defined by start coordinate plus the given range (calculated elsewhere), clamped to
     * the image size.
     *
     * @param startPosition the start position.
     * @param range         the range to add.
     * @param maximum       the maximum that the start coordinate can be in relation to the image.
     * @return the end coordinate.
     */
    private int calculateEndCoordinate(int startPosition, int range, double maximum) {
        int absoluteEnd = startPosition + range;
        int clampedEnd = clamp(absoluteEnd, (int) maximum);
        return clampedEnd;
    }

    private int clamp(int value, int maximum) {
        value = Math.max(value, 0);
        value = Math.min(value, maximum);
        return value;
    }
}

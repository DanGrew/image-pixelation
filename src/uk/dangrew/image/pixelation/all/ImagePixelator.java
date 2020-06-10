package uk.dangrew.image.pixelation.all;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import uk.dangrew.kode.friendly.javafx.FriendlyImage;

import java.util.Collection;

/**
 * The {@link ImagePixelator} is the calculator that will construct the new image by obtaining the inputs, calculating
 * the output and placing it in the image.
 */
public class ImagePixelator {

    private final ImagePixelationConfiguration configuration;
    private final PixelExtractor pixelExtractor;
    private final PixelColourAverage pixelColourAverage;

    public ImagePixelator(ImagePixelationConfiguration configuration) {
        this(new PixelExtractor(configuration), new PixelColourAverage(), configuration);
    }

    ImagePixelator(PixelExtractor pixelExtractor, PixelColourAverage pixelColourAverage, ImagePixelationConfiguration configuration) {
        this.configuration = configuration;
        this.pixelExtractor = pixelExtractor;
        this.pixelColourAverage = pixelColourAverage;
    }

    /**
     * Performs a new pixelation of the given {@link FriendlyImage} by building a new {@link Image}.
     * @param input the {@link FriendlyImage} to be pixelated.
     * @return the constructed {@link Image}.
     */
    public Image pixelate(FriendlyImage input) {
        int width = (int) (input.friendly_getWidth() / configuration.getOutputPixelSize());
        int height = (int) (input.friendly_getHeight() / configuration.getOutputPixelSize());

        WritableImage outputImage = new WritableImage(width, height);
        PixelWriter pixelWriter = outputImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Collection<Color> influence = pixelExtractor.extractColours(input, i, j);
                Color colour = pixelColourAverage.computeAverage(influence);
                pixelWriter.setColor(i, j, colour);
            }
        }

        return outputImage;
    }
}

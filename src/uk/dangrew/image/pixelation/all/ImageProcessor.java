package uk.dangrew.image.pixelation.all;

import javafx.scene.image.Image;
import uk.dangrew.kode.friendly.javafx.FriendlyImage;

import java.io.IOException;
import java.net.URL;

/**
 * The {@link ImageProcessor} is responsible for triggering the {@link ImagePixelator} and placing the results in the
 * {@link PixelationSideBySide}.
 */
public class ImageProcessor {

    private final ImagePixelationConfiguration configuration;
    private final PixelationSideBySide pixelationSideBySide;

    public ImageProcessor(ImagePixelationConfiguration configuration, PixelationSideBySide pixelationSideBySide) {
        this.configuration = configuration;
        this.pixelationSideBySide = pixelationSideBySide;
    }

    public void process() {
        Image originalImage = loadImage(configuration.getImageLocationProperty().get());
        pixelationSideBySide.changeOriginal(originalImage);

        Image modifiedImage = new ImagePixelator(configuration)
                .pixelate(new FriendlyImage(originalImage));
        pixelationSideBySide.changeModified(modifiedImage);
    }

    private Image loadImage(URL url) {
        Image image = null;
        try {
            image = new Image(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}

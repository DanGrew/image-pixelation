package uk.dangrew.image.pixelation.all;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Simple ui structure to show two images side by side.
 */
public class PixelationSideBySide extends GridPane {

    private final ImageView originalImageView;
    private final ImageView modifiedImageView;

    public PixelationSideBySide(){
        this.originalImageView = new ImageView();
        this.originalImageView.setFitWidth(500);
        this.originalImageView.setFitHeight(500);
        this.add(originalImageView, 0, 0);

        this.modifiedImageView = new ImageView();
        this.modifiedImageView.setFitWidth(500);
        this.modifiedImageView.setFitHeight(500);
        this.add(modifiedImageView, 0, 1);
    }

    public void changeOriginal(Image original){
        this.originalImageView.setImage(original);
    }

    public void changeModified(Image modified){
        this.modifiedImageView.setImage(modified);
    }

}

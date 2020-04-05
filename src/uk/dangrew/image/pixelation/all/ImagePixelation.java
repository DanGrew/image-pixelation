/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.image.pixelation.all;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Entry point to the system for launching.
 */
public class ImagePixelation extends Application {

    static final String TITLE = "Nutrient Usage Tracking System";

    public ImagePixelation() {
    }//End Constructor

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(TITLE);
        stage.setOnCloseRequest(event -> {
            PlatformImpl.exit();
            System.exit(0);
        });

        ImagePixelationConfiguration configuration = new ImagePixelationConfiguration();

        BorderPane view = new BorderPane();
        PixelationSideBySide sideBySide = new PixelationSideBySide();
        ImageProcessor imageProcessor = new ImageProcessor(configuration, sideBySide);

        view.setLeft(sideBySide);
        view.setRight(new ImagePixelationControls(configuration, imageProcessor));
//        view.setCenter(new ColourPaletteView(new ColourPalette()));

        stage.setScene(new Scene(view));
        stage.setMaximized(true);
        stage.show();
    }//End Method

    public static void main(String[] args) {
        launch();
    }//End Method

}//End Class

package uk.dangrew.image.pixelation.all;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.friendly.controlsfx.FriendlyFileChooser;
import uk.dangrew.kode.javafx.custom.BoundIntegerAsTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveButtonRegion;
import uk.dangrew.kode.javafx.custom.ResponsiveTextRegion;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;

import java.io.File;
import java.net.MalformedURLException;

public class ImagePixelationControls extends BorderPane {

    public ImagePixelationControls(ImagePixelationConfiguration configuration, ImageProcessor imageProcessor) {
        TextField chosenFileField = new TextField();
        chosenFileField.setEditable(false);
        configuration.getImageLocationProperty().addListener((s, o, n) -> chosenFileField.setText(n.getFile()));

        FriendlyFileChooser chooser = new FriendlyFileChooser();

        Button process = new Button("Process");
//        process.setOnAction(e -> imageProcessor.process());

        PropertiesPane controls = new PropertiesPane("Image Pixelation",
                new PropertyRowBuilder()
                        .withLabelName("Choose Image")
                        .withBinding(new ResponsiveButtonRegion(new Button("Browse..."), e -> {
                            File chosen = chooser.showOpenDialog(null);
                            if (chosen != null) {
                                try {
                                    configuration.withImage(chosen.toURI().toURL());
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        })),
                new PropertyRowBuilder()
                        .withLabelName("Selected Image")
                        .withBinding(new ResponsiveTextRegion(chosenFileField, (s, o, n) -> {
                        })),
                new PropertyRowBuilder()
                        .withLabelName("Output Pixel Size")
                        .withBinding(new BoundIntegerAsTextProperty(configuration.getOutputPixelSizeProperty(), true)),
                new PropertyRowBuilder()
                        .withLabelName("Overlap Pixel Size")
                        .withBinding(new BoundIntegerAsTextProperty(configuration.getOverlapPixelSizeProperty(), true)),
                new PropertyRowBuilder()
                        .withLabelName("Pixelate Image")
                        .withBinding(new ResponsiveButtonRegion(process, e -> imageProcessor.process())));

        this.setCenter(controls);


        //disabled if no url set
    }
}

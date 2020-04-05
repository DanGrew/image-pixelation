package uk.dangrew.image.pixelation.all;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class ColourPaletteView extends GridPane {

    public ColourPaletteView(ColourPaletteConfiguration colourPaletteConfiguration){
        int row = 0;
        int column = 0;
        for ( Color colour : colourPaletteConfiguration.getColours()){
            Label label = new Label(colourPaletteConfiguration.nameOf(colour));
            label.setBackground(new JavaFxStyle().backgroundFor(colour));
            add(label, column, row);

            row++;
            if ( row > 40){
                row = 0;
                column++;
            }
        }
    }
}

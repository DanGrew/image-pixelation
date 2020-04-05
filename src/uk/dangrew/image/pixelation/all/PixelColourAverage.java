package uk.dangrew.image.pixelation.all;

import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * {@link PixelColourAverage} is responsible for computing the average pixel colour from a collection of inputs.
 */
public class PixelColourAverage {

    public Color computeAverage(Collection<Color> colours){
        double red = 0;
        double blue = 0;
        double green = 0;

        for ( Color colour : colours ) {
            red += colour.getRed();
            blue += colour.getBlue();
            green += colour.getGreen();
        }

        red /= colours.size();
        blue /= colours.size();
        green /= colours.size();

        return Color.color(red, green, blue);
    }
}

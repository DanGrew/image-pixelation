package uk.dangrew.image.pixelation.all;

import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.color.JavaFxNamedColours;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link ColourPaletteConfiguration} is responsible for providing the colours for the system to use.
 */
public class ColourPaletteConfiguration {

    private final JavaFxNamedColours javaFxNamedColours;
    private final Map<Color, Boolean> palette;

    public ColourPaletteConfiguration() {
        this.palette = new LinkedHashMap<>();
        this.javaFxNamedColours = new JavaFxNamedColours();

        for (Color colour : javaFxNamedColours.getColours()) {
            this.palette.put(colour, true);
        }
    }

    public void setEnabled(Color colour, boolean enabled) {
        if ( !palette.containsKey(colour)){
            return;
        }
        palette.put(colour, enabled);
    }

    public Collection<Color> getColours() {
        return Collections.unmodifiableCollection(
                palette.keySet().stream()
                        .filter(palette::get)
                        .collect(Collectors.toList()));
    }

    public String nameOf(Color colour) {
        return javaFxNamedColours.getNameFor(colour);
    }
}

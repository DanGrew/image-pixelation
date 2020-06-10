package uk.dangrew.image.pixelation.all;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.dangrew.kode.friendly.javafx.FriendlyImage;

import java.util.Collection;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PixelExtractorTest {

    private ImageProperties imageProperties;
    @Mock private FriendlyImage image;
    @Mock private PixelReader pixelReader;

    private ImagePixelationConfiguration configuration;
    private PixelExtractor systemUnderTest;

    @Before
    public void initialiseSystemUnderTest() {
        initMocks(this);
        configuration = new ImagePixelationConfiguration();
        systemUnderTest = new PixelExtractor(configuration);

        imageProperties = new ImageProperties();
        when(image.friendly_getWidth()).thenAnswer(i -> imageProperties.getWidth());
        when(image.friendly_getHeight()).thenAnswer(i -> imageProperties.getHeight());
        when(image.friendly_getPixelReader()).thenReturn(pixelReader);
        when(pixelReader.getColor(anyInt(), anyInt()))
                .thenAnswer(i -> imageProperties.getColourAt(
                        (int) i.getArguments()[0],
                        (int) i.getArguments()[1]));
    }

    @Test
    public void shouldIdentifyColoursFromPixelReader() {
        imageProperties
                .withPixelDimensions(9, 9)
                .colourAll(Color.RED);
        configuration
                .withOutputPixelWidth(9);

        Collection<Color> result = systemUnderTest.extractColours(image, 0, 0);
        assertThat(result, hasSize(9 * 9));
        for (Color color : result) {
            assertThat(color, is(Color.RED));
        }
    }

    @Test
    public void shouldCapExtractionAtImageWidthAndHeight() {
        imageProperties
                .withPixelDimensions(4, 4)
                .colourAll(Color.RED);
        configuration
                .withOutputPixelWidth(9);

        Collection<Color> result = systemUnderTest.extractColours(image, 0, 0);
        assertThat(result, hasSize(4 * 4));
        for (Color color : result) {
            assertThat(color, is(Color.RED));
        }
    }

    @Test
    public void shouldIncludeColoursUsingOverlap() {
        imageProperties
                .withPixelDimensions(24, 24)
                .colourAll(Color.RED);
        configuration
                .withOutputPixelWidth(2)
                .withOverlapPixelWidth(3);

        Collection<Color> result = systemUnderTest.extractColours(image, 1, 1);

        int expectedGridSize = configuration.getOutputPixelSize() + (2 * configuration.getOverlapPixelSize());
        expectedGridSize *= expectedGridSize;

        assertThat(result, hasSize(expectedGridSize));
        for (Color color : result) {
            assertThat(color, is(Color.RED));
        }
    }

    @Test
    public void shouldCapExtractionAtWidthAndHeightUsingOverlap() {
        imageProperties
                .withPixelDimensions(5, 5)
                .colourAll(Color.RED);
        configuration
                .withOutputPixelWidth(4)
                .withOverlapPixelWidth(5);

        Collection<Color> result = systemUnderTest.extractColours(image, 0, 0);
        assertThat(result, hasSize(5 * 5));
        for (Color color : result) {
            assertThat(color, is(Color.RED));
        }
    }

    @Test
    public void shouldIncludeColoursUsingOverlapInMiddleOfImage() {
        imageProperties
                .withPixelDimensions(20, 20)
                .colourAll(Color.RED)
                //centre point
                .withPixelColor(10, 10, Color.GREEN)
                //four corners of bounds to extract
                .withPixelColor(7, 7, Color.BLUE)
                .withPixelColor(14, 7, Color.BLUE)
                .withPixelColor(14, 14, Color.BLUE)
                .withPixelColor(7, 14, Color.BLUE);

        configuration
                .withOutputPixelWidth(2)
                .withOverlapPixelWidth(3);

        Collection<Color> result = systemUnderTest.extractColours(image, 5, 5);

        int expectedGridSize = configuration.getOutputPixelSize() + (2 * configuration.getOverlapPixelSize());
        expectedGridSize *= expectedGridSize;

        assertThat(result, hasSize(expectedGridSize));

        assertThat(result.stream()
                .filter(c -> c.equals(Color.GREEN))
                .count(), is(1L));

        assertThat(result.stream()
                .filter(c -> c.equals(Color.BLUE))
                .count(), is(4L));
    }


}
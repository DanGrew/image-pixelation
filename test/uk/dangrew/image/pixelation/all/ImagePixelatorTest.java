package uk.dangrew.image.pixelation.all;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.dangrew.kode.friendly.javafx.FriendlyImage;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ImagePixelatorTest {

    @Mock private FriendlyImage image;
    @Mock private PixelExtractor pixelExtractor;
    @Mock private PixelColourAverage pixelColourAverage;

    private ImagePixelationConfiguration configuration;
    private ImagePixelator systemUnderTest;

    @Before
    public void initialiseSystemUnderTest(){
        initMocks(this);
        configuration = new ImagePixelationConfiguration();
        systemUnderTest = new ImagePixelator(pixelExtractor, pixelColourAverage, configuration);

        when(pixelColourAverage.computeAverage(anyList())).thenReturn(Color.RED);
    }

    @Test
    public void shouldProduceOutputWithCorrectDimensions(){
        when(image.getWidth()).thenReturn(40.5);
        when(image.getHeight()).thenReturn(60.5);

        Image result = systemUnderTest.pixelate(image);
        assertThat(result.getWidth(), is( 2.0));
        assertThat(result.getHeight(), is(3.0));
    }

    @Test public void shouldIdentifyCorrctColouring(){
        when(image.getWidth()).thenReturn(20.0);
        when(image.getHeight()).thenReturn(20.0);

        Collection<Color> extractedColours = Arrays.asList(Color.BLUE, Color.RED);
        when(pixelExtractor.extractColours(image, 0, 0)).thenReturn(extractedColours);
        when(pixelColourAverage.computeAverage(extractedColours)).thenReturn(Color.BLUE);

        Image result = systemUnderTest.pixelate(image);
        assertThat(result.getWidth(), is( 1.0));
        assertThat(result.getHeight(), is(1.0));
        assertThat(result.getPixelReader().getColor(0, 0), is(Color.BLUE));
    }
}
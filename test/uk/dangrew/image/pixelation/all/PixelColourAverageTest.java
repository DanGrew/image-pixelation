package uk.dangrew.image.pixelation.all;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import uk.dangrew.image.pixelation.all.PixelColourAverage;
import uk.dangrew.kode.TestCommon;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;

public class PixelColourAverageTest {

    private PixelColourAverage systemUnderTest;

    @Before
    public void initialiseSystemUnderTest() {
        systemUnderTest = new PixelColourAverage();
    }

    @Test
    public void shouldComputeAverageColourFromBaseColours(){
        assertThat(systemUnderTest.computeAverage(Collections.singleton(Color.RED)), is(Color.RED));
        assertThat(systemUnderTest.computeAverage(Collections.singleton(Color.BLUE)), is(Color.BLUE));
        assertThat(systemUnderTest.computeAverage(Collections.singleton(Color.GREEN)), is(Color.GREEN));
    }

    @Test public void shouldComputeAverageColourFromBaseCombinations(){
        Color result = systemUnderTest.computeAverage(Arrays.asList(Color.RED, Color.BLUE));
        assertColourComposition(result, redSharedOver(2), 0, blueSharedOver(2));

        result = systemUnderTest.computeAverage(Arrays.asList(Color.GREEN, Color.BLUE));
        assertColourComposition(result, 0, greenSharedOver(2), blueSharedOver(2));

        result = systemUnderTest.computeAverage(Arrays.asList(Color.RED, Color.GREEN));
        assertColourComposition(result, redSharedOver(2), greenSharedOver(2), 0);

        result = systemUnderTest.computeAverage(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
        assertColourComposition(result, redSharedOver(3), greenSharedOver(3), blueSharedOver(3));
    }

    private void assertColourComposition(Color result, double r, double g, double b){
        assertThat(result.getRed(), is(closeTo(r, TestCommon.precision())));
        assertThat(result.getGreen(), is(closeTo(g, TestCommon.precision())));
        assertThat(result.getBlue(), is(closeTo(b, TestCommon.precision())));
    }

    private double greenSharedOver(int count){
        return averagedColourComponent(Color.GREEN, Color::getGreen, count);
    }

    private double blueSharedOver(int count){
        return averagedColourComponent(Color.BLUE, Color::getBlue, count);
    }

    private double redSharedOver(int count){
        return averagedColourComponent(Color.RED, Color::getRed, count);
    }

    private double averagedColourComponent(Color colour, Function<Color, Double> component, int count){
        return component.apply(colour) / count;
    }

}
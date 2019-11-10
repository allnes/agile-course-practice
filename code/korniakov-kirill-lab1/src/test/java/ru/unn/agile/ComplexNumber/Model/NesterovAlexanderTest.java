package ru.unn.agile.ComplexNumber.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class NesterovAlexanderTest {
    @Test
    public void canArgNumberHalfPI() {
        // Arrange
        ComplexNumber z = new ComplexNumber(0, 1);

        // Act
        double res = z.getArg();

        // Assert
        assertEquals(res, Math.PI / 2, 1e-15);
    }

    @Test
    public void canArgNumberZero() {
        // Arrange
        ComplexNumber z = new ComplexNumber(1, 0);

        // Act
        double res = z.getArg();

        // Assert
        assertEquals(res, 0, 1e-15);
    }

    @Test
    public void canArgNumberQuarterPI() {
        // Arrange
        ComplexNumber z = new ComplexNumber(Math.sqrt(2) / 2, Math.sqrt(2) / 2);

        // Act
        double res = z.getArg();

        // Assert
        assertEquals(res, Math.PI / 4, 1e-15);
    }
}

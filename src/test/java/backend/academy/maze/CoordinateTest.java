package backend.academy.maze;

import backend.academy.maze.outputConsole.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testCoordinateCreation() {
        // Arrange & Act
        Coordinate coordinate = new Coordinate(2, 3);

        // Assert
        assertEquals(2, coordinate.row());
        assertEquals(3, coordinate.col());
    }

    @Test
    void testCoordinateEquality() {
        // Arrange
        Coordinate coord1 = new Coordinate(1, 1);
        Coordinate coord2 = new Coordinate(1, 1);

        // Act & Assert
        assertEquals(coord1, coord2);
        assertEquals(coord1.hashCode(), coord2.hashCode());
    }

    @Test
    void testCoordinateInequality() {
        // Arrange
        Coordinate coord1 = new Coordinate(1, 1);
        Coordinate coord2 = new Coordinate(2, 2);

        // Act & Assert
        assertNotEquals(coord1, coord2);
    }

    @Test
    void testCoordinateToString() {
        // Arrange
        Coordinate coordinate = new Coordinate(5, 7);

        // Act & Assert
        assertEquals("Coordinate[row=5, col=7]", coordinate.toString());
    }
}


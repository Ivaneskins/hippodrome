import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    private static Horse horse;

    private static final int HORSE_SPEED = 60;
    private static final int HORSE_DISTANCE = 100;
    private static final int HORSE_SPEED_MINUS = -10;
    private static final String HORSE_NAME = "Ipolit";
    private static final String ERR_MSG_NAME_NULL = "Name cannot be null.";
    private static final String ERR_MSG_WHITE_SPACE = "Name cannot be blank.";
    private static final String ERR_MSG_SPEED_MINUS = "Speed cannot be negative.";
    private static final String ERR_MSG_DISTANCE_MINUS = "Distance cannot be negative.";

    @BeforeAll
    static void setHorse() {
        horse = new Horse(HORSE_NAME, HORSE_SPEED, HORSE_DISTANCE);
    }


    @Test
    void checkFirstArgumentToNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, HORSE_SPEED));
    }

    @Test
    void checkMessageFromException() {
        try {
            new Horse(null, HORSE_SPEED);
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_NAME_NULL, e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("whitespaceStringsProvider")
    void checkFirstArgumentConstructorForWhiteSpaces(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(input, HORSE_SPEED));
    }

    @ParameterizedTest
    @MethodSource("whitespaceStringsProvider")
    void checkMsgForFirstConstructorArgumentWithWhiteSpace(String input) {
        try {
            new Horse(input, HORSE_SPEED);
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_WHITE_SPACE, e.getMessage());
        }
    }

    @Test
    void checkSecondArgumentConstructorForMinusNum() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, HORSE_SPEED_MINUS));
    }

    @Test
    void checkThrownMsgWithSecondArgumentConstructorForMinusNum() {
        try {
            new Horse(HORSE_NAME, HORSE_SPEED_MINUS);
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_SPEED_MINUS, e.getMessage());
        }
    }

    @Test
    void checkThatThrownExceptionWithThirdArgConstructorWithMinusNum() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, HORSE_SPEED, HORSE_SPEED_MINUS));
    }

    @Test
    void checkThrownMsgWithThirdArgConstructorForMinusNum() {
        try {
            new Horse(HORSE_NAME, HORSE_SPEED, HORSE_SPEED_MINUS);
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_DISTANCE_MINUS, e.getMessage());
        }
    }

    @Test
    void getName() {
        assertEquals(HORSE_NAME, horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(HORSE_SPEED, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(HORSE_DISTANCE, horse.getDistance());
        Horse newHorse = new Horse(HORSE_NAME, HORSE_SPEED);
        assertEquals(0, newHorse.getDistance());
    }

    @ParameterizedTest
    @CsvSource({
            "0.2, 0.9, 0.5"
    })
    void move(double a, double b, double result) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(a, b)).thenReturn(result);

            double startDistance = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(a, b);
            mockedStatic.verify(() -> Horse.getRandomDouble(a, b));
            horse.move();

            assertEquals(horse.getDistance(), startDistance);
        }
    }

    // Метод для предоставления данных через @MethodSource
    static Stream<String> whitespaceStringsProvider() {
        return Stream.of("", " ", "  ", "\t", "\n", "\r", "\t\n\r ");
    }
}
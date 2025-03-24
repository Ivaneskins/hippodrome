import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    private static final String ERR_MSG_FIRST_ARGUMENT_NULL = "Horses cannot be null.";
    private static final String ERR_MSG_EMPTY_LIST = "Horses cannot be empty.";

    @Test
    void checkThrowExceptionForFirstArgConstructorForNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void checkErrMesWhenFirstArgConstructorIsNull() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_FIRST_ARGUMENT_NULL, e.getMessage());
        }
    }

    @Test
    void checkThatThrowExceptionForEmptyArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
    }

    @Test
    void checkErrMessWhenConstructorGetEmptyList() {
        try {
            new Hippodrome(List.of());
        } catch (IllegalArgumentException e) {
            assertEquals(ERR_MSG_EMPTY_LIST, e.getMessage());
        }
    }

    @Test
    void getHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse " + i, i * 2));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(hippodrome.getHorses(), horseList);
    }

    @Test
    void move() {
        List<Horse> mockedHorseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockedHorseList.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(mockedHorseList);
        hippodrome.move();
        for (Horse horse : mockedHorseList) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            horseList.add(new Horse(String.valueOf(i), i, i));
        }
        double maxSpeed = horseList.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get().getDistance();
        Hippodrome hippodrome = new Hippodrome(horseList);

        assertEquals(maxSpeed, hippodrome.getWinner().getDistance());
    }
}
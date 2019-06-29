package coinhunter;

import java.util.Random;

public class Util {
    public static int rand(int min, int max, Random rand) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range");
        }

        return rand.ints(min, (max + 1)) // IntStream
                .findFirst() // OptionalInt
                .getAsInt(); // int
    }
}

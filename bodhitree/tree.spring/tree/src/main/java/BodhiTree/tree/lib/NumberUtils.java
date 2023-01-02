package bodhitree.tree.lib;

import java.util.Random;

public class NumberUtils {

    public static Random randomGenerator = new Random();

    public static int randomInt (int bound) {
        // from 0 to bound
        return randomGenerator.nextInt(bound);
    }

    public static String randomIntStr (int bound, int length) {
        // from 0 to bound
        int num = randomGenerator.nextInt(bound);
        return String.format("%0" + length + "d", num);
    }
}

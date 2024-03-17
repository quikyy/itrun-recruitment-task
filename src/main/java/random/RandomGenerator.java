package random;

import java.util.Random;

public class RandomGenerator {

    private final static String[] firstNames = {"Szymon", "Jan", "Anna", "Tomasz", "Jonasz", "Krzysztof", "Grzegorz", "Anna", "Renata", "Ewa"};
    private final static String[] lastNames = {"Lato", "Kowal", "Zima", "Nowak", "Ziobro", "Jagiello", "Kozio"};

    private final static Random random = new Random();

    public static String getRandomFirstName() {
        return firstNames[random.nextInt(firstNames.length)];
    }

    public static String getRandomLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    public static String getRandomNumber(int length) {
        String mobile = "";
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                mobile += String.valueOf(random.nextInt(1, 9));
                continue;
            }
            mobile += String.valueOf(random.nextInt(9));
        }
        return mobile;
    }

}

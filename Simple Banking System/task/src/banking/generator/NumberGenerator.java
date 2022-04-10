package banking.generator;

import java.util.concurrent.ThreadLocalRandom;

public abstract class NumberGenerator {

    static long generate(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}

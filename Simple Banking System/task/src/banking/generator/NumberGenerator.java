package banking.generator;

import banking.util.PropertiesLoader;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public abstract class NumberGenerator {

    protected static final Properties PROPERTIES = PropertiesLoader.getInstance().properties();

    protected static long generate(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }


}

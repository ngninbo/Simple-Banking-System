package banking.generator;

import banking.util.PropertiesLoader;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public abstract class NumberGenerator {

    protected final Properties PROPERTIES = PropertiesLoader.getInstance().properties();

    public abstract String next();

    protected long next(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}

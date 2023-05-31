package banking.generator;


import banking.domain.MessageFactory;

import java.util.concurrent.ThreadLocalRandom;

public abstract class NumberGenerator {

    protected static final MessageFactory MESSAGE_FACTORY = new MessageFactory();

    protected static long generate(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }


}

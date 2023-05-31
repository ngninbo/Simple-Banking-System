package banking.domain;

import java.text.MessageFormat;

public abstract class Formatter {

    private final ResourceProperties properties;

    public Formatter(ResourceProperties properties) {
        this.properties = properties;
    }

    public String get(String messageKey, Object... params) {
        return MessageFormat.format(from(messageKey), params);
    }

    public String from(String messageKey) {
        return properties.get(messageKey);
    }
}

package banking.domain;

import java.util.ResourceBundle;

public class ResourceProperties {

    private final ResourceBundle resourceBundle;

    public ResourceProperties(String filename) {
        resourceBundle = ResourceBundle.getBundle(filename);
    }

    public static ResourceProperties from(String filename) {
        return new ResourceProperties(filename);
    }

    public String get(String key) {
        return resourceBundle.getString(key);
    }
}

package banking.builder;

import banking.Automate;

public class AutomateFactory {

    public static Automate create(String databaseFilename) {
        return Automate.init(databaseFilename);
    }
}

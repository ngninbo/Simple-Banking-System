package banking;

import banking.builder.AutomateFactory;

public class Main {
    public static void main(String[] args) {
        // Start automate 4000003663552475 (2634) -- > 4000003432583223 (not exists) // 4000004336206028 (7465) // 4000002715961882 (3638)
        AutomateFactory.create(args[1]).run();
    }
}
package banking;

import banking.builder.AutomateBuilder;

public class Main {
    public static void main(String[] args) {
        // Start automate 4000003663552475 (2634) -- > 4000003432583223 (not exists) // 4000004336206028 (7465)
        AutomateBuilder.init(args[1])
                .withCardService()
                .build().start();
    }
}
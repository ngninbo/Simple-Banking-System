package banking.util;

import java.util.Scanner;

public interface UserInput {

    /**
     * Request user to input an amount
     * @param command {@link String} Text message telling the user what to da, e.e. Enter amount
     * @return amount {@link Long}
     */
    static long inputAmount(String command) {
        return Long.parseLong(request(command));
    }

    /**
     * Prompt users to enter something
     *
     * @param message {@link String}
     * @return user input {@link String}
     */
    static String request(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}

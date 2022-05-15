package banking;

import banking.services.AccountSessionService;
import banking.services.CardService;
import banking.util.PropertiesLoader;
import banking.util.RequestUserTo;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * This class serves for account management.
 * It provided several methods for creating an account, logging into an account and
 * for performing some transaction on an account
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class Automate {

    private final CardService cardService;
    private final BiFunction<String, String, Boolean> cardInfoValidation;
    private Properties properties;
    private boolean exit = false;

    public static final int BALANCE_CMD = 1;
    public static final int CREATE_CARD_CMD = 1;
    public static final int ADD_INCOME_CMD = 2;
    public static final int TRANSFER_CMD = 3;
    public static final int CLOSE_ACCOUNT_CMD = 4;
    public static final int LOG_OUT_CMD = 5;
    public static final int LOG_IN_CMD = 2;
    public static final int EXIT_CMD = 0;

    {
        try {
            properties = PropertiesLoader.loadProperties("logs.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Automate(CardService cardService) {
        this.cardService = cardService;
        cardInfoValidation = cardService::isCardWithCardNumberAndPinAvailable;
    }

    /**
     * Start automate
     */
    public void start() throws IOException {

        while (!exit) {
            int choice = displaySelectionMenu(Menu.START);
            switch (choice) {
                case CREATE_CARD_CMD:
                    cardService.createCard();
                    break;
                case LOG_IN_CMD:
                    loginToAccount();
                    break;
                case EXIT_CMD:
                    printByeMessage();
                    exit = true;
                    break;
            }
        }
    }

    /**
     * Display action the user can perform
     *
     * @param menu Item from enumerator
     * @return User selection
     */
    private int displaySelectionMenu(Menu menu) {
        return displayOptions(menu == Menu.START ? List.of(
                        properties.getProperty("CREATE_AN_ACCOUNT"),
                        properties.getProperty("LOG_INTO_ACCOUNT"),
                        properties.getProperty("EXIT_OPTION")
                ) : List.of(
                        properties.getProperty("BALANCE"),
                        properties.getProperty("ADD_INCOME"),
                        properties.getProperty("DO_TRANSFER"),
                        properties.getProperty("CLOSE_ACCOUNT"),
                        properties.getProperty("LOG_OUT"),
                        properties.getProperty("EXIT_OPTION")
                )
        );
    }

    /**
     * Login into account to perform transactions
     */
    private void loginToAccount() throws IOException {
        String cardNumber = RequestUserTo.inputCardInformation(properties.getProperty("USER_CARD_NUMBER_INPUT_REQUEST_MSG"));
        String pin = RequestUserTo.inputCardInformation(properties.getProperty("USER_PIN_INPUT_REQUEST_MSG"));

        if (!cardInfoValidation.apply(cardNumber, pin)) {
            printWrongCardNumberOrPinErrorMessage();
        } else {
            boolean login = true;
            AccountSessionService account = AccountSessionService.accountSession(cardNumber, cardService);
            printLoginState(true);

            while (login) {
                int action = displaySelectionMenu(Menu.LOGIN);

                switch (action) {
                    case BALANCE_CMD:
                        account.printAccountBalance();
                        break;
                    case ADD_INCOME_CMD:
                        account.addIncome();
                        break;
                    case TRANSFER_CMD:
                        account.doTransfer();
                        break;
                    case CLOSE_ACCOUNT_CMD:
                        account.closeAccount();
                        login = false;
                        break;
                    case LOG_OUT_CMD:
                        printLoginState(false);
                        login = false;
                        break;
                    case EXIT_CMD:
                        printByeMessage();
                        break;
                }
            }
        }
    }

    /**
     * Say 'Bye' and end the program
     */
    private void printByeMessage() {
        System.out.println(properties.getProperty("BYE_MSG"));
        System.exit(0);
    }

    /**
     * Informed user that the input card number or PIN is wrong when validation failed
     */
    private void printWrongCardNumberOrPinErrorMessage() {
        System.out.println(properties.getProperty("WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG") + "\n");
    }

    /**
     * @param options List of options
     * @return selected option
     */
    private int displayOptions(List<String> options) {
        // Print menu
        int selectedOption;
        IntStream.range(0, options.size())
                .forEach(i -> System.out.printf("%d. %s\n",
                        properties.getProperty("EXIT_OPTION").equals(options.get(i)) ? 0 : i + 1, options.get(i)));

        // Prompt user to select an option
        Scanner scanner = new Scanner(System.in);
        selectedOption = scanner.nextInt();

        return selectedOption;
    }


    /**
     * Print message depending on the login state
     *
     * @param loginState login state
     */
    private void printLoginState(boolean loginState) {
        String state = String.format(properties.getProperty("LOG_IN_STATUS_MSG"),
                loginState ? properties.getProperty("IN_TXT") : properties.getProperty("OUT_TXT"));
        System.out.printf("%s\n\n", state);
    }
}
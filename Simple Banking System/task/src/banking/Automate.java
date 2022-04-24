package banking;

import banking.services.AccountSessionService;
import banking.services.CardService;
import banking.util.RequestUserTo;

import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static banking.util.TextOutput.*;

/**
 * This class serves for account management.
 * It provided several methods for creating an account, logging into an account and
 * for performing some transaction on an account
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class Automate {

    private boolean exit = false;
    private final CardService cardService;
    private final BiFunction<String, String, Boolean> cardInfoValidation;


    public Automate(CardService cardService) {
        this.cardService = cardService;
        cardInfoValidation = cardService::isCardWithCardNumberAndPinAvailable;
    }

    /**
     * Start automate
     */
    public void start() {

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
        return menu == Menu.START ? displayOptions(START_MENU_OPTIONS) : displayOptions(LOGIN_MENU_OPTIONS);
    }

    /**
     * Login into account to perform transactions
     */
    private void loginToAccount() {
        String cardNumber = RequestUserTo.inputCardInformation(USER_CARD_NUMBER_INPUT_REQUEST_MSG);
        String pin = RequestUserTo.inputCardInformation(USER_PIN_INPUT_REQUEST_MSG);

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
        System.out.println(BYE_MSG);
        System.exit(0);
    }

    /**
     * Informed user that the input card number or PIN is wrong when validation failed
     */
    private void printWrongCardNumberOrPinErrorMessage() {
        System.out.println(WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG);
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
                        EXIT_OPTION.equals(options.get(i)) ? 0 : i + 1, options.get(i)));

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
        String state = String.format(LOG_IN_STATUS_MSG, loginState ? IN_TXT : OUT_TXT);
        System.out.println(state);
    }
}
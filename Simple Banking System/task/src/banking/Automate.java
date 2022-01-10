package banking;

import banking.generator.CreditCardGenerator;
import banking.services.*;
import banking.models.Card;
import banking.repository.CardRepository;
import static banking.util.TextOutput.*;

import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * This class serves for account management.
 * It provided several methods for creating an account, logging into an account and
 * for performing some transaction on an account
 * @author Beauclair Dongmo Ngnintedem
 */
public class Automate {

    private boolean exit = false;
    private int selectedOption;
    private final CardService cardService;
    private final BiFunction<String, String, Boolean> cardInfoValidation;


    private Automate(String filename) {
        CardRepository repository = CardRepository.init(filename);
        cardService = new CardServiceImpl(repository);
        cardInfoValidation = cardService::isCardAvailableByCardNumberAndPin;
    }

    public static Automate init(String filename) {
        return new Automate(filename);
    }

    /**
     * Start automate
     */
    public void start() {

        while (!exit) {
            int choice = displaySelectionMenu(Menu.START);
            switch (choice) {
                case 1:
                    createCard();
                    break;
                case 2:
                    loginToAccount();
                    break;
                case 0:
                    printByeMessage();
                    exit = true;
                    break;
            }
        }
    }

    /**
     * Display action the user can perform
     * @param menu Item from enumerator
     * @return User selection
     */
    private int displaySelectionMenu(Menu menu) {

        switch (menu) {
            case START:
                selectedOption = displayOptions(START_MENU_OPTIONS);
                break;
            case LOGIN:
                selectedOption = displayOptions(LOGIN_MENU_OPTIONS);
                break;
        }

        return selectedOption;
    }

    /**
     * Create card and print card number and PIN when creation succeed
     */
    private void createCard() {

        Card card = CreditCardGenerator.init()
                .createPin()
                .createCardNumber()
                .build();

        cardService.saveCard(card);

        System.out.printf(CARD_INFORMATION_AFTER_CREATION_TEXT,
                card.getCreditCardNumber(), card.getPin());
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
                    case 1:
                        account.printAccountBalance();
                        break;
                    case 2:
                        account.addIncome();
                        break;
                    case 3:
                        account.doTransfer();
                        break;
                    case 4:
                        account.closeAccount();
                        login = false;
                        break;
                    case 5:
                        printLoginState(false);
                        login = false;
                        break;
                    case 0:
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
        options.forEach(System.out::println);
        // Prompt user to select an option
        Scanner scanner = new Scanner(System.in);
        selectedOption = scanner.nextInt();

        return selectedOption;
    }



    /**
     * Print message depending on the login state
     * @param loginState login state
     */
    private void printLoginState(boolean loginState) {
        String state;
        state = loginState ? LOG_IN_SUCCEED_MSG : LOG_OUT_SUCCEED_MSG;
        System.out.println(state);
    }
}
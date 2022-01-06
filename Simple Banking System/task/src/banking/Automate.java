package banking;

import banking.generator.CreditCardGenerator;
import banking.models.Account;
import banking.models.Card;
import banking.repository.CardRepository;
import banking.services.CardService;
import banking.services.CardServiceImpl;
import banking.services.PromptUser;

import java.util.Scanner;

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

    private Automate(String filename) {
        CardRepository repository = CardRepository.createCardRepository(filename);
        cardService = new CardServiceImpl(repository);
    }

    public static Automate createAutomate(String filename) {
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
                    sayBye();
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
        StringBuilder options;
        switch (menu) {
            case START:
                options = new StringBuilder();
                options.append("1. Create an account\n")
                        .append("2. Log into account\n")
                        .append("0. Exit");
                selectedOption = displayOptions(options);
                break;
            case LOGIN:
                options = new StringBuilder();
                options.append("1. Balance\n")
                        .append("2. Add income\n")
                        .append("3. Do transfer\n")
                        .append("4. Close account\n")
                        .append("5. Log out\n")
                        .append("0. Exit");
                selectedOption = displayOptions(options);
                break;
        }

        return selectedOption;
    }

    /**
     * Create card and print card number and PIN when creation succeed
     */
    private void createCard() {
        CreditCardGenerator generator = CreditCardGenerator.createCreditCardGenerator();
        Card card = generator.getCard();
        cardService.saveCard(card);
        StringBuilder creationResult;
        creationResult = new StringBuilder();
        creationResult.append("\nYour card has been created\n")
                .append("Your card number:\n").append(card.getCreditCardNumber()).append("\n")
                .append("Your card PIN:\n").append(card.getPin()).append("\n");
        System.out.println(creationResult);
    }

    /**
     * Login into account to perform transactions
     */
    private void loginToAccount() {
        String cardNumber = PromptUser.getCardInfo("\nEnter your card number:");
        String pin = PromptUser.getCardInfo("Enter your PIN:");

        if (cardService.validateCard(cardNumber, pin)) {
            boolean login = true;
            Account account = Account.accountSession(cardNumber, cardService);
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
                        sayBye();
                        break;
                }
            }
        } else {
            printWrongInputsMessage();
        }
    }

    /**
     * Say 'Bye' and end the program
     */
    private void sayBye() {
        System.out.println("\nBye");
        System.exit(0);
    }

    /**
     * Informed user that the input card number or PIN is wrong when validation failed
     */
    private void printWrongInputsMessage() {
        System.out.println("Wrong card number or PIN!\n");
    }

    /**
     * @param options Options for choice
     * @return user selected option
     */
    private int displayOptions(StringBuilder options) {
        // Print menu
        int selectedOption;
        System.out.println(options);
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
        state = "\nYou have successfully logged out!\n";
        if (loginState) {
            state = "\nYou have successfully logged in!\n";
        }
        System.out.println(state);
    }
}
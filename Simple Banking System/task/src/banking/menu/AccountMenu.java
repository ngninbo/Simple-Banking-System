package banking.menu;

import banking.services.AccountSessionService;
import banking.services.CardService;
import banking.util.RequestUserTo;

import java.io.IOException;
import java.util.function.BiFunction;

public class AccountMenu extends Menu {

    private final BiFunction<String, String, Boolean> cardInfoValidation;
    private AccountSessionService account;

    public AccountMenu(CardService cardService) {
        super(cardService);
        cardInfoValidation = cardService::isCardWithCardNumberAndPinAvailable;
    }

    @Override
    public void process() throws IOException {
        String cardNumber = RequestUserTo.inputCardInformation(properties.getProperty("USER_CARD_NUMBER_INPUT_REQUEST_MSG"));
        String pin = RequestUserTo.inputCardInformation(properties.getProperty("USER_PIN_INPUT_REQUEST_MSG"));

        if (!cardInfoValidation.apply(cardNumber, pin)) {
            printWrongCardNumberOrPinErrorMessage();
        } else {
            account = AccountSessionService.accountSession(cardNumber, cardService);
            printLoginState(properties.getProperty("IN_TXT"));
            super.process();
        }
    }

    @Override
    protected boolean process(MenuItem menuItem) throws IOException {

        switch (menuItem) {
            case BALANCE:
                account.printAccountBalance();
                break;
            case ADD_INCOME:
                account.addIncome();
                break;
            case DO_TRANSFER:
                account.doTransfer();
                break;
            case CLOSE_ACCOUNT:
                account.closeAccount();
                return false;
            case LOG_OUT:
                printLoginState(properties.getProperty("OUT_TXT"));
                return false;
            case EXIT:
                exit();
                break;
            default:
        }

        return true;
    }

    @Override
    protected MenuItem getMenuItem(int choice) {
        int size = MenuItem.account().size();
        if (choice >= size) {
            return MenuItem.UNKNOWN;
        }

        if (choice == 0) {
            return MenuItem.EXIT;
        }

        return MenuItem.account().get(choice - 1);
    }

    @Override
    public int displayMenu() {
        return displayOptions(MenuItem.account());
    }

    /**
     * Informed user that the input card number or PIN is wrong when validation failed
     */
    private void printWrongCardNumberOrPinErrorMessage() {
        System.out.println(properties.getProperty("WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG") + "\n");
    }

    /**
     * Print message depending on the login state
     *
     * @param loginState login state
     */
    private void printLoginState(String loginState) {
        String state = String.format(properties.getProperty("LOG_IN_STATUS_MSG"), loginState);
        System.out.printf("%s\n\n", state);
    }
}

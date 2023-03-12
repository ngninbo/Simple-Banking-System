package banking.menu;

import banking.services.AccountSessionService;

import java.io.IOException;

public class AccountMenu extends Menu {

    private final AccountSessionService account;

    public AccountMenu(AccountSessionService account) {
        this.account = account;
    }

    @Override
    public void process() throws IOException {
        if (!account.login()) {
            printWrongCardNumberOrPinErrorMessage();
        } else {
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
        return getMenuItem(choice, MenuItem.account());
    }

    @Override
    protected int displayMenu() {
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

    @Override
    protected void exit() {
        super.exit();
        System.exit(0);
    }
}

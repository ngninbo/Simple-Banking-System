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
            System.out.println(properties.getProperty("WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG") + "\n");
        } else {
            printLoginState(LoginState.LOGGED_IN);
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
                printLoginState(LoginState.LOGGED_OUT);
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
     * Print message depending on the login state
     *
     * @param loginState {@link LoginState} login state
     */
    private void printLoginState(LoginState loginState) {
        String state = String.format(properties.getProperty("LOG_IN_STATUS_MSG"), loginState.getName());
        System.out.printf("%s\n\n", state);
    }

    @Override
    protected void exit() {
        super.exit();
        System.exit(0);
    }
}

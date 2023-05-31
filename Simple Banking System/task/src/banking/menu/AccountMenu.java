package banking.menu;

import banking.services.AccountSessionService;

import java.io.IOException;
import java.util.List;

/**
 * This class represents the menu displayed after log into account in the simple banking system.
 * It provided several methods for performing some transaction on an account.
 * It extends {@link Menu}
 * @author Beauclair Dongmo Ngnintedem
 */
public class AccountMenu extends Menu {

    private static final List<MenuItem> ACCOUNT_MENU_ITEMS = MenuItem.ACCOUNT_OPTIONS;
    private final AccountSessionService account;

    public AccountMenu(AccountSessionService account) {
        this.account = account;
    }

    @Override
    public void show() throws IOException {
        if (!account.login()) {
            System.out.println(messageFactory.from("WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG").concat("\n"));
        } else {
            printLoginState(LoginState.LOGGED_IN);
            super.show();
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
                account.close();
                System.out.println();
                return false;
            case LOG_OUT:
                printLoginState(LoginState.LOGGED_OUT);
                System.out.println();
                return false;
            case EXIT:
                exit();
                break;
            default:
        }

        System.out.println();
        return true;
    }

    @Override
    protected MenuItem getMenuItem(int choice) {
        return getMenuItem(choice, ACCOUNT_MENU_ITEMS);
    }

    @Override
    protected int display() {
        return display(ACCOUNT_MENU_ITEMS);
    }

    /**
     * Print message depending on the login state
     *
     * @param loginState {@link LoginState} login state
     */
    private void printLoginState(LoginState loginState) {
        System.out.println(messageFactory.from(loginState.getLabel()));
    }

    @Override
    protected void exit() {
        super.exit();
        System.exit(0);
    }
}

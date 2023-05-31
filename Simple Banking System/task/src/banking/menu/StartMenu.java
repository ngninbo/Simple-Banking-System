package banking.menu;

import banking.services.AccountSessionService;

import java.io.IOException;
import java.util.List;

/**
 * This class represents the initial menu of the simple banking system
 * It provided several options for creating an account, logging into an account.
 * It extends {@link Menu}
 * @author Beauclair Dongmo Ngnintedem
 */
public class StartMenu extends Menu {

    private static final List<MenuItem> OPTIONS = MenuItem.START_OPTIONS;
    private final AccountSessionService account;

    public StartMenu(AccountSessionService account) {
        this.account = account;
    }

    @Override
    protected boolean process(MenuItem menuItem) throws IOException {

        switch (menuItem) {
            case CREATE_AN_ACCOUNT:
                account.createAccount();
                System.out.println();
                break;
            case LOG_INTO_ACCOUNT:
                new AccountMenu(account).show();
                break;
            case EXIT:
                exit();
                return false;
            default:
        }

        return true;
    }

    @Override
    protected MenuItem getMenuItem(int choice) {
        return getMenuItem(choice, OPTIONS);
    }

    @Override
    protected int display() {
        return display(OPTIONS);
    }
}

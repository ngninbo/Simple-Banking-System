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

    private static final List<MenuItem> START_MENU_ITEMS = MenuItem.start();
    private final AccountSessionService account;

    public StartMenu(AccountSessionService account) {
        this.account = account;
    }

    @Override
    protected boolean process(MenuItem menuItem) throws IOException {

        switch (menuItem) {
            case CREATE_AN_ACCOUNT:
                account.createAccount();
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
        return getMenuItem(choice, START_MENU_ITEMS);
    }

    @Override
    protected int display() {
        return display(START_MENU_ITEMS);
    }
}

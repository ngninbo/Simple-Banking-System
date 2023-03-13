package banking.menu;

import banking.services.AccountSessionService;

import java.io.IOException;
import java.util.List;

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
                new AccountMenu(account).process();
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
    protected int displayMenu() {
        return displayOptions(START_MENU_ITEMS);
    }
}

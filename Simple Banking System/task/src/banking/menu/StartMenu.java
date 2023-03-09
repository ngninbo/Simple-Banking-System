package banking.menu;

import banking.services.CardService;

import java.io.IOException;

public class StartMenu extends Menu {

    private MenuItem item = MenuItem.UNKNOWN;

    public StartMenu(CardService cardService) {
        super(cardService);
    }

    @Override
    protected boolean process(MenuItem menuItem) throws IOException {

        switch (menuItem) {
            case CREATE_AN_ACCOUNT:
                cardService.createCard();
                break;
            case LOG_INTO_ACCOUNT:
                new AccountMenu(cardService).process();
                break;
            case EXIT:
                exit();
                return false;
            default:
        }

        return true;
    }

    @Override
    public void process() throws IOException {
        while (process(item)) {
            int choice = displayMenu();
            item = getMenuItem(choice);
        }
    }

    @Override
    protected MenuItem getMenuItem(int choice) {

        final int size = MenuItem.start().size();
        if (choice >= size) {
            return MenuItem.UNKNOWN;
        }

        if (choice == 0) {
            return MenuItem.EXIT;
        }

        return MenuItem.start().get(choice - 1);
    }

    @Override
    protected int displayMenu() {
        return displayOptions(MenuItem.start());
    }
}

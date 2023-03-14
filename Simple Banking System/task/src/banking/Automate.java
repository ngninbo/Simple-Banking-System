package banking;

import banking.services.AccountSessionService;
import banking.menu.StartMenu;

import java.io.IOException;

public class Automate implements Runnable {

    private final AccountSessionService account;

    public Automate(AccountSessionService account) {
        this.account = account;
    }

    @Override
    public void run() {

        try {
            new StartMenu(account).show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
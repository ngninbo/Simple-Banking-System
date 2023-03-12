package banking;

import banking.services.AccountSessionService;
import banking.menu.StartMenu;

import java.io.IOException;

/**
 * This class serves for account management.
 * It provided several methods for creating an account, logging into an account and
 * for performing some transaction on an account
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class Automate implements Runnable {

    private final AccountSessionService account;

    public Automate(AccountSessionService account) {
        this.account = account;
    }

    @Override
    public void run() {

        try {
            new StartMenu(account).process();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
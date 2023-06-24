package banking;

import banking.repository.CardRepository;
import banking.services.AccountSessionService;
import banking.menu.StartMenu;
import banking.services.CardServiceImpl;

import java.io.IOException;

public class Automate implements Runnable {

    private final AccountSessionService account;

    public static Automate init(String databaseFilename) {
        return new Automate(AccountSessionService.accountSession(new CardServiceImpl(CardRepository.init(databaseFilename))));
    }

    private Automate(AccountSessionService account) {
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
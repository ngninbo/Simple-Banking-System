package banking;

import banking.menu.StartMenu;
import banking.services.CardService;

import java.io.IOException;

/**
 * This class serves for account management.
 * It provided several methods for creating an account, logging into an account and
 * for performing some transaction on an account
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class Automate extends Thread {

    private final CardService cardService;

    public Automate(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void start() {

        try {
            new StartMenu(cardService).process();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
package banking.services;

import java.io.IOException;

/**
 * This class is for performing transaction on an account after logging in.
 * @author Beauclair Dongmo Ngnintedem
 */
public class AccountSessionService {

    private final AccountService accountService;

    private AccountSessionService(String cardNumber, CardService cardService) {
        this.accountService = new AccountServiceImpl(cardNumber, cardService);
    }

    public static AccountSessionService accountSession(String cardNumber, CardService cardService) {
        return new AccountSessionService(cardNumber, cardService);
    }

    /**
     * Transfer money to the current account to another
     */
    public void doTransfer() throws IOException {
        accountService.doTransfer();
    }

    /**
     * Delete account from database. The user will be automatically logged out
     */
    public void closeAccount() {
        accountService.closeAccount();
    }

    /**
     * Output balance of card with given number
     */
    public void printAccountBalance() {
        accountService.printAccountBalance();
    }

    /**
     * Prompt user to input the income value and update balance of given account
     */
    public void addIncome() {
        accountService.addIncome();
    }
}
package banking.services;

import java.io.IOException;

public interface AccountService {

    boolean login();
    void addIncome();
    void doTransfer() throws IOException;
    void printAccountBalance();
    void closeAccount();
}

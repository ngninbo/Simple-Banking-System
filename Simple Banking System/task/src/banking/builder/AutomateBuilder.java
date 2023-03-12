package banking.builder;

import banking.Automate;
import banking.repository.CardRepository;
import banking.services.AccountSessionService;
import banking.services.CardServiceImpl;

public class AutomateBuilder {

    private final String databaseFilename;
    private AccountSessionService account;

    private AutomateBuilder(String databaseFilename) {
        this.databaseFilename = databaseFilename;
    }

    public static AutomateBuilder init(String databaseFilename) {
        return new AutomateBuilder(databaseFilename);
    }

    public AutomateBuilder withSession() {
        this.account = AccountSessionService.accountSession(new CardServiceImpl(CardRepository.of(databaseFilename)));
        return this;
    }

    public Automate build() {
        return new Automate(account);
    }
}

package banking.builder;

import banking.Automate;
import banking.repository.CardRepository;
import banking.services.CardService;
import banking.services.CardServiceImpl;

public class AutomateBuilder {

    private final String databaseFilename;
    private CardService cardService;

    private AutomateBuilder(String databaseFilename) {
        this.databaseFilename = databaseFilename;
    }

    public static AutomateBuilder init(String databaseFilename) {
        return new AutomateBuilder(databaseFilename);
    }

    public AutomateBuilder withCardService() {
        this.cardService = new CardServiceImpl(CardRepository.init(databaseFilename));
        return this;
    }

    public Automate build() {
        return new Automate(cardService);
    }
}

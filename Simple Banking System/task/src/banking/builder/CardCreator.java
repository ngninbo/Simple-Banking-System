package banking.builder;

import banking.generator.CreditCardNumberGenerator;
import banking.generator.NumberGeneratorContext;
import banking.generator.PinNumberGenerator;
import banking.models.Card;

public class CardCreator {

    private final NumberGeneratorContext ctx = new NumberGeneratorContext();

    private String createCardNumber() {
        return ctx.setNumberGenerator(new CreditCardNumberGenerator()).next();
    }

    private String createPin() {
       return ctx.setNumberGenerator(new PinNumberGenerator()).next();
    }

    public Card create() {
        return CreditCardBuilder.init()
                .withCardNumber(createCardNumber())
                .withPin(createPin())
                .build();
    }
}

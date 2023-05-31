package banking.services;

import banking.domain.MessageFactory;
import banking.util.CreditCardNumberValidator;

import java.util.function.BiPredicate;

import static banking.services.TransferResult.*;
import static banking.util.UserInput.inputAmount;

public abstract class TransferExecutor {

    protected final CardService cardService;
    protected final CreditCardNumberValidator validator = new CreditCardNumberValidator();
    protected final MessageFactory messageFactory = new MessageFactory();

    public TransferExecutor(CardService cardService) {
        this.cardService = cardService;
    }

    protected TransferResult doTransfer(String source, String target) {
        if (target.equals(source)) {
            return SAME_ACCOUNT_ERROR;
        } else if (!validator.setCreditCardNumber(target).validate()) {
            return CARD_NUMBER_ERROR;
        } else if (cardService.findCardByNumber(target).isEmpty()) {
            return CARD_NOT_EXISTS_ERROR;
        } else {
            return transferWhenIsEnoughMoney(source, target);
        }
    }

    private TransferResult transferWhenIsEnoughMoney(String source, String target) {
        long amount = inputAmount(messageFactory.from("AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG"));
        if (isEnoughMoney().negate().test(source, amount)) {
            return NOT_ENOUGH_MONEY_ERROR;
        } else {
            cardService.transfer(amount, source, target);
            return SUCCESS;
        }
    }

    private BiPredicate<String, Long> isEnoughMoney() {
        return (source, amount) -> cardService.findCardByNumber(source)
                .map(card -> card.getBalance() > amount)
                .orElse(Boolean.FALSE);
    }
}

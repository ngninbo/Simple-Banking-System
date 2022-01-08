package banking.services;

import banking.models.Card;

import java.util.Optional;

public interface CardService {

    void saveCard(Card card);
    Optional<Card> findCardByNumber(String cardNumber);
    long readBalanceByCardNumber(String cardNumber);
    void updateBalanceByCardNumber(String cardNumber, long income);
    void deleteCardByNumber(String cardNumber);
    void updateBalanceByCardNumber(String cardNumber, String targetCardNumber, long amount);
    boolean isCardAvailableByCardNumberAndPin(String cardNumber, String pin);
    boolean isCardByNumberPresent(String targetCardNumber);
    boolean isCardByNumberAndPinPresent(String cardNumber, String pin);

    /**
     * Validate the given card number with the Luhn algorithm
     * @param creditCardNumber Credit card number
     * @return isValid Whether the given number passed the Luhn algorithm
     */
    static boolean isValid(String creditCardNumber) {

        int checkSum = Integer.parseInt(String.valueOf(creditCardNumber.charAt(creditCardNumber.length() - 1)));
        boolean isValid;

        for (int i = 0; i < creditCardNumber.length() - 1; i++) {
            int digit = Integer.parseInt(String.valueOf(creditCardNumber.charAt(i)));

            if (i % 2 == 0) {
                digit = digit * 2;

                if (digit > 9) {
                    digit = digit - 9;
                }
            }
            checkSum += digit;
        }

        isValid = checkSum % 10 == 0;

        return isValid;
    }
}

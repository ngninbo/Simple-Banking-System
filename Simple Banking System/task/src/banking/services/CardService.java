package banking.services;

import banking.models.Card;

public interface CardService {

    void saveCard(Card card);
    Card getCardByNumber(String cardNumber);
    long getBalance(String cardNumber);
    void addIncome(String cardNumber, long income);
    void deleteCard(String cardNumber);
    void setBalance(String cardNumber, String targetCardNumber, long amount);
    boolean validateCard(String cardNumber, String pin);

    /**
     * Validate the given card number with the Luhn algorithm
     * @param creditCardNumber Credit card number
     * @return isValid Whether the given number passed the Luhn algorithm
     */
    static boolean checkCreditCardNumber(String creditCardNumber) {

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

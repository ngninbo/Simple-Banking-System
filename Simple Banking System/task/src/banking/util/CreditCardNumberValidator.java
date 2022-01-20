package banking.util;

public interface CreditCardNumberValidator {

    /**
     * Validate the given card number with the Luhn algorithm
     *
     * @param creditCardNumber Credit card number
     * @return isValid Whether the given number passed the Luhn algorithm
     */
    static boolean validate(String creditCardNumber) {

        return computeCheckSum(creditCardNumber) % 10 == 0;
    }

    static int computeCheckSum(String creditCardNumber) {
        int checkSum = Integer.parseInt(String.valueOf(creditCardNumber.charAt(creditCardNumber.length() - 1)));

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
        return checkSum;
    }
}

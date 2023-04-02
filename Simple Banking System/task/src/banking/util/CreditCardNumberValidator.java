package banking.util;

import java.math.BigInteger;

public interface CreditCardNumberValidator {

    /**
     * Validate the given card number with the Luhn algorithm
     *
     * @param creditCardNumber {@link String} Credit card number
     * @return {@link Boolean} Whether the given number passed the Luhn algorithm or not
     */
    static boolean isValid(String creditCardNumber) {

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

        return checkSum % BigInteger.TEN.intValue() == 0;
    }
}

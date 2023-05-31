package banking.util;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class CreditCardNumberValidator {

    private String creditCardNumber;

    public CreditCardNumberValidator setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public boolean validate() {

        int checkSum = Integer.parseInt(String.valueOf(creditCardNumber.charAt(creditCardNumber.length() - 1)));
        checkSum += IntStream.iterate(creditCardNumber.length() - 2, i -> i >= 0, i -> i - 1)
                .map(i -> check(i, Integer.parseInt(String.valueOf(creditCardNumber.charAt(i)))))
                .sum();

        return checkSum % BigInteger.TEN.intValue() == 0;
    }

    private int check(int index, int digit) {
        if (index % 2 == 0) {
            digit = digit * 2;

            if (digit > 9) {
                digit = digit - 9;
            }
        }

        return digit;
    }
}

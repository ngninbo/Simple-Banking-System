package banking.util;

public class DisplayMessage {

    public static final String CARD_NUMBER_ERROR_MSG = "Probably you made a mistake in the card number. Please try again!\n";
    public static final String SAME_ACCOUNT_ERROR_MSG = "You can't transfer money to the same account!\n";
    public static final String CARD_NOT_EXISTS_ERROR_MSG = "Such a card does not exist.\n";
    public static final String AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG = "Enter how much money you want to transfer:";
    public static final String NOT_ENOUGH_MONEY_ERROR_MSG = "Not enough money!\n";
    public static final String SUCCESS_MSG = "Success!\n";
    public static final String ACCOUNT_BALANCE_TEXT = "\nBalance: %s\n\n";
    public static final String ACCOUNT_CLOSED_TEXT = "\nThe account has been closed!\n";
    public static final String INCOME_INPUT_REQUEST_MSG = "\nEnter income:";
    public static final String INCOME_ADDED_TEXT = "Income was added!\n";
    public static final String TRANSFER_TEXT = "\nTransfer";
    public static final StringBuilder START_MENU_ITEMS = new StringBuilder().append("1. Create an account\n")
            .append("2. Log into account\n").append("0. Exit");
    public static final StringBuilder LOGIN_SESSION_MENU_ITEMS = new StringBuilder().append("1. Balance\n")
            .append("2. Add income\n")
            .append("3. Do transfer\n")
            .append("4. Close account\n")
            .append("5. Log out\n")
            .append("0. Exit");

    public static final String USER_CARD_NUMBER_INPUT_REQUEST_MSG = "\nEnter your card number:";
    public static final String USER_PIN_INPUT_REQUEST_MSG = "Enter your PIN:";
    public static final String BYE_MSG = "\nBye!";
    public static final String WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG = "\nWrong card number or PIN!\n";
    public static final String LOG_OUT_SUCCEED_MSG = "\nYou have successfully logged out!\n";
    public static final String LOG_IN_SUCCEED_MSG = "\nYou have successfully logged in!\n";
    public static final String CARD_INFORMATION_AFTER_CREATION_TEXT = "\nYour card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n\n";
}

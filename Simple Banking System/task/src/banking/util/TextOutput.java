package banking.util;

import java.util.List;

public class TextOutput {

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
    public static final String EXIT_OPTION = "Exit";

    public static List<String> START_MENU_OPTIONS = List.of(
            "Create an account",
            "Log into account",
            EXIT_OPTION);

    public static List<String> LOGIN_MENU_OPTIONS = List.of(
            "Balance",
            "Add income",
            "Do transfer",
            "Close account",
            "Log out",
            EXIT_OPTION);

    public static final int BALANCE_CMD = 1;
    public static final int CREATE_CARD_CMD = 1;
    public static final int ADD_INCOME_CMD = 2;
    public static final int TRANSFER_CMD = 3;
    public static final int CLOSE_ACCOUNT_CMD = 4;
    public static final int LOG_OUT_CMD = 5;
    public static final int LOG_IN_CMD = 2;
    public static final int EXIT_CMD = 0;

    public static final String USER_CARD_NUMBER_INPUT_REQUEST_MSG = "\nEnter your card number:";
    public static final String USER_PIN_INPUT_REQUEST_MSG = "Enter your PIN:";
    public static final String BYE_MSG = "\nBye!";
    public static final String WRONG_CARD_NUMBER_OR_PIN_ERROR_MSG = "\nWrong card number or PIN!\n";
    public static final String OUT_TXT = "out";
    public static final String IN_TXT = "in";
    public static final String LOG_IN_STATUS_MSG = "\nYou have successfully logged %s!\n";
    public static final String CARD_INFORMATION_AFTER_CREATION_TEXT = "\nYour card has been created\n" +
            "Your card number:\n%s\nYour card PIN:\n%s\n\n";
    public static final String TARGET_CARD_NUMBER_INPUT_REQUEST_MSG = "Enter card number:";
}

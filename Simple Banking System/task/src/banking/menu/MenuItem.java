package banking.menu;

import java.util.List;

public enum MenuItem {

    CREATE_AN_ACCOUNT,
    LOG_INTO_ACCOUNT,
    BALANCE,
    ADD_INCOME,
    DO_TRANSFER,
    CLOSE_ACCOUNT,
    LOG_OUT,
    EXIT,
    UNKNOWN;

    public static List<MenuItem> START_OPTIONS = List.of(CREATE_AN_ACCOUNT, LOG_INTO_ACCOUNT, EXIT);

    public static List<MenuItem> ACCOUNT_OPTIONS = List.of(BALANCE, ADD_INCOME, DO_TRANSFER, CLOSE_ACCOUNT, LOG_OUT, EXIT);

    public String getText() {
        return "MENU_ITEM_".concat(name());
    }
}

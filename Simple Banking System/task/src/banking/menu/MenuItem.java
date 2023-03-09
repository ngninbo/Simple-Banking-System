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

    public static List<MenuItem> start() {
        return List.of(CREATE_AN_ACCOUNT, LOG_INTO_ACCOUNT, EXIT);
    }

    public static List<MenuItem> account() {
        return List.of(BALANCE, ADD_INCOME, DO_TRANSFER, CLOSE_ACCOUNT, LOG_OUT, EXIT);
    }

    public String capitalize() {
        String name = replaceUnderscore();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    private String replaceUnderscore() {
        return name().replace("_", " ");
    }
}

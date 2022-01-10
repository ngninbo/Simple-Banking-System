package banking.util;

/**
 * This class provides all SQL Queries needed by the repository
 * @see banking.repository.CardRepository
 * @author Beauclair Dongmo Ngnintedem Beauclair
 */
public class SqlQuery {

    public static final String CREATE_TABLE_CARD = "BEGIN TRANSACTION;\n" +
            "CREATE TABLE IF NOT EXISTS 'card'('id' INTEGER PRIMARY KEY, " +
            "'number' TEXT, " +
            "'pin' TEXT, " +
            "'balance' INTEGER);\n" +
            "COMMIT;";

    public static final String INSERT_INTO_CARD_NUMBER_PIN_BALANCE_VALUES = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?);";
    public static final String SELECT_FROM_CARD_WHERE_NUMBER = "SELECT * FROM card WHERE number = ?";
    public static final String SELECT_BALANCE_FROM_CARD_WHERE_NUMBER = "SELECT balance FROM card WHERE number = ?";
    public static final String UPDATE_CARD_INCREASING_BALANCE_WHERE_NUMBER = "UPDATE card SET balance = (balance + ?) WHERE number = ?";
    public static final String UPDATE_CARD_DECREASING_BALANCE_WHERE_NUMBER = "UPDATE card SET balance = (balance - ?) WHERE number = ?";
    public static final String DELETE_CARD_BY_NUMBER = "DELETE FROM card WHERE number = ?";

}

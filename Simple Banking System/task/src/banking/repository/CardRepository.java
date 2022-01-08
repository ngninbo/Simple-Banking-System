package banking.repository;

import banking.models.Card;
import banking.util.SqlQuery;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Optional;

/**
 * This class provides the CRUD methods
 * @author Beauclair Dongmo Ngnintedem
 */
public class CardRepository {

    private final SQLiteDataSource dataSource;
    private PreparedStatement statement;

    private CardRepository(String filename) {
        String url = "jdbc:sqlite:" + filename;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        createTableCard();
    }

    public static CardRepository createCardRepository(String filename) {
        return new CardRepository(filename);
    }

    /**
     * Add new card in the database
     * @param card Card entity
     */
    public void saveCard(Card card) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SqlQuery.INSERT_INTO_CARD_NUMBER_PIN_BALANCE_VALUES);
            statement.setString(1, card.getCreditCardNumber());
            statement.setString(2, card.getPin());
            statement.setLong(3, card.getBalance());

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create card table in the database if not exists
     */
    public void createTableCard() {

        try (Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();

            statement.executeUpdate(SqlQuery.CREATE_TABLE_CARD);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Read card by given number from database
     * @param cardNumber card number
     * @return card entity
     */
    public Optional<Card> findCardByNumber(String cardNumber) {

        Card card = null;
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(SqlQuery.SELECT_FROM_CARD_WHERE_NUMBER);
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                card = new Card(resultSet.getString("number"),
                        resultSet.getString("pin"), resultSet.getInt("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card != null ? Optional.of(card) : Optional.empty();
    }

    /**
     * Read balance value of card by given number and pin
     * @param number Card number
     * @return account balance
     */
    public long readCardByNumberAndReturnBalance(String number) {

        long balance = 0L;

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(SqlQuery.SELECT_BALANCE_FROM_CARD_WHERE_NUMBER);
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                balance = resultSet.getLong("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    /**
     * Add income to given account number
     * @param cardNumber Card number
     * @param income money to deposit to the account
     */
    public void updateCardByNumber(String cardNumber, long income) {

        try (Connection connection = dataSource.getConnection()){

            // Disable auto commit mode
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(SqlQuery.UPDATE_CARD_INCREASE_BALANCE_WHERE_NUMBER);
            statement.setLong(1, income);
            statement.setString(2, cardNumber);

            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param currentCardNumber card number of current account
     * @param targetCardNumber card number of target account
     * @param amount amount of money to transfer to @param targetCardNumber
     */
    public void updateCardsByNumbers(String currentCardNumber, String targetCardNumber, long amount) {

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);
            // connection.setSavepoint();
            statement = connection.prepareStatement(SqlQuery.UPDATE_CARD_INCREASE_BALANCE_WHERE_NUMBER);
            statement.setLong(1, amount);
            statement.setString(2, targetCardNumber);
            statement.executeUpdate();

            // connection.setSavepoint();
            statement = connection.prepareStatement(SqlQuery.UPDATE_CARD_DECREASE_BALANCE_WHERE_NUMBER);
            statement.setLong(1, amount);
            statement.setString(2, currentCardNumber);
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete account from the database
     * @param cardNumber card number
     */
    public void deleteCardByCardNumber(String cardNumber) {

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(SqlQuery.DELETE_CARD_BY_NUMBER);
            statement.setString(1, cardNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Card> findCardByNumberAndPin(String cardNumber, String pin) {

        Optional<Card> card = findCardByNumber(cardNumber);

        return pin.equals(card.orElseThrow().getPin()) ? card : Optional.empty();
    }
}
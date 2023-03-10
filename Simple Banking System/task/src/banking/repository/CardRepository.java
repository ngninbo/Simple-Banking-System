package banking.repository;

import banking.models.Card;
import banking.util.PropertiesLoader;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

/**
 * This class provides the CRUD methods
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class CardRepository {

    private final SQLiteDataSource dataSource;
    private PreparedStatement statement;

    private final Properties properties;

    {
        properties = PropertiesLoader.getInstance().statements();
    }

    private CardRepository(String filename) {
        String url = "jdbc:sqlite:" + filename;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        createTableCard();
    }

    public static CardRepository init(String filename) {
        return new CardRepository(filename);
    }

    /**
     * Save card item
     *
     * @param card Card object
     */
    public void saveCard(Card card) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection
                    .prepareStatement(properties.getProperty("INSERT_INTO_CARD_NUMBER_PIN_BALANCE_VALUES"));
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
     * Create table card if not exists
     */
    public void createTableCard() {

        try (Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();

            statement.executeUpdate(properties.getProperty("CREATE_TABLE_CARD"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Find card by given number
     *
     * @param cardNumber card number
     * @return Optional card object
     */
    public Optional<Card> findCardByNumber(String cardNumber) {

        Card card = null;
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(properties.getProperty("SELECT_FROM_CARD_WHERE_NUMBER"));
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                card = Card.createCard(resultSet.getString("number"),
                        resultSet.getString("pin"), resultSet.getInt("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(card);
    }

    /**
     * Find card by given number and return balance value
     *
     * @param number credit card number
     * @return account balance
     */
    public long findCardByNumberAndReturnBalance(String number) {

        long balance = 0L;

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(properties.getProperty("SELECT_BALANCE_FROM_CARD_WHERE_NUMBER"));
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
     * Update balance of card by given number increasingly
     *
     * @param cardNumber credit card number
     * @param income     amount of money to be added to current account balance
     */
    public void updateCardByNumber(String cardNumber, long income) {

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(properties.getProperty("UPDATE_CARD_INCREASING_BALANCE_WHERE_NUMBER"));
            statement.setLong(1, income);
            statement.setString(2, cardNumber);

            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update balance of card by given number increasingly in target and decreasingly in current account
     *
     * @param currentCardNumber card number of current account
     * @param targetCardNumber  card number of target account
     * @param amount            amount of money to be added/subtracted to/from target/current account balance
     */
    public void updateCardsByNumbers(String currentCardNumber, String targetCardNumber, long amount) {

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);
            // connection.setSavepoint();
            statement = connection.prepareStatement(
                    properties.getProperty("UPDATE_CARD_INCREASING_BALANCE_WHERE_NUMBER"));
            statement.setLong(1, amount);
            statement.setString(2, targetCardNumber);
            statement.executeUpdate();

            // connection.setSavepoint();
            statement = connection.prepareStatement(
                    properties.getProperty("UPDATE_CARD_DECREASING_BALANCE_WHERE_NUMBER"));
            statement.setLong(1, amount);
            statement.setString(2, currentCardNumber);
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete card by given number
     *
     * @param cardNumber card number
     */
    public void deleteCardByCardNumber(String cardNumber) {

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(properties.getProperty("DELETE_CARD_BY_NUMBER"));
            statement.setString(1, cardNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find card by given number and pin
     *
     * @param cardNumber card number
     * @param actualPin  PIN entered by user
     * @return Optional Card object
     */
    public Optional<Card> findCardByNumberAndPin(String cardNumber, String actualPin) {

        Optional<Card> card = findCardByNumber(cardNumber);

        return card.isEmpty() ? Optional.empty() :
                (isPinValid(actualPin, card.get().getPin()) ? card : Optional.empty());
    }

    private boolean isPinValid(String actualPin, String exceptedPin) {
        return actualPin.equals(exceptedPin);
    }
}
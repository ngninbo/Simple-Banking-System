package banking.repository;

import banking.builder.CardFactory;
import banking.models.Card;
import banking.util.PropertiesLoader;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import static banking.repository.UpdateStrategy.DECREASING;
import static banking.repository.UpdateStrategy.INCREASING;

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

    public static CardRepository of(String filename) {
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

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(properties.getProperty("SELECT_FROM_CARD_WHERE_NUMBER"));
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                Card card = CardFactory.createCard(resultSet.getString("number"),
                        resultSet.getString("pin"), resultSet.getInt("balance"));
                return Optional.of(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Find card by given number and return balance value
     *
     * @param number credit card number
     * @return account balance
     */
    public long findBalanceByCardNumber(String number) {

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(properties.getProperty("SELECT_BALANCE_FROM_CARD_WHERE_NUMBER"));
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                return resultSet.getLong("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Update balance of card by given number increasingly
     *
     * @param cardNumber credit card number
     * @param income     amount of money to be added to current account balance
     */
    public void addIncome(String cardNumber, long income) {

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);
            executeUpdate(income, cardNumber, connection, INCREASING);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update balance of card by given number increasingly in target and decreasingly in current account
     * @param amount    amount of money to be transferred from current account to target
     * @param source    card number of current account
     * @param target    card number of target account
     */
    public void transfer(long amount, String source, String target) {

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);
            // connection.setSavepoint();
            executeUpdate(amount, target, connection, INCREASING);

            // connection.setSavepoint();
            executeUpdate(amount, source, connection, DECREASING);

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
     * @param pin  PIN entered by user
     * @return Optional Card object
     */
    public Optional<Card> findCardByNumberAndPin(String cardNumber, String pin) {

        Optional<Card> card = findCardByNumber(cardNumber);

        return card.isPresent() ? (pin.equals(card.get().getPin()) ? card : Optional.empty()) : Optional.empty();
    }

    private void executeUpdate(long amount, String cardNumber,
                               Connection connection,
                               UpdateStrategy updateStrategy) throws SQLException {
        final String query = String.join("_", "UPDATE_CARD", updateStrategy.name(), "BALANCE_WHERE_NUMBER");
        statement = connection.prepareStatement(properties.getProperty(query));
        statement.setLong(1, amount);
        statement.setString(2, cardNumber);
        statement.executeUpdate();
    }
}
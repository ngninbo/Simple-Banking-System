package banking.repository;

import banking.builder.CreditCardBuilder;
import banking.domain.StatementFactory;
import banking.models.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

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

    protected StatementFactory statementFactory = new StatementFactory();

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
            PreparedStatement statement = connection.prepareStatement(statementFactory.get(
                    "INSERT_INTO_CARD_NUMBER_PIN_BALANCE_VALUES",
                    card.getCreditCardNumber(),
                    card.getPin(),
                    card.getBalance()));

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

            statement.executeUpdate(statementFactory.get("CREATE_TABLE_CARD"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Find card by given number
     *
     * @param cardNumber card number
     * @return {@link Card} or null if not present
     */
    public Card findByNumber(String cardNumber) {

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(statementFactory.get("SELECT_FROM_CARD_WHERE_NUMBER", cardNumber));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return CreditCardBuilder.init()
                        .withCardNumber(resultSet.getString("number"))
                        .withPin(resultSet.getString("pin"))
                        .withBalance(resultSet.getInt("balance"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            statement = connection.prepareStatement(statementFactory.get("DELETE_CARD_BY_NUMBER", cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(long amount, String cardNumber,
                               Connection connection,
                               UpdateStrategy updateStrategy) throws SQLException {
        final String query = String.join("_", "UPDATE_CARD", updateStrategy.name(), "BALANCE_WHERE_NUMBER");
        statement = connection.prepareStatement(statementFactory.get(query, String.valueOf(amount), cardNumber));
        statement.executeUpdate();
    }
}
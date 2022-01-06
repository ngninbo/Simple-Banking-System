package banking.repository;

import banking.models.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

/**
 * This class provides the CRUD methods
 * @author Beauclair Dongmo Ngnintedem
 */
public class CardRepository {

    private final SQLiteDataSource dataSource;
    private PreparedStatement statement;
    private String query;
    private Card card;
    private long balance;

    private CardRepository(String filename) {
        String url = "jdbc:sqlite:" + filename;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        createCardTable();
    }

    public static CardRepository createCardRepository(String filename) {
        return new CardRepository(filename);
    }

    /**
     * Add new card in the database
     * @param card Card entity
     */
    public void saveCard(Card card) {

        query = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?);";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
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
    public void createCardTable() {

        query = "BEGIN TRANSACTION;\n" +
                "CREATE TABLE IF NOT EXISTS 'card'('id' INTEGER PRIMARY KEY, " +
                "'number' TEXT, " +
                "'pin' TEXT, " +
                "'balance' INTEGER);\n" +
                "COMMIT;";

        try (Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();

            statement.executeUpdate(query);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Read card by given number from database
     * @param number card number
     * @return card entity
     */
    public Card getCardByNumber(String number) {

        query = "SELECT * FROM card WHERE number= ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                card = new Card(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getInt("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    /**
     * Read balance value of card by given number and pin
     * @param number Card number
     * @return account balance
     */
    public long getBalance(String number) {
        query = "SELECT balance FROM card WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(query);
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
    public void setBalance(String cardNumber, long income) {

        query = "UPDATE card SET balance = (balance + ?) WHERE number = ?";

        try (Connection connection = dataSource.getConnection()){

            // Disable auto commit mode
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
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
    public void setBalance(String currentCardNumber, String targetCardNumber, long amount) {

        query = "UPDATE card SET balance = (balance + ?) WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {

            // Disable auto commit mode
            connection.setAutoCommit(false);
            // connection.setSavepoint();
            statement = connection.prepareStatement(query);
            statement.setLong(1, amount);
            statement.setString(2, targetCardNumber);
            statement.executeUpdate();

            // connection.setSavepoint();
            query = "UPDATE card SET balance = (balance - ?) WHERE number = ?";
            statement = connection.prepareStatement(query);
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
    public void deleteCard(String cardNumber) {
        query = "DELETE FROM card WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setString(1, cardNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
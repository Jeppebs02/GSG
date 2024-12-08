package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.DataAccessException;

/**
 * The DBConnection class handles establishing a single shared connection 
 * to a SQL Server database, as well as managing database transactions.
 * 
 * <p>This class uses the Singleton pattern to ensure only one instance 
 * of the database connection exists at any time.</p>
 */
public class DBConnection {

    private Connection connection = null; 
    private static DBConnection dBConnection = new DBConnection(); 

    private static final String DBNAME = "DMA-CSD-V24_10519150";
    private static final String SERVERNAME = "hildur.ucn.dk";
    private static final String PORTNUMBER = "1433";
    private static final String USERNAME = "DMA-CSD-V24_10519150";
    private static final String PASSWORD = "Password1!";

    /**
     * Private constructor that establishes a connection to the database 
     * when the class is instantiated. The connection details (server name, 
     * port number, database name, username, and password) are defined as constants.
     */
    private DBConnection() {
        String urlString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false", SERVERNAME, PORTNUMBER,
                DBNAME);
        try {
            connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection to DataBase failed");
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of DBConnection. If the instance 
     * does not yet exist, it creates a new one.
     *
     * @return The single DBConnection instance.
     */
    public static synchronized DBConnection getInstance() {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    /**
     * Gets the current active database connection.
     *
     * @return A Connection object representing the established database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Begins a database transaction by turning off auto-commit mode.
     * 
     * @throws DataAccessException If starting the transaction fails.
     */
    public synchronized void startTransaction() throws DataAccessException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataAccessException("Could not start transaction.", e);
        }
    }

    /**
     * Commits the current database transaction.
     * 
     * @throws DataAccessException If committing the transaction fails.
     */
    public synchronized void commitTransaction() throws DataAccessException {
        try {
            comTra();
        } catch (SQLException e) {
            throw new DataAccessException("Could not commit transaction", e);
        }
    }

    /**
     * Private helper method that handles the actual transaction commit.
     * It commits the transaction and then resets the connection to auto-commit mode.
     *
     * @throws SQLException If an error occurs during commit or setting auto-commit.
     */
    private void comTra() throws SQLException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Rolls back the current database transaction.
     * 
     * @throws DataAccessException If rolling back the transaction fails.
     */
    public synchronized void rollbackTransaction() throws DataAccessException {
        try {
            rolTra();
        } catch (SQLException e) {
            throw new DataAccessException("Could not rollback transaction", e);
        }
    }

    /**
     * Private helper method that handles the actual transaction rollback.
     * It rolls back the transaction and then resets the connection to auto-commit mode.
     *
     * @throws SQLException If an error occurs during rollback or setting auto-commit.
     */
    private void rolTra() throws SQLException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Executes the given PreparedStatement (typically an INSERT) and returns the 
     * generated identity value (primary key) from the database.
     * 
     * <p>This method starts a new transaction, executes the prepared statement, 
     * retrieves the generated key, and commits the transaction if successful. 
     * If any exception occurs, it rolls back the transaction.</p>
     * 
     * @param ps The PreparedStatement to execute, which should be configured for returning generated keys.
     * @return The generated key (primary key), or -1 if no key was generated.
     * @throws SQLException If a database access error occurs.
     * @throws DataAccessException If starting, committing, or rolling back the transaction fails.
     */
    public int executeSqlInsertWithIdentityPS(PreparedStatement ps) throws SQLException, DataAccessException {
        int key = -1;
        try {
            // Start the transaction
            startTransaction();

            // Execute insert statement
            int res = ps.executeUpdate();
            if (res > 0) {
                // Get generated keys
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        key = rs.getInt(1);
                    }
                }
            }

            // Commit transaction
            commitTransaction();
        } catch (SQLException e) {
            // Rollback transaction if there is an exception
            rollbackTransaction();
            e.printStackTrace();
            throw e;
        }
        return key;
    }

    /**
     * Executes a SELECT query using the provided PreparedStatement and returns a ResultSet.
     * 
     * <p>This method starts a transaction before executing the query and commits 
     * afterwards if successful. If an error occurs during the query execution, the 
     * transaction is rolled back.</p>
     *
     * @param ps The PreparedStatement representing the SELECT query to be executed.
     * @return The ResultSet obtained from executing the query.
     * @throws Exception If any error occurs during query execution or transaction handling.
     */
    public ResultSet getResultSetWithPS(PreparedStatement ps) throws Exception {
        ResultSet rs = null;

        try {
            startTransaction();

            rs = ps.executeQuery();

            commitTransaction();

        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
            throw e;
        }
        return rs;
    }

}

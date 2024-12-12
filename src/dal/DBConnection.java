package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.DataAccessException;

/**
 * The {@code DBConnection} class manages a single connection to a SQL Server
 * database using the Singleton pattern. It also provides methods to handle
 * database transactions (start, commit, rollback) and execute queries that
 * require generated keys.
 * <p>
 * This ensures that only one {@code Connection} object is maintained throughout
 * the application's lifecycle, preventing unnecessary overhead and resource
 * usage.
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
	 * Private constructor that establishes a connection to the database when the
	 * class is instantiated. The connection details (server name, port number,
	 * database name, username, and password) are defined as constants.
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
	 * Returns the singleton instance of {@code DBConnection}. If the instance does
	 * not yet exist, it creates a new one.
	 *
	 * @return the single {@code DBConnection} instance.
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
	 * @return a {@link Connection} object representing the established database
	 *         connection.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Begins a database transaction by turning off auto-commit mode.
	 * 
	 * @throws DataAccessException if starting the transaction fails.
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
	 * @throws DataAccessException if committing the transaction fails.
	 */
	public synchronized void commitTransaction() throws DataAccessException {
		try {
			comTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not commit transaction", e);
		}
	}

	/**
	 * Rolls back the current database transaction.
	 * 
	 * @throws DataAccessException if rolling back the transaction fails.
	 */
	public synchronized void rollbackTransaction() throws DataAccessException {
		try {
			rolTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not rollback transaction", e);
		}
	}

	/**
	 * Private helper method that handles the actual transaction rollback. It rolls
	 * back the transaction and then resets the connection to auto-commit mode.
	 *
	 * @throws SQLException if an error occurs during rollback or setting
	 *                      auto-commit.
	 */
	private void rolTra() throws SQLException {
		try {
			connection.rollback();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Private helper method that handles the actual transaction commit. It commits
	 * the transaction and then resets the connection to auto-commit mode.
	 *
	 * @throws SQLException if an error occurs during commit or setting auto-commit.
	 */
	private void comTra() throws SQLException {
		try {
			connection.commit();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Executes the given {@link PreparedStatement} (typically an INSERT) and
	 * returns the generated identity value (primary key) from the database.
	 * <p>
	 * This method starts a new transaction, executes the prepared statement,
	 * retrieves the generated key, and commits the transaction if successful. If
	 * any exception occurs, it rolls back the transaction.
	 * 
	 * @param ps the PreparedStatement to execute, which should be configured for
	 *           returning generated keys.
	 * @return the generated key (primary key), or -1 if no key was generated.
	 * @throws SQLException        if a database access error occurs.
	 * @throws DataAccessException if starting, committing, or rolling back the
	 *                             transaction fails.
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
	 * Executes an INSERT statement using the provided PreparedStatement, but does
	 * not return a generated key. This method is similar to
	 * {@link #executeSqlInsertWithIdentityPS(PreparedStatement)} except it doesn't
	 * retrieve generated keys.
	 *
	 * @param ps the PreparedStatement to execute.
	 * @return the number of rows affected by the INSERT.
	 * @throws SQLException        if a database access error occurs.
	 * @throws DataAccessException if starting, committing, or rolling back the
	 *                             transaction fails.
	 */
	public int executeSqlInsertPS(PreparedStatement ps) throws SQLException, DataAccessException {
		int key = -1;
		try {
			// Start the transaction
			startTransaction();

			// Execute insert statement
			key = ps.executeUpdate();

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
	 * Executes a SELECT query using the provided PreparedStatement and returns a
	 * {@link ResultSet}.
	 * <p>
	 * This method starts a transaction before executing the query and commits
	 * afterwards if successful. If an error occurs during the query execution, the
	 * transaction is rolled back.
	 *
	 * @param ps the PreparedStatement representing the SELECT query to be executed.
	 * @return the ResultSet obtained from executing the query.
	 * @throws Exception if any error occurs during query execution or transaction
	 *                   handling.
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

package dal;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dal.DataAccessException;

public class DBConnection {

	private Connection connection = null; 
	private static DBConnection dBConnection = new DBConnection(); 

	private static final String DBNAME = "DMA-CSD-V24_10519150";
	private static final String SERVERNAME = "hildur.ucn.dk";
	private static final String PORTNUMBER = "1433";
	private static final String USERNAME = "DMA-CSD-V24_10519150";
	private static final String PASSWORD = "Password1!";

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
	
	public static DBConnection getInstance() {
		if (dBConnection == null) {
			dBConnection = new DBConnection();
		}
		return dBConnection;
	}

	public Connection getConnection() {
		return connection;
	}

	public int executeSqlInsertWithIdentity(String sql) throws SQLException {
		int key = -1;
		try (Statement s = connection.createStatement()) {
			int res = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (res > 0) {
				ResultSet rs = s.getGeneratedKeys();
				if (rs != null && rs.next()) {
					key = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;
	}
	
	public void startTransaction() throws DataAccessException {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException("Could not start transaction.", e);
		}
	}
	
	public void commitTransaction() throws DataAccessException {
		try {
			comTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not commit transaction", e);
		}
	}

	private void comTra() throws SQLException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}
	
	public void rollbackTransaction() throws DataAccessException {
		try {
			rolTra();
		} catch (SQLException e) {
			throw new DataAccessException("Could not rollback transaction", e);
		}
	}

	private void rolTra() throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}
	
	public int executeSqlInsertWithIdentityPS(PreparedStatement ps) throws SQLException, DataAccessException {
	    int key = -1;
	    try {
	        // Start the transaction
	        startTransaction();

	        // Execute insert statement
	        int res = ps.executeUpdate();
	        if (res > 0) {
	            // Get generated keys
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs != null && rs.next()) {
	                key = rs.getInt(1);
	            }
	        }

	        // commit if everything is good :)
	        commitTransaction();
	    } catch (SQLException e) {
	        // If there's an exception, roll back the transaction
	        rollbackTransaction();
	        e.printStackTrace();
	        throw e; 
	    }
	    return key;
	}
}

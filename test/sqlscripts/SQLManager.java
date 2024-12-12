package sqlscripts;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dal.DBConnection;
import dal.DataAccessException;

/**
 * The SqlScriptExecutor class is responsible for reading SQL scripts from a file
 * and executing them using the DBConnection class.
 */
public class SQLManager {

    /**
     * Executes a SQL script from the specified file path.
     *
     * @param filePath The path to the .sql file containing the SQL script.
     * @throws DataAccessException If an error occurs while reading the file or executing the SQL commands.
     */
	
	
    public static void executeScript(String filePath) throws DataAccessException {
    	// String builder to make working with strings easier :)
        StringBuilder scriptBuilder = new StringBuilder();

        // Read the SQL file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Ignore lines that are comments
                String trimmedLine = line.trim();
                if (trimmedLine.startsWith("--") || trimmedLine.startsWith("//") || trimmedLine.isEmpty()) {
                    continue;
                }
                scriptBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
        	// Error if something goes wrong
            throw new DataAccessException("Error reading SQL script file: " + filePath, e);
        }

        // Split the script by "GO"
        String[] commands = scriptBuilder.toString().split("(?i)\\bGO\\b");

        // Get the database connection
        Connection connection = DBConnection.getInstance().getConnection();

        // Execute each statement
        try (Statement stmt = connection.createStatement()) {
            for (String command : commands) {
                String sql = command.trim();
                if (sql.isEmpty()) {
                    continue;
                }
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error executing SQL script: " + filePath, e);
        }
    }

    /**
     * Executes multiple SQL scripts provided as file paths.
     *
     * @param filePaths - An array of file paths to the .sql files containing SQL scripts.
     * @throws DataAccessException If an error occurs while reading the files or executing the SQL commands.
     */
    public static void executeScripts(String[] filePaths) throws DataAccessException {
        for (String filePath : filePaths) {
            executeScript(filePath);
        }
    }
    
    
    /**
     * Teardown method to be used in tests.
     * It first resets all tables, then populates them with test data.
     *
     */
    public static void tearDown() throws Exception {
    	executeScript("SQL_Scripts/RESET_ALL_TABLES.sql");
    	executeScript("SQL_Scripts/CREATE_TEST_DATA.sql");
    }
    
    
}

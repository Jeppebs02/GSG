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
     * Executes the SQL script located at the given file path.
     *
     * @param filePath The path to the .sql file.
     * @throws IOException         If an error occurs while reading the file.
     * @throws DataAccessException If a database access error occurs.
     */
    public void executeSqlScript(String filePath) throws IOException, DataAccessException {
        String script = readSqlFile(filePath);
        String[] statements = splitSqlScript(script);

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();

        try {
            dbConnection.startTransaction();

            for (String statement : statements) {
                statement = statement.trim();
                if (statement.isEmpty()) {
                    continue;
                }

                executeStatement(connection, statement);
            }

            dbConnection.commitTransaction();
            System.out.println("SQL script executed successfully.");
        } catch (SQLException | DataAccessException e) {
            try {
                dbConnection.rollbackTransaction();
            } catch (DataAccessException rollbackEx) {
                System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
            }
            System.err.println("Error executing SQL script: " + e.getMessage());
            throw new DataAccessException("Error executing SQL script.", e);
        }
    }

    /**
     * Reads the entire SQL file into a single String.
     *
     * @param filePath The path to the .sql file.
     * @return The content of the SQL file as a String.
     * @throws IOException If an error occurs while reading the file.
     */
    private String readSqlFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip comments
                String trimmedLine = line.trim();
                if (trimmedLine.startsWith("--") || trimmedLine.startsWith("//") || trimmedLine.startsWith("/*")) {
                    continue;
                }
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Splits the SQL script into individual statements using 'GO' as the separator.
     *
     * @param script The full SQL script.
     * @return An array of individual SQL statements.
     */
    private String[] splitSqlScript(String script) {
        // Split by GO command (case-insensitive) on a line by itself
        return script.split("(?i)^GO$", java.util.regex.Pattern.MULTILINE);
    }

    /**
     * Executes a single SQL statement.
     *
     * @param connection The active database connection.
     * @param sql        The SQL statement to execute.
     * @throws SQLException If a database access error occurs.
     */
    private void executeStatement(Connection connection, String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Executed: " + getFirstLine(sql));
        }
    }

    /**
     * Retrieves the first line of the SQL statement for logging purposes.
     *
     * @param sql The full SQL statement.
     * @return The first line of the SQL statement.
     */
    private String getFirstLine(String sql) {
        String[] lines = sql.split("\n");
        return lines.length > 0 ? lines[0].trim() : sql.trim();
    }

    /**
     * Main method for executing the SQL script.
     *
     * @param args Command-line arguments. Expects a single argument: path to the .sql file.
     */
    public static void main(String[] args) {
        // Check if the file path is provided
        if (args.length != 1) {
            System.err.println("Usage: java dal.SqlScriptExecutor <path_to_sql_script>");
            System.exit(1);
        }

        String filePath = args[0];
        SQLManager executor = new SQLManager();

        try {
            executor.executeSqlScript(filePath);
        } catch (IOException e) {
            System.err.println("Error reading the SQL file: " + e.getMessage());
        } catch (DataAccessException e) {
            System.err.println("Database access error: " + e.getMessage());
        }
    }
}

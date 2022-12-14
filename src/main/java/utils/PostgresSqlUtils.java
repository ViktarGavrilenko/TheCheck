package utils;

import config.ConfigurationProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class PostgresSqlUtils {
    private static final Logger LOG = Logger.getLogger(ConfigurationProperties.class);

    protected static final Properties configProperties =
            ConfigurationProperties.createProperties("application.properties");
    private static final String DB_HOST = configProperties.getProperty("dbHost");
    private static final String DB_PORT = configProperties.getProperty("dbPort");
    private static final String DB_USER = configProperties.getProperty("dbUser");
    private static final String DB_PASS = configProperties.getProperty("dbPass");
    private static final String DB_NAME = configProperties.getProperty("dbName");

    public static final String CONNECTION_FAILED = "Connection failed...";
    public static final String SQL_QUERY_FAILED = "Sql query failed...";

    private static Connection connection;

    private static Connection getDbConnection() {
        if (connection != null) {
            return connection;
        } else {
            String connectionString = String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(connectionString, DB_USER, DB_PASS);
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                LOG.error(CONNECTION_FAILED + e);
                throw new IllegalArgumentException(CONNECTION_FAILED, e);
            }
        }
    }

    public static void sendSqlQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
        }
    }

    public static ResultSet sendSelectQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}
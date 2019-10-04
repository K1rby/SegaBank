package dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistanceManager {

    private static Connection connection;
    private static final String PROPS_FILE = "resources/db.properties";

    private PersistanceManager(){} //Prevents initialization

    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {

        if (connection == null || !connection.isValid(5)) {

            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(PROPS_FILE)) {
                props.load(fis);
            }

            String driverClass = props.getProperty("jdbc.class.driver");
            String dbUrl = props.getProperty("jdbc.db.url");
            String login = props.getProperty("jdbc.db.login");
            String pwd = props.getProperty("jdbc.db.pwd");

            Class.forName(driverClass);
            connection = DriverManager.getConnection(dbUrl, login, pwd);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && connection.isValid(2)) {
            connection.close();
        }
    }
}

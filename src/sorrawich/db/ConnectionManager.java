package sorrawich.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection createConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
//        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        System.out.println("Driver class registered successfully");
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}

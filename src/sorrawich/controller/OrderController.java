package sorrawich.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import sorrawich.db.ConnectionManager;
import sorrawich.model.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderController {
    private Connection connection;

    public OrderController(String user, String password) throws ClassNotFoundException, SQLException {
        this.connection = ConnectionManager
                .createConnection(
                    'jdbc:derby://localhost:1527/homework',
                    user,
                    password
                );
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE orders (" +
                "   orderId VARCHAR(6)," +
                "   productName VARCHAR(50)," +
                "   price DECIMAL(6,2)" +
                "   status VARCHAR(7)" +
                "   PRIMARY KEY(orderId)" +
                ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Created table success");
    }

    public void dropTable() throws SQLException {
        String sql = "DROP TABLE orders";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Drop table success");
    }

    public int insertOrder(Order order) throws SQLException {
        String sql = String.format("INSERT INTO orders(orderId, productName, price, status) VALUES ('%s', '%s', '%.2f', '%s')",
                order.getOrderId(),
                order.getProductName(),
                order.getPrice(),
                order.getStatus()
        );

        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(sql);
        System.out.println("Insert success");
        return result;
    }

    public ArrayList<Order> findFromOrderId(String orderId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE orderId = '" + orderId + "'");
        while(resultSet.next()) {
            Order order = new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            orders.add(order);
        }
        return orders;
    }

    private Object execute(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        Boolean hasResultSet = statement.execute(sql);
        if(hasResultSet) {
            ResultSet resultSet = statement.getResultSet();
            ArrayList<Order> orders = new ArrayList<>();
            while(resultSet.next()) {
                Order course = new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4)
                );
                orders.add(course);
            }
            return orders;
        } else {
            return statement.getUpdateCount();
        }
    }

    public int truncateOrder() throws SQLException {
        Statement statement = connection.createStatement();
        Integer result = statement.executeUpdate("TRUNCATE TABLE orders");
        System.out.println("Truncated");
        return result;
    }

    public void closeCourseConnection() throws SQLException {
        ConnectionManager.closeConnection(connection);
    }


}

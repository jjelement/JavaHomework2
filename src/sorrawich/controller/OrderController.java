package sorrawich.controller;

import sorrawich.db.ConnectionManager;
import sorrawich.model.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderController {
    private Connection connection;

    public OrderController(String user, String password) throws ClassNotFoundException, SQLException {
        this.connection = ConnectionManager
                .createConnection(
                    "jdbc:derby://localhost:1527/homework",
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

        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("> Created table success");
    }

    public void dropTable() throws SQLException {
        String sql = "DROP TABLE orders";
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("> Drop table success");
    }

    public void insertOrder(Order order) throws SQLException {
        String sql = String.format("INSERT INTO orders(orderId, productName, price, status) VALUES ('%s', '%s', '%.2f', '%s')",
                order.getOrderId(),
                order.getProductName(),
                order.getPrice(),
                order.getStatus()
        );

//        Statement statement = this.connection.createStatement();
//        int result = statement.executeUpdate(sql);
        this.execute(sql);
        System.out.println("> Insert success");
    }
    
    public ArrayList<Order> findByOrderId(String orderId) throws SQLException {
//        ArrayList<Order> orders = new ArrayList<>();
//        Statement statement = this.connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE orderId = '" + orderId + "'");
//        while(resultSet.next()) {
//            Order order = new Order(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getDouble(3),
//                    resultSet.getString(4)
//            );
//            orders.add(order);
//        }
        return (ArrayList<Order>)this.execute("SELECT * FROM orders WHERE orderId = '" + orderId + "'");
    }

    public ArrayList<Order> findByProductName(String productName) throws SQLException {
        return (ArrayList<Order>)this.execute("SELECT * FROM orders WHERE productName = '" + productName + "'");
    }
    
    public ArrayList<Order> findByStatus(String status) throws SQLException {
        return (ArrayList<Order>)this.execute("SELECT * FROM orders WHERE status = '" + status + "'");
    }
    
    private Object execute(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
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
        Statement statement = this.connection.createStatement();
        Integer result = statement.executeUpdate("TRUNCATE TABLE orders");
        System.out.println("Truncated");
        return result;
    }

    public void closeCourseConnection() throws SQLException {
        ConnectionManager.closeConnection(this.connection);
    }


}

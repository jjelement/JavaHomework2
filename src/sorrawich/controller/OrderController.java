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
                "   productName VARCHAR(64)," +
                "   price DECIMAL(8,2)," +
                "   status VARCHAR(7)," +
                "   PRIMARY KEY(orderId)" +
                ")";

        this.execute(sql);
        System.out.println("> Created table success");
    }

    public void dropTable() throws SQLException {
        String sql = "DROP TABLE orders";
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("> Drop table success");
    }
    
    public void updateOrder(Order order) throws SQLException {
        String sql = String.format("UPDATE orders SET productName='%s', price='%.2f', status='%s' WHERE orderId='%s'",
                order.getProductName(),
                order.getPrice(),
                order.getStatus(),
                order.getOrderId()
        );
        
        int result = (int)this.execute(sql);
        System.out.println("> Update success");
    }

    public void insertOrder(Order order) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
        resultSet.next();
        Integer orderCount = resultSet.getInt(1);
        String orderId = String.format("%s", 100000+orderCount);
        String sql = String.format("INSERT INTO orders(orderId, productName, price, status) VALUES ('%s', '%s', %.2f, '%s')",
                orderId,
                order.getProductName(),
                order.getPrice(),
                order.getStatus()
        );
        
        this.execute(sql);
        System.out.println("> Insert success");
    }
    
    public void deleteOrder(Order order) throws SQLException {
        String sql = "DELETE orders WHERE orderId='" + order.getOrderId() + "'";
        this.execute(sql);
        System.out.println("> Delete success");
    }
    
    public ArrayList<Order> findByOrderId(String orderId) throws SQLException {
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

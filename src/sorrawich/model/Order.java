package sorrawich.model;

import java.sql.Timestamp;

public class Order {
    private String orderId;
    private String productName;
    private Double price;
    private String status;
    private static Integer orderCount = 0;

    public Order() {
    }

    public Order(String orderId, String productName, Double price, String status) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
        this.status = status;
    }

    public Order(String productName, Double price, String status) {
        this.productName = productName;
        this.price = price;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}

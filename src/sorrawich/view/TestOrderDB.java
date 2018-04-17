package sorrawich.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import sorrawich.controller.OrderController;
import sorrawich.model.Order;

public class TestOrderDB {
    private static OrderController orderController;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            orderController = new OrderController("sit", "sit");
            int input = -1;
            do {
                System.out.println("/================= Order Manager =================\\");
                System.out.println("|    Press 1: Search Order                        |");
                System.out.println("|    Press 2: Insert Order                        |");
                System.out.println("|    Press 3: Edit Order                          |");
                System.out.println("|    Press 4: Delete Order                        |");
                System.out.println("|    Press 5: Clear Order                         |");            
                System.out.println("|    Press 0: Exit                                |");
                System.out.println("\\_________________________________________________/");
                System.out.print("Enter your choice: ");
                input = sc.nextInt();

                switch(input) {
                    case 1: {
                        menuSearchOrder();
                        break;
                    }
                    case 2: {
                        menuInsertOrder();
                        break;
                    }
                    case 3: {
                        menuEditOrder();
                        break;
                    }
                    case 4: {
                        menuDeleteOrder();
                        break;
                    }
                    case 5: {
                        menuClearOrder();
                        break;
                    }
                }
            } while(input != 0);
        } catch(ClassNotFoundException | SQLException exception) {
            System.out.println(exception);
        }
    }

    private static void menuSearchOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("/================= Search Order =================\\");
        System.out.println("|    Press 1: Search By Order Id                 |");
        System.out.println("|    Press 2: Search By Product Name             |");
        System.out.println("|    Press 3: Search By Status                   |");
        System.out.println("|    Press 0: Back                               |");
        System.out.println("\\________________________________________________/");
        System.out.print("Enter your choice: ");
        int input = sc.nextInt();
        switch(input) {
            case 1: {
                System.out.print("Enter Order Id: ");
                System.out.println(orderController.findByOrderId(sc.next()).toString());
                break;
            }
            case 2: {
                System.out.print("Enter Product Name: ");
                System.out.println(orderController.findByOrderId(sc.next()).toString());
                break;
            }
            case 3: {
                System.out.println("/================= Search Order By Status =================\\");
                System.out.println("|    Press 1: Status Waiting                               |");
                System.out.println("|    Press 2: Status Shipped                               |");
                System.out.println("\\__________________________________________________________/");
                System.out.print("Enter your choice: ");
                System.out.println(orderController.findByStatus(sc.nextInt() == 1 ? "waiting" : "shipped").toString());
                break;
            }
        }
    }
    
    private static void menuInsertOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product Name: ");
        String productName = sc.next();
        
        System.out.print("Enter Price: ");
        Double price = sc.nextDouble();
        
        String status = "";
        do {
            System.out.print("Enter Shipping Status (waiting, shipped): ");
            status = sc.next();
        } while(!status.equals("waiting") && !status.equals("shipped"));
        
        Order order = new Order(productName, price, status);
        orderController.insertOrder(order);
    }
    
    private static void menuClearOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("/================= Clear Order =================\\");
        System.out.println("|    Press 1: Confirm                           |");
        System.out.println("|    Press 2: Back                              |");
        System.out.println("\\_______________________________________________/");
        System.out.print("Enter your choice: ");
        if(sc.nextInt() == 1) {
            orderController.truncateOrder();
        }
    }

    private static void menuEditOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Id: ");
        String orderId = sc.next();
        ArrayList<Order> orders = orderController.findByOrderId(orderId);
        if(orders.size() > 0) {
            Order order = orders.get(0);
            System.out.println("Old Product Name: " + order.getProductName());
            System.out.print("Enter New Product Name: ");
            String productName = sc.next();

            System.out.println("Old Price: " + order.getPrice());
            System.out.print("Enter New Price: ");
            Double price = sc.nextDouble();

            String status = "";
            System.out.print("Old status: " + order.getStatus());
            do {
                System.out.print("Enter Shipping Status (waiting, shipping): ");
                status = sc.next();
            } while(!status.equals("waiting") && !status.equals("shipping"));

            order.setProductName(productName);
            order.setPrice(price);
            order.setStatus(status);
            orderController.updateOrder(order);
        } else {
            System.out.println("Error: can't find order with order id: " + orderId);
        }
    }

    private static void menuDeleteOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Id: ");
        String orderId = sc.next();
        ArrayList<Order> orders = orderController.findByOrderId(orderId);
        if(orders.size() > 0) {
            orderController.deleteOrder(orders.get(0));
        } else {
            System.out.println("Error: can't find order with order id: " + orderId);
        }
    }
}

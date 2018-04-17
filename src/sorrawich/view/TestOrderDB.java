package sorrawich.view;

import java.sql.SQLException;
import java.util.Scanner;
import sorrawich.controller.OrderController;
import sorrawich.model.Order;

public class TestOrderDB {
    private static OrderController orderController;
    private static Scanner sc;
    
    public static void main(String[] args) {
        try {
            orderController = new OrderController("sit", "sit");
            int input = 1;

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

                switch(sc.nextInt()) {
                    case 1: {
                        menuSelectOrder();
                    }
                    case 2: {
                        menuInsertOrder();
                    }
                    case 3: {

                    }
                    case 4: {

                    }
                    case 5: {
                        menuClearOrder();
                    }
                }
            } while(input != 0);
        } catch(ClassNotFoundException | SQLException exception) {
            System.out.println(exception);
        }
    }

    private static void menuSelectOrder() throws SQLException {
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
                orderController.findByOrderId(sc.next()).toString();
            }
            case 2: {
                System.out.print("Enter Product Name: ");
                orderController.findByOrderId(sc.next()).toString();
            }
            case 3: {
                System.out.println("/================= Search Order By Status =================\\");
                System.out.println("|    Press 1: Status Waiting                               |");
                System.out.println("|    Press 2: Status Shipped                               |");
                System.out.println("\\__________________________________________________________/");
                System.out.print("Enter your choice: ");
                orderController.findByStatus(sc.nextInt() == 1 ? "waiting" : "shipped").toString();
            }
        }
    }
    
    private static void menuInsertOrder() throws SQLException {
        System.out.print("Enter Product Name: ");
        String productName = sc.next();
        
        System.out.print("Enter Price: ");
        Double price = sc.nextDouble();
        
        String status = "";
        do {
            System.out.print("Enter Shipping Status (waiting, shipping): ");
            status = sc.next();
        } while(!status.equals("waiting") && !status.equals("shipping"));
        
        Order order = new Order(productName, price, status);
        orderController.insertOrder(order);
    }
    
    public static void menuClearOrder() throws SQLException {
        System.out.println("/================= Clear Order =================\\");
        System.out.println("|    Press 1: Confirm                           |");
        System.out.println("|    Press 2: Back                              |");
        System.out.println("\\_______________________________________________/");
        if(sc.nextInt() == 1) {
            orderController.truncateOrder();
        }
    }
}

package com.stockify.platform;

import com.stockify.service.InventoryService;
import com.stockify.util.InputValidator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {
    private static final InventoryService inventoryService = new InventoryService();
    private static final AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (running.get()) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                String[] parts = input.split(" ");
                String command = parts[0].toUpperCase();

                try {
                    switch (command) {
                        case "ADD_PRODUCT":
                            if (parts.length != 5) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.addProduct(
                                    parts[1], parts[2], parts[3], parts[4]));
                            break;

                        case "UPDATE_PRICE":
                            if (parts.length != 3) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.updatePrice(
                                    parts[1], parts[2]));
                            break;

                        case "UPDATE_QUANTITY":
                            if (parts.length != 3) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.updateQuantity(
                                    parts[1], parts[2]));
                            break;

                        case "VIEW_PRODUCT":
                            if (parts.length != 2) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.viewProduct(parts[1]));
                            break;

                        case "REMOVE_PRODUCT":
                            if (parts.length != 2) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.removeProduct(parts[1]));
                            break;

                        case "LIST_PRODUCTS":
                            if (parts.length != 1) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.listProducts());
                            break;

                        case "SORT_PRODUCTS":
                            if (parts.length != 2) {
                                System.out.println("REQUEST_PATTERN_INVALID");
                                break;
                            }
                            System.out.println(inventoryService.sortProducts(parts[1]));
                            break;

                        case "EXIT":
                            System.out.println("Product Count: " +
                                    inventoryService.getProductCount());
                            System.out.println("Total Inventory Value: " +
                                    inventoryService.getTotalInventoryValue());
                            System.out.println("Goodbye!");
                            running.set(false);
                            scanner.close();
                            System.exit(0);
                            break;

                        default:
                            System.out.println("REQUEST_PATTERN_INVALID");
                    }
                } catch (Exception e) {
                    System.out.println("REQUEST_PATTERN_INVALID");
                }
            }
        }
    }

    // For testing purposes only
    public static void reset() {
        running.set(true);
    }
}
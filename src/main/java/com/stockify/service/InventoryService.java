package com.stockify.service;
import com.stockify.model.Product;
import com.stockify.util.InputValidator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryService {
    private final List<Product> products;
    private final List<Product> productsInOrderAdded;

    public InventoryService() {
        this.products = new ArrayList<>();
        this.productsInOrderAdded = new ArrayList<>();
    }

    public String addProduct(String productId, String productName, String quantityStr, String priceStr) {
        if (!InputValidator.isValidProductName(productName) ||
                !InputValidator.isPositiveInteger(quantityStr) ||
                !InputValidator.isPositiveInteger(priceStr)) {
            return "REQUEST_PATTERN_INVALID";
        }

        if (getProductById(productId) != null) {
            return "PRODUCT_ALREADY_EXISTS";
        }

        int quantity = Integer.parseInt(quantityStr);
        int price = Integer.parseInt(priceStr);

        Product product = new Product(productId, productName, quantity, price);
        products.add(product);
        productsInOrderAdded.add(product);
        return "SUCCESS";
    }

    public String updatePrice(String productId, String priceStr) {
        if (!InputValidator.isPositiveInteger(priceStr)) {
            return "REQUEST_PATTERN_INVALID";
        }

        Product product = getProductById(productId);
        if (product == null) {
            return "PRODUCT_NOT_FOUND";
        }

        product.setPrice(Integer.parseInt(priceStr));
        return "SUCCESS";
    }

    public String updateQuantity(String productId, String quantityStr) {
        if (!InputValidator.isPositiveInteger(quantityStr)) {
            return "REQUEST_PATTERN_INVALID";
        }

        Product product = getProductById(productId);
        if (product == null) {
            return "PRODUCT_NOT_FOUND";
        }

        product.setQuantity(Integer.parseInt(quantityStr));
        return "SUCCESS";
    }

    public String viewProduct(String productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "PRODUCT_NOT_FOUND";
        }

        return "Product ID: " + product.getProductId() + "\n" +
                "Name: " + product.getProductName() + "\n" +
                "Quantity: " + product.getQuantity() + "\n" +
                "Price: " + product.getPrice();
    }

    public String removeProduct(String productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "PRODUCT_NOT_FOUND";
        }

        products.remove(product);
        productsInOrderAdded.remove(product);
        return "SUCCESS";
    }

    public String listProducts() {
        if (productsInOrderAdded.isEmpty()) {
            return "NO_PRODUCTS_AVAILABLE";
        }

        StringBuilder sb = new StringBuilder();
        for (Product product : productsInOrderAdded) {
            sb.append(product.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    public String sortProducts(String byField) {
        if (!InputValidator.isValidSortField(byField)) {
            return "INVALID_SORT_FIELD";
        }

        List<Product> sortedProducts = new ArrayList<>(products);
        Comparator<Product> comparator = switch (byField) {
            case "id" -> Comparator.comparing(Product::getProductId);
            case "name" -> Comparator.comparing(Product::getProductName);
            case "quantity" -> Comparator.comparingInt(Product::getQuantity);
            case "price" -> Comparator.comparingInt(Product::getPrice);
            default -> null;
        };

        if (comparator != null) {
            sortedProducts.sort(comparator);
        }

        StringBuilder sb = new StringBuilder();
        for (Product product : sortedProducts) {
            sb.append(product.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    public int getProductCount() {
        return products.size();
    }

    public int getTotalInventoryValue() {
        return products.stream()
                .mapToInt(p -> p.getQuantity() * p.getPrice())
                .sum();
    }

    private Product getProductById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}

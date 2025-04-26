package com.stockify.util;

public class InputValidator {
    public static boolean isValidProductName(String name) {
        return name != null && !name.isEmpty() && !name.contains(" ") && !name.contains(";");
    }

    public static boolean isPositiveInteger(String value) {
        try {
            int num = Integer.parseInt(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidSortField(String field) {
        return field != null && (field.equals("id") || field.equals("name") ||
                field.equals("quantity") || field.equals("price"));
    }
}

package com.mycompany.assignment;
import java.util.Scanner;

public class Assignment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final double DRINK_PRICE = 15.50;

        System.out.print("Enter customer's name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter customer's age: ");
        int age = scanner.nextInt();

        System.out.print("Enter price of the meal: ");
        double mealPrice = scanner.nextDouble();

        System.out.print("Enter number of drinks ordered: ");
        int numberOfDrinks = scanner.nextInt();

        System.out.print("Is the customer a student? (true/false): ");
        boolean isStudent = scanner.nextBoolean();

        System.out.print("Enter service rating from 1 to 5: ");
        int serviceRating = scanner.nextInt();

        double drinksCost = numberOfDrinks * DRINK_PRICE;
        double subtotal = mealPrice + drinksCost;

        double discountAmount = 0.0;
        double studentDiscount = subtotal * 0.10;
        double seniorDiscount = subtotal * 0.05;

        if (isStudent && age >= 60) {
            if (studentDiscount > seniorDiscount) {
                discountAmount = studentDiscount;
            } else {
                discountAmount = seniorDiscount;
            }
        } else if (isStudent) {
            discountAmount = studentDiscount;
        } else if (age >= 60) {
            discountAmount = seniorDiscount;
        }

        double tipAmount = 0.0;
        if (serviceRating < 1 || serviceRating > 5) {
            System.out.println("Error: Rating must be between 1 and 5!");
        } else if (serviceRating == 4 || serviceRating == 5) {
            tipAmount = subtotal * 0.10;
        }

        double finalBill = subtotal - discountAmount + tipAmount;
        int wholeAmount = (int) finalBill;

        System.out.println("\nSMART RESTAURANT");
        System.out.println("Customer: " + customerName);
        System.out.println("Meal: $" + mealPrice);
        System.out.println("Drinks: $" + drinksCost);
        System.out.println("Discount: $" + discountAmount);
        System.out.println("Tip: $" + tipAmount);
        System.out.println("Final bill: $" + finalBill);
        System.out.println("Whole amount: $" + wholeAmount);

        scanner.close();
    }
}

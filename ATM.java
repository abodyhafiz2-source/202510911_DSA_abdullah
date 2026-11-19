package com.mycompany.atm;
import java.util.Scanner;

public class ATM {

    static final int CORRECT_PIN = 1234;
    static final int MAX_ATTEMPTS = 3;
    static final int MAX_TRANSACTIONS = 100;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] transactions = new String[MAX_TRANSACTIONS];
        int count = 0;
        double balance = 0;

        boolean loggedIn = false;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter PIN: ");
            int pin = readInt(sc);

            if (pin == CORRECT_PIN) {
                loggedIn = true;
                break;
            }

            attempts++;
            System.out.println("Wrong PIN. Attempts left: " + (MAX_ATTEMPTS - attempts));
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Program ended.");
            sc.close();
            return;
        }

        System.out.println("Login successful. Welcome!");

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("===== ATM MENU =====");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View transactions");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    System.out.println("Your balance is: " + balance);
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double dep = readDouble(sc);

                    if (dep <= 0) {
                        System.out.println("Error: invalid amount.");
                    } else if (count >= MAX_TRANSACTIONS) {
                        System.out.println("Error: transaction history is full.");
                    } else {
                        balance += dep;
                        transactions[count] = "Deposit: +" + dep;
                        count++;
                        System.out.println("Deposit successful. New balance: " + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double wd = readDouble(sc);

                    if (wd <= 0) {
                        System.out.println("Error: invalid amount.");
                    } else if (wd > balance) {
                        System.out.println("Error: insufficient balance.");
                    } else if (count >= MAX_TRANSACTIONS) {
                        System.out.println("Error: transaction history is full.");
                    } else {
                        balance -= wd;
                        transactions[count] = "Withdrawal: -" + wd;
                        count++;
                        System.out.println("Withdrawal successful. New balance: " + balance);
                    }
                    break;

                case 4:
                    if (count == 0) {
                        System.out.println("No transactions yet.");
                    } else {
                        System.out.println("--- Transaction History ---");
                        for (int i = 0; i < count; i++) {
                            System.out.println((i + 1) + ". " + transactions[i]);
                        }
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose from 1 to 5.");
            }
        }

        sc.close();
    }

    static int readInt(Scanner sc) {
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static double readDouble(Scanner sc) {
        String line = sc.nextLine().trim();
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

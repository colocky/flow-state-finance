package com.pluralsight;

import java.time.*;
import java.util.*;

public class FlowStateFinanceApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    showHomeScreen();
    }

    public static void showHomeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Flow State Finance ===");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    showLedgerScreen();
                    break;
                case "X":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void showLedgerScreen() {
        boolean inLedger = true;

        while (inLedger) {
            System.out.println("\n=== Ledger ===");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            ArrayList<Transaction> transactions =
                    new ArrayList<>(TransactionFileManager.loadTransactions());

            switch (choice) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayDeposits(transactions);
                    break;
                case "P":
                    displayPayments(transactions);
                    break;
                case "R":
                    showReportsScreen();
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void addDeposit() {
        System.out.println("\n--- Add Deposit ---");

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        amount = Math.abs(amount);

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                description,
                vendor,
                amount
        );

        TransactionFileManager.saveTransaction(transaction);
        System.out.println("Deposit saved.");
    }

    public static void makePayment() {
        System.out.println("\n--- Make Payment ---");

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        amount = -Math.abs(amount);

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                description,
                vendor,
                amount
        );

        TransactionFileManager.saveTransaction(transaction);
        System.out.println("Payment saved.");
    }

    public static void displayTransactions(ArrayList<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void displayDeposits(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (transaction.isDeposit()) {
                System.out.println(transaction);
            }
        }
    }

    public static void displayPayments(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (transaction.isPayment()) {
                System.out.println(transaction);
            }
        }
    }
}
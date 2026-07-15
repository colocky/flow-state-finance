package com.pluralsight;

import java.io.*;
import java.time.*;
import java.util.*;

public class FlowStateFinanceApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        transactions = new ArrayList<>(TransactionFileManager.loadTransactions());
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
        transactions.add(transaction);
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
        transactions.add(transaction);
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

    public static void showReportsScreen() {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n=== Reports ===");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            LocalDate today = LocalDate.now();

            switch (choice) {
                case "1":
                    // Month To Date
                    for (Transaction t : transactions) {
                        if (t.getDate().getMonth() == today.getMonth()
                                && t.getDate().getYear() == today.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "2":
                    // Previous Month
                    LocalDate lastMonth = today.minusMonths(1);
                    for (Transaction t : transactions) {
                        if (t.getDate().getMonth() == lastMonth.getMonth()
                                && t.getDate().getYear() == lastMonth.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "3":
                    // Year To Date
                    for (Transaction t : transactions) {
                        if (t.getDate().getYear() == today.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "4":
                    // Previous Year
                    int lastYear = today.getYear() - 1;

                    for (Transaction t : transactions) {
                        if (t.getDate().getYear() == lastYear) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "5":
                    // Search by Vendor
                    System.out.print("Enter vendor name: ");
                    String vendorSearch = scanner.nextLine().toLowerCase();

                    for (Transaction t : transactions) {
                        if (t.getVendor().toLowerCase().contains(vendorSearch)) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "6":
                    customSearch(transactions);
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void customSearch(ArrayList<Transaction> transactions) {
        System.out.println("\n--- Custom Search ---");

        System.out.print("Start Date (yyyy-MM-dd) or leave blank: ");
        String startDateInput = scanner.nextLine();

        System.out.print("End Date (yyyy-MM-dd) or leave blank: ");
        String endDateInput = scanner.nextLine();

        System.out.print("Description or leave blank: ");
        String descriptionInput = scanner.nextLine().toLowerCase();

        System.out.print("Vendor or leave blank: ");
        String vendorInput = scanner.nextLine().toLowerCase();

        System.out.print("Amount or leave blank: ");
        String amountInput = scanner.nextLine();

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double amount = null;

        if (!startDateInput.isBlank()) {
            startDate = LocalDate.parse(startDateInput);
        }

        if (!endDateInput.isBlank()) {
            endDate = LocalDate.parse(endDateInput);
        }

        if (!amountInput.isBlank()) {
            amount = Double.parseDouble(amountInput);
        }

        boolean found = false;

        for (Transaction t : transactions) {
            boolean matches = true;

            if (startDate != null && t.getDate().isBefore(startDate)) {
                matches = false;
            }
            if (endDate != null && t.getDate().isAfter(endDate)) {
                matches = false;
            }
            if (!descriptionInput.isBlank()
                    && !t.getDescription().toLowerCase().contains(descriptionInput)) {
                matches = false;
            }
            if (!vendorInput.isBlank()
                    && !t.getVendor().toLowerCase().contains(vendorInput)) {
                matches = false;
            }
            if (amount != null && t.getAmount() != amount) {
                matches = false;
            }
            if (matches) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching transactions found.");
        }
    }
}
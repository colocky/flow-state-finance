package com.pluralsight;

import java.time.*;
import java.util.*;

public class FlowStateFinanceApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showHomeScreen();
    }

    public static void showHomeScreen() {
        boolean running = true;

        System.out.println("""
                  -------------------------------------------------
                  +         Welcome to Flow State Finance         +
                  -------------------------------------------------""");



        while (running) {
            System.out.println("""
                  
                    =================================================
                    |                    Home                       |
                    ╔-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽_⎽-╗
                    │           What would you like to do?          │
                    │             D) Add deposit                    │
                    │             P) Make Payment (Debit)           │
                    │             L) Ledger                         │
                    │             X) Exit                           │
                    ╚-----------------------------------------------╝""");
            System.out.print("Choose an option: "
            );

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
            System.out.println("""
                    
                    =================================================
                    |                   Ledger                      |
                    ╔-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎽__⎽-⎻⎺⎺⎻-⎽_⎽-╗
                    │           What would you like to do?          │
                    │             A) All                            │
                    │             D) Deposits                       │
                    │             P) Payments                       │
                    │             R) Reports                        │
                    │             H) Home                           │
                    ╚-----------------------------------------------╝""");
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
        header("Make Deposit");

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
        System.out.println("Deposit saved 💸");
    }

    public static void makePayment() {
        header("Make Payment");

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
        System.out.println("Payment saved. 🪙");
    }

    public static void displayTransactions(ArrayList<Transaction> transactions) {
        System.out.println("""
            
            ====================================================================================================
                                                      ALL TRANSACTIONS
            ====================================================================================================""");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void displayDeposits(ArrayList<Transaction> transactions) {
        System.out.println("""
            
            ====================================================================================================
                                                      ALL DEPOSITS
            ====================================================================================================""");
        for (Transaction transaction : transactions) {
            if (transaction.isDeposit()) {
                System.out.println(transaction);
            }
        }
    }

    public static void displayPayments(ArrayList<Transaction> transactions) {
        System.out.println("""
            
            ====================================================================================================
                                                      ALL PAYMENTS
            ====================================================================================================""");
        for (Transaction transaction : transactions) {
            if (transaction.isPayment()) {
                System.out.println(transaction);
            }
        }
    }

    public static void showReportsScreen() {
        boolean inReports = true;

        while (inReports) {
            header("Reports");
            System.out.println("""
                    1) Month To Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    6) Custom Search
                    0) Back""");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            ArrayList<Transaction> transactions =
                    new ArrayList<>(TransactionFileManager.loadTransactions());

            LocalDate today = LocalDate.now();

            switch (choice) {
                case "1":
                    // Month To Date
                    skipLine();
                    for (Transaction t : transactions) {
                        if (t.getDate().getMonth() == today.getMonth()
                                && t.getDate().getYear() == today.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "2":
                    // Previous Month
                    skipLine();
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
                    skipLine();
                    for (Transaction t : transactions) {
                        if (t.getDate().getYear() == today.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "4":
                    // Previous Year
                    skipLine();
                    int lastYear = today.getYear() - 1;

                    for (Transaction t : transactions) {
                        if (t.getDate().getYear() == lastYear) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "5":
                    // Search by Vendor
                    skipLine();
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
        header("Custom Search");

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

    public static void header(String title) {
        System.out.println("\n================== " + title + " ==================");
    }

    public static void skipLine(){
        System.out.println();
    }


}
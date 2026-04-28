package com.pluralsight;

import java.time.*;
import java.util.*;

public class FlowStateFinanceApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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
}
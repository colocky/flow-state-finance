package com.pluralsight;

import java.io.*;
import java.time.*;
import java.util.*;

public class TransactionFileManager {
    private static final String fileName = "transactions.csv";
    ////////// FILE WRITER //////////
    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(transaction.toCsvLine());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction.");
        }
    }

    ////////// SHOW TRANSACTIONS METHOD //////////
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("date|time")) continue;

                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);

                    transactions.add(new Transaction(
                            date,
                            time,
                            parts[2],
                            parts[3],
                            Double.parseDouble(parts[4])
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading transactions.");
        }

        ////////// SORTING //////////
        transactions.sort(Comparator.comparing(Transaction::getDate));

        return transactions;
    }
}
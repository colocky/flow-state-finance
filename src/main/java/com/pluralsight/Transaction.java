package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final LocalDate date;
    private final LocalTime time;
    private final String description;
    private final String vendor;
    private final double amount;
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    ////////// CONSTRUCTOR //////////
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    ////////// GETTERS AND SETTERS //////////
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    ////////// TRANSACTION METHODS //////////
    public boolean isDeposit() {
        return amount > 0;
    }

    public boolean isPayment() {
        return amount < 0;
    }

    ////////// TO CSV FORMAT //////////
    public String toCsvLine() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    ////////// TO STRING FORMAT //////////
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public String toString() {
        String formattedTime = this.time.format(TIME_FORMAT);
        String transactionText = String.format(
                "| %s | %s | %-30s | %-25s | $%10.2f |",
                date,
                formattedTime,
                description,
                vendor,
                amount
        );

        if (isDeposit()) {
            return GREEN + transactionText + RESET;
        } else if (isPayment()) {
            return RED + transactionText + RESET;
        }

        return transactionText;
    }
}
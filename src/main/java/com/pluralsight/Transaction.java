package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private final LocalDate date;
    private final LocalTime time;
    private final String description;
    private final String vendor;
    private final double amount;

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
    public String toString() {
        return String.format(
                "%s | %s | %s | %s | $%.2f",
                date,
                time,
                description,
                vendor,
                amount
        );
    }
}
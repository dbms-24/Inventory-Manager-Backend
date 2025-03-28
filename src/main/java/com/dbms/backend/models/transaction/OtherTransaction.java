package com.dbms.backend.models.transaction;

import java.time.LocalDate;



public record OtherTransaction(int id, int user_id, LocalDate transaction_date, double amount, String description) {
    public OtherTransaction {
        if (transaction_date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

    }
}

package com.dbms.backend.models.transaction;

import java.time.LocalDate;



public record UpdateTransactionInfo(int id, int resp_id, LocalDate transaction_date, double amount) {
    public UpdateTransactionInfo {
        if (transaction_date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

    }
}

package com.dbms.backend.models.transaction;

import java.time.LocalDate;
import java.util.List;
import com.dbms.backend.models.stock.StockDressDescription;

// Here id is that of the transaction
public record CustomerCompleteTransaction(int id, int customer_id, LocalDate transaction_date, double amount, List<StockDressDescription> stockDressDescription) {
    public CustomerCompleteTransaction {
        if (transaction_date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

    }
}

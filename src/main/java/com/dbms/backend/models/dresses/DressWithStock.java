package com.dbms.backend.models.dresses;

import com.dbms.backend.models.stock.StockDescription;
import java.util.List;

public record DressWithStock(int id, String name, String brand, String gender, String size, String color, int required_quantity, List<StockDescription> stockDescription) {
    public DressWithStock {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        if(gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        if(size == null || size.isBlank()) {
            throw new IllegalArgumentException("Size cannot be null or empty");
        }
        if(color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }
        if(required_quantity < 0) {
            throw new IllegalArgumentException("Required quantity cannot be negative");
        }
    }
        
}

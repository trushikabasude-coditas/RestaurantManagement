package com.example.RestaurantManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)

    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Price snapshot at time of order (dish price may change later)
    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    // quantity * unitPrice
    @Column(name = "total_price")
    private Integer totalPrice;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        if (quantity != null && unitPrice != null) {
            this.totalPrice = quantity * unitPrice;
        }
    }
}

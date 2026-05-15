package com.example.RestaurantManagement.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class order {
    private Long id;
    private Long branch_id;
    private Long table_id;
    private Long waiter_id;
  private Integer subtotal;

    private Integer tax_amount;
    private Integer discount;
private Integer final_Amount;
@Enumerated(EnumType.STRING)
    private  orderStatus status;
}

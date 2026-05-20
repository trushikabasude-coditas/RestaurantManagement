package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="branch_id",nullable=false)
    private RestaurantBranch branch;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="table_id", nullable=false)
    private DineTable table;

    // waiter took order that waiter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="waiter_id")
    private User waiter;

    @Column(name="subtotal")
    private Integer subtotal;

    @Column(name="tax_amount")
    private Integer taxAmount;

    @Column(name="discount")
    private Integer discount;

    @Column(name="final_amount")
    private Integer finalAmount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable=false,length = 20)
    private OrderStatus status = OrderStatus.PENDING;

    @Builder.Default
    @OneToMany(mappedBy ="order",cascade=CascadeType.ALL)
    private List<OrderItems> items = new ArrayList<>();

    // one order -bil
    @OneToOne(mappedBy="order", cascade=CascadeType.ALL)
    private Bill bill;

}

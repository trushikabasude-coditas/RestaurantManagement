package com.example.RestaurantManagement.entity;
import com.example.RestaurantManagement.enums.PaymentWay;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @NotNull
    @Column(name = "subtotal")
 private Integer subTotal;

    @Column(name = "discount_applied")
    private Integer discountApplied = 0;
    @Column(name = "discount_reason" ,length = 20)
 private String discountReason;
    @Column(name = "tax_amount")
private int taxAmount;//(subtotal-=disc)

    @Min(0)
    @Column(name = "gst_rate")
    private Integer gstRate;
@Column(name = "final_amount")
private Integer finalAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_way", length = 20)
    private PaymentWay paymentWay;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "is_paid", nullable = false)
    private boolean paid = false;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

 }


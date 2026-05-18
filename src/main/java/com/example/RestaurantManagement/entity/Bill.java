package com.example.RestaurantManagement.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private Long restaurant_id;
 private int subTotal;
 private int discountApplied;
 private String discountReason;
private int taxAmount;//(subtotal-=disc)
private int gstRate;
private int FinalAmount;
@Column(name = "pdf_url")
    private String pdfUrl;





}


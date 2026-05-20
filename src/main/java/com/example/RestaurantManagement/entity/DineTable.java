package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.TableStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name ="dining_tables",uniqueConstraints=@UniqueConstraint(columnNames={"branch_id","table_number"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DineTable {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable=false, nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="branch_id",nullable = false)
    private RestaurantBranch branch;
    @NotNull
    @Min(value=1)
    @Column(name ="table_number", nullable = false)
    private Integer tableNumber;

    @Min(value=1)
    @Column(name="capacity")
    private Integer capacity;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assigned_waiter_id")
    private User assignedWaiter;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name ="status",nullable=false,length=20)
    private TableStatus status=TableStatus.AVAILABLE;
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
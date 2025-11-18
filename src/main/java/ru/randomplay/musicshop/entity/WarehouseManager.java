package ru.randomplay.musicshop.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.randomplay.musicshop.model.WorkerStatus;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouse_managers")
public class WarehouseManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkerStatus status;


    @OneToMany(mappedBy = "warehouseManager")
    private Set<Supply> supplies = new HashSet<>();

}

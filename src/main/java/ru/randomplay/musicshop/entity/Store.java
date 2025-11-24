package ru.randomplay.musicshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.randomplay.musicshop.model.StoreStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StoreStatus status;

    @Column(name = "location", nullable = false, unique = true)
    private String location;

    @Column(name = "working_hours", nullable = false)
    private String workingHours;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;


    @OneToMany(mappedBy = "store")
    private Set<WarehouseManager> warehouseManagers = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Employee> employees = new HashSet<>();

}

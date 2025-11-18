package ru.randomplay.musicshop.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.randomplay.musicshop.model.ProductStatus;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Используем BigDecimal для гарантированной точности
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING) // в БД будет сохраняться строка, вместо числа
    @Column(name = "status", nullable = false)
    private ProductStatus status;


    // Используем HashSet, чтобы был поиск со скоростью O(1)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductCategoryLink> categoryLinks = new HashSet<>();

}

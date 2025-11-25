package ru.randomplay.musicshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.randomplay.musicshop.model.ProductStatus;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    @Column(name = "image_filename")
    private String imageFilename;


    // Используем HashSet, чтобы был поиск со сложностью O(1)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductCategoryLink> categoryLinks = new HashSet<>();

}

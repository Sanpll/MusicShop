package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.randomplay.musicshop.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String supplierName;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private ProductStatus status;
    private String imageFilename;
    private List<String> categoryNames;
}

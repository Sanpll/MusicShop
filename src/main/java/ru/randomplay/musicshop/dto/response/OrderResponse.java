package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerLastName;
    private String customerFirstName;
    private LocalDateTime createdAt;
    private Map<String, Integer> products;
    private BigDecimal totalPrice;
}

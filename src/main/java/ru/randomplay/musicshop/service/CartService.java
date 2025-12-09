package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.response.CartItemResponse;
import ru.randomplay.musicshop.entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<CartItemResponse> getAll(Cart cart);

    BigDecimal getTotalPrice(Cart cart);

    void addProduct(Cart cart, Long productId, Integer quantity);

    void deleteProduct(Cart cart, Long productId);
}

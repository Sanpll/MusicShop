package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.response.CartItemResponse;
import ru.randomplay.musicshop.entity.Cart;
import ru.randomplay.musicshop.entity.CartItem;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.mapper.CartItemMapper;
import ru.randomplay.musicshop.repository.CartRepository;
import ru.randomplay.musicshop.repository.ProductRepository;
import ru.randomplay.musicshop.service.CartService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public List<CartItemResponse> getAll(Cart cart) {
        List<CartItem> cartItemList = cart.getCartItems().stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .toList();

        return cartItemMapper.toCartItemResponseList(cartItemList);
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> {
                    BigDecimal price = item.getProduct().getPrice();
                    Integer quantity = item.getQuantity();
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void addProduct(Cart cart, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " doesn't exist"));

        // Находим уже существующий cartItem
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        // Проверяем, что сохраняемое количество не превышает хранящееся на складе
        int newQuantity = (existingItem != null) ? existingItem.getQuantity() + quantity : quantity;
        if (newQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product with id " + product.getId() +
                    ". Available: " + product.getQuantity() +
                    ", requested total: " + newQuantity);
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("You must choose a positive number of products with id " + product.getId());
        }
        if (existingItem != null && newQuantity == 0 && existingItem.getQuantity() == 1) {
            deleteProduct(cart, productId);
            return;
        }

        // Применяем изменения / сохраняем
        if (existingItem != null) {
            existingItem.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(newQuantity);
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteProduct(Cart cart, Long productId) {
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " isn't in cart"));

        cart.getCartItems().remove(existingItem);
        cartRepository.save(cart);
    }

    @Override
    public boolean checkProductQuantities(Cart cart) {
        boolean isCartQuantitiesChanged = false;
        for (CartItem cartItem : cart.getCartItems()) {
            int productQuantity = cartItem.getProduct().getQuantity();
            if (cartItem.getQuantity() > productQuantity) {
                cartItem.setQuantity(productQuantity);
                isCartQuantitiesChanged = true;
            }
        }
        cartRepository.save(cart);
        return isCartQuantitiesChanged;
    }
}

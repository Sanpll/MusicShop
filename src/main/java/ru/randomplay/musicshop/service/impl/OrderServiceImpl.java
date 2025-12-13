package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.response.OrderResponse;
import ru.randomplay.musicshop.entity.*;
import ru.randomplay.musicshop.mapper.OrderMapper;
import ru.randomplay.musicshop.model.CartStatus;
import ru.randomplay.musicshop.model.PaymentStatus;
import ru.randomplay.musicshop.repository.CustomerRepository;
import ru.randomplay.musicshop.repository.OrderRepository;
import ru.randomplay.musicshop.repository.ProductRepository;
import ru.randomplay.musicshop.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse get(Long id) {
        return orderMapper.toOrderResponse(orderRepository.findByIdWithUserAndProducts(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + id + " doesn't exist")));
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderMapper.toOrderResponseList(orderRepository.findAllWithUserAndProducts());
    }

    @Override
    public List<OrderResponse> getAllByEmail(String email) {
        return orderMapper.toOrderResponseList(orderRepository.findAllByEmailWithProducts(email));
    }

    @Override
    public List<OrderResponse> getAllWithoutConfirm() {
        return orderMapper.toOrderResponseList(orderRepository.findAllWithUserAndProductsAndNullEmployee());
    }

    @Override
    @Transactional
    public void create(Customer customer, BigDecimal totalPrice) {
        Cart cart = customer.getCart();
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + customer.getId() + " has empty cart");
        }

        // Уменьшаем количество товаров на складе
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            Integer quantityInCart = cartItem.getQuantity();

            if (product.getQuantity() < quantityInCart) {
                throw new IllegalArgumentException("Not enough stock for product with id " + product.getId() +
                        ". Available: " + product.getQuantity() +
                        ", requested total: " + quantityInCart);
            }

            product.setQuantity(product.getQuantity() - quantityInCart);
            if (product.getQuantity() == 0) {
                product.setStatus(ru.randomplay.musicshop.model.ProductStatus.OUT_OF_STOCK);
            }

            productRepository.save(product);
        }

        // Сохраняем текущую корзину в заказ
        Order order = new Order();
        order.setCustomer(customer);
        cart.setStatus(CartStatus.COMPLETED);
        order.setCart(cart);
        order.setTotalPrice(totalPrice);
        order.setStatus(PaymentStatus.PENDING);

        // Даём покупателю новую пустую корзину
        customer.setCart(new Cart());

        customerRepository.save(customer);
        orderRepository.save(order);
    }

    @Override
    public void confirm(Employee employee, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " doesn't exist"));
        order.setEmployee(employee);
        order.setStatus(PaymentStatus.PAID);
        orderRepository.save(order);
    }

    @Override
    public void reject(Employee employee, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " doesn't exist"));
        order.setEmployee(employee);
        order.setStatus(PaymentStatus.FAILED);
        orderRepository.save(order);
    }
}

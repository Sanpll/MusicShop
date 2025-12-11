package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.response.OrderResponse;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void create(Customer customer, BigDecimal orderPrice);

    OrderResponse get(Long id);

    List<OrderResponse> getAll();

    List<OrderResponse> getAllWithoutConfirm();

    void confirmOrder(Employee employee, Long orderId);
}

package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.response.OrderResponse;
import ru.randomplay.musicshop.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void create(Customer customer, BigDecimal orderPrice);

    List<OrderResponse> getAll();
}

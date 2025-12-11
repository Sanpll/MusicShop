package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.CustomerCreateRequest;
import ru.randomplay.musicshop.entity.Customer;

public interface CustomerService {
    void save(CustomerCreateRequest customerCreateRequest);

    Customer getByEmailWithCart(String email);
}

package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.CustomerCreateRequest;
import ru.randomplay.musicshop.dto.response.CustomerResponse;
import ru.randomplay.musicshop.dto.update.CustomerUpdateRequest;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;

public interface CustomerService {
    void save(CustomerCreateRequest customerCreateRequest);

    void update(User user, CustomerUpdateRequest customerUpdateRequest);

    CustomerResponse getByEmailWithUser(String email);

    Customer getByEmailWithCart(String email);
}

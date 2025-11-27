package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.CustomerRequest;

public interface CustomerService {
    void save(CustomerRequest customerRequest);
}

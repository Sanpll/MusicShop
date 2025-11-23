package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.CustomerCreateRequest;

public interface CustomerService {
    void save(CustomerCreateRequest customerCreateRequest);
}

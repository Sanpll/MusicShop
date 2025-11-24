package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.StoreCreateRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
    List<StoreResponse> getAll();

    void save(StoreCreateRequest storeCreateRequest);
}

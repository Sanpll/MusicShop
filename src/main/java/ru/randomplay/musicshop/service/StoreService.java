package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.StoreCreateRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;
import ru.randomplay.musicshop.dto.update.StoreUpdateRequest;

import java.util.List;

public interface StoreService {
    StoreResponse get(Long id);

    List<StoreResponse> getAll();

    void save(StoreCreateRequest storeCreateRequest);

    void update(Long id, StoreUpdateRequest storeUpdateRequest);
}

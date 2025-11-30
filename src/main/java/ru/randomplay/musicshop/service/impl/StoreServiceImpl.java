package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.create.StoreCreateRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;
import ru.randomplay.musicshop.dto.update.StoreUpdateRequest;
import ru.randomplay.musicshop.entity.Store;
import ru.randomplay.musicshop.mapper.StoreMapper;
import ru.randomplay.musicshop.repository.StoreRepository;
import ru.randomplay.musicshop.service.StoreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public StoreResponse get(Long id) {
        return storeMapper.toStoreResponse(storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store with this ID doesn't exist")));
    }

    @Override
    public List<StoreResponse> getAll() {
        return storeMapper.toStoreResponseList(storeRepository.findAll());
    }

    @Override
    public void save(StoreCreateRequest storeCreateRequest) {
        if (storeRepository.findByLocation(storeCreateRequest.getLocation()).isPresent()) {
            throw new IllegalArgumentException("Store with this location already exists");
        }

        Store createdStore = storeMapper.toStore(storeCreateRequest);
        storeRepository.save(createdStore);
    }

    @Override
    public void update(Long id, StoreUpdateRequest storeUpdateRequest) {
        Store updatedStore = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store with this ID doesn't exist"));

        // проверка на то, что новый адрес не будет совпадать с уже существующими
        if (!updatedStore.getLocation().equals(storeUpdateRequest.getLocation()) &&
                storeRepository.findByLocation(storeUpdateRequest.getLocation()).isPresent()) {
            throw new IllegalArgumentException("Store with this location already exists");
        }

        storeMapper.updateStore(updatedStore, storeUpdateRequest);
        storeRepository.save(updatedStore);
    }
}

package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.request.StoreRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;
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
    public List<StoreResponse> getAll() {
        return storeMapper.toStoreResponseList(storeRepository.findAll());
    }

    @Override
    @Transactional
    public void save(StoreRequest storeRequest) {
        if (storeRepository.findByLocation(storeRequest.getLocation()).isPresent()) {
            throw new IllegalArgumentException("Store with this location already exists");
        }

        Store createdStore = storeMapper.toStore(storeRequest);
        storeRepository.save(createdStore);
    }
}

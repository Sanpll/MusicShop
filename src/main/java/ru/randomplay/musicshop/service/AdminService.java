package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.AdminCreateRequest;

public interface AdminService {
    void save(AdminCreateRequest adminCreateRequest);
}

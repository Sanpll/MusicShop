package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.AdminCreateRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;

import java.util.List;

public interface AdminService {
    List<AdminResponse> getAll();

    void save(AdminCreateRequest adminCreateRequest);
}

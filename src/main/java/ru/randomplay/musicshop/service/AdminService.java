package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.AdminCreateRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;
import ru.randomplay.musicshop.dto.update.AdminUpdateRequest;

import java.util.List;

public interface AdminService {
    AdminResponse get(Long id);

    List<AdminResponse> getAll();

    void save(AdminCreateRequest adminCreateRequest);

    void update(Long id, AdminUpdateRequest adminUpdateRequest);
}

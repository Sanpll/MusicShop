package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.AdminRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;

import java.util.List;

public interface AdminService {
    List<AdminResponse> getAll();

    void save(AdminRequest adminRequest);
}

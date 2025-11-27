package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.request.CustomerRequest;
import ru.randomplay.musicshop.entity.Cart;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.CustomerMapper;
import ru.randomplay.musicshop.repository.CustomerRepository;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(CustomerRequest customerRequest) {
        if (userRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = customerMapper.toUser(customerRequest);
        createdUser.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

        Cart createdCart = new Cart();
        Customer createdCustomer = Customer.create(createdUser, createdCart);

        customerRepository.save(createdCustomer);
    }
}

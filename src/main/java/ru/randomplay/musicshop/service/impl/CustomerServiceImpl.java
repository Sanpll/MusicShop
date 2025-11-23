package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.CustomerCreateRequest;
import ru.randomplay.musicshop.entity.Cart;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.CustomerMapper;
import ru.randomplay.musicshop.model.CartStatus;
import ru.randomplay.musicshop.model.CustomerStatus;
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
    public void save(CustomerCreateRequest customerCreateRequest) {
        if (userRepository.findByEmail(customerCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = customerMapper.toUser(customerCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

        Cart createdCart = Cart.builder()
                .status(CartStatus.ACTIVE)
                .build();
        Customer createdCustomer = Customer.builder()
                .user(createdUser)
                .cart(createdCart)
                .status(CustomerStatus.ACTIVE)
                .build();

        customerRepository.save(createdCustomer);
    }
}

package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.UserCreateRequest;
import ru.randomplay.musicshop.entity.Cart;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Customer save(UserCreateRequest userCreateRequest) {
        if (userRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = userMapper.toUser(userCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        Cart createdCart = Cart.builder()
                .status(CartStatus.ACTIVE)
                .build();
        Customer createdCustomer = Customer.builder()
                .user(createdUser)
                .cart(createdCart)
                .status(CustomerStatus.ACTIVE)
                .build();

        customerRepository.save(createdCustomer);

        return createdCustomer;
    }
}

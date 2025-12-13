package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.CustomerCreateRequest;
import ru.randomplay.musicshop.dto.response.CustomerResponse;
import ru.randomplay.musicshop.dto.update.CustomerUpdateRequest;
import ru.randomplay.musicshop.entity.Cart;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.CustomerMapper;
import ru.randomplay.musicshop.mapper.UserMapper;
import ru.randomplay.musicshop.repository.CustomerRepository;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(CustomerCreateRequest customerCreateRequest) {
        if (userRepository.findByEmail(customerCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email '" + customerCreateRequest.getEmail() + "' already exists");
        }

        User createdUser = customerMapper.toUser(customerCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

        Cart createdCart = new Cart();
        Customer createdCustomer = Customer.create(createdUser, createdCart);

        customerRepository.save(createdCustomer);
    }

    @Override
    @Transactional
    public void update(User user, CustomerUpdateRequest customerUpdateRequest) {
        userMapper.updateUser(user, customerUpdateRequest);
        userRepository.save(user);
    }

    @Override
    public CustomerResponse getByEmailWithUser(String email) {
        return customerMapper.toCustomerResponse(customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with email '" + email + "' doesn't exist")));
    }

    @Override
    public Customer getByEmailWithCart(String email) {
        return customerRepository.findByEmailWithCartAndProducts(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with email '" + email + "' doesn't exist"));
    }
}

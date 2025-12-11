package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.response.OrderResponse;
import ru.randomplay.musicshop.entity.CartItem;
import ru.randomplay.musicshop.entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper {
    @Mapping(target = "customerLastName", source = "customer.user.lastName")
    @Mapping(target = "customerFirstName", source = "customer.user.firstName")
    @Mapping(target = "employeeLastName", source = "employee.user.lastName")
    @Mapping(target = "employeeFirstName", source = "employee.user.firstName")
    @Mapping(target = "products", expression = "java(mapCartItems(order.getCart().getCartItems()))")
    public abstract OrderResponse toOrderResponse(Order order);

    public abstract List<OrderResponse> toOrderResponseList(List<Order> orderList);

    protected Map<String, Integer> mapCartItems(Set<CartItem> cartItems) {
        Map<String, Integer> products = new HashMap<>();
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                products.put(item.getProduct().getName(), item.getQuantity());
            }
        }
        return products;
    }
}

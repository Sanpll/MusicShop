package ru.randomplay.musicshop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.randomplay.musicshop.dto.create.SupplyCreateRequest;
import ru.randomplay.musicshop.dto.response.SupplyResponse;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.Supply;
import ru.randomplay.musicshop.entity.SupplyItem;
import ru.randomplay.musicshop.repository.ProductRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SupplyMapper {
    protected final ProductRepository productRepository;

    public List<SupplyResponse> toSupplyResponseList(List<Supply> supplyList) {
        List<SupplyResponse> supplyResponseList = new ArrayList<>();
        for (Supply supply : supplyList) {
            SupplyResponse supplyResponse = new SupplyResponse();
            supplyResponse.setId(supply.getId());
            supplyResponse.setWarehouseManagerLastName(supply.getWarehouseManager().getUser().getLastName());
            supplyResponse.setWarehouseManagerFirstName(supply.getWarehouseManager().getUser().getFirstName());
            supplyResponse.setDate(supply.getDate());

            Map<String, Integer> products = new HashMap<>();
            for (SupplyItem supplyItem : supply.getSupplyItems()) {
                products.put(supplyItem.getProduct().getName(), supplyItem.getQuantity());
            }
            supplyResponse.setSupplyItems(products);

            supplyResponseList.add(supplyResponse);
        }

        return supplyResponseList;
    }

    public Set<SupplyItem> toSupplyItemSet(SupplyCreateRequest supplyCreateRequest, Supply supply) {
        Set<SupplyItem> supplyItems = new HashSet<>();

        for (Long productId : supplyCreateRequest.getProductsId().keySet()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " doesn't exist"));

            SupplyItem supplyItem = new SupplyItem();
            supplyItem.setSupply(supply);
            supplyItem.setProduct(product);
            supplyItem.setQuantity(supplyCreateRequest.getProductsId().get(productId));
            supplyItems.add(supplyItem);

            product.setQuantity(product.getQuantity() + supplyItem.getQuantity());
            productRepository.save(product);
        }

        return supplyItems;
    }
}

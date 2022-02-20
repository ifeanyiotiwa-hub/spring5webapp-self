package guru.springframework.spring5webapp.service;

import guru.springframework.spring5webapp.dto.BeerOrderDTO;
import guru.springframework.spring5webapp.model.BeerOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface BeerOrderService {
    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDto);

    BeerOrderDTO getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}

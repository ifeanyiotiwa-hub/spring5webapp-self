package guru.springframework.spring5webapp.business.serviceimpl;

import guru.springframework.spring5webapp.business.service.BeerOrderService;
import guru.springframework.spring5webapp.dto.BeerOrderDTO;
import guru.springframework.spring5webapp.mapper.BeerOrderMapper;
import guru.springframework.spring5webapp.model.BeerOrder;
import guru.springframework.spring5webapp.model.BeerOrderPagedList;
import guru.springframework.spring5webapp.model.Customer;
import guru.springframework.spring5webapp.model.OrderStatusEnum;
import guru.springframework.spring5webapp.repository.BeerOrderRepository;
import guru.springframework.spring5webapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);

        if (optCustomer.isPresent()) {
            Customer customer = optCustomer.get();
            Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(customer, pageable);

            return new BeerOrderPagedList(beerOrderPage.stream().map(beerOrderMapper::beerOrderToDTO)
                                                                .collect(Collectors.toList()),
                                          PageRequest.of(beerOrderPage.getPageable().getPageNumber(),
                                                         beerOrderPage.getPageable().getPageSize()),
                                          beerOrderPage.getTotalElements());
        } else {
            return null;
        }
    }

    @Override
    public BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            BeerOrder beerOrder = beerOrderMapper.DTOToBeerOrder(beerOrderDto);
            beerOrder.setId(null);
            beerOrder.setCustomer(customer);
            beerOrder.setOrderStatus(OrderStatusEnum.NEW);

            beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));

            BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

            return beerOrderMapper.beerOrderToDTO(savedBeerOrder);
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "CUSTOMER Not Found");
        }
    }

    @Override
    public BeerOrderDTO getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDTO(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId) {
        Optional<Customer> optionalCustomer =customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);
            if (beerOrderOptional.isPresent()) {
                BeerOrder beerOrder = beerOrderOptional.get();
                if (Objects.equals(beerOrder.getCustomer().getId(), customerId)) {
                    return beerOrder;
                }
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer Order Not Found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer NOT FOUND");
    }
}

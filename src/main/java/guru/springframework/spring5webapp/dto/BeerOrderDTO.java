package guru.springframework.spring5webapp.dto;


import guru.springframework.spring5webapp.model.BaseItem;
import guru.springframework.spring5webapp.model.OrderStatusEnum;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDTO extends BaseItem {

    @Builder
    public BeerOrderDTO(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID customerId, List<BeerOrderLineDTO> beerOrderLines,
                        OrderStatusEnum orderStatus, String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }

    private UUID customerId;
    private String customerRef;
    private List<BeerOrderLineDTO> beerOrderLines;
    private OrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;
}

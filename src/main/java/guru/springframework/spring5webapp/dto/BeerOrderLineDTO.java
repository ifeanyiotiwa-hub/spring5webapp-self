package guru.springframework.spring5webapp.dto;

import guru.springframework.spring5webapp.model.BaseItem;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDTO extends BaseItem {

    @Builder
    public BeerOrderLineDTO(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            UUID beerId, Integer orderQuantity) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerId = beerId;
        this.orderQuantity = orderQuantity;
    }

    private UUID beerId;
    private Integer orderQuantity = 0;
}

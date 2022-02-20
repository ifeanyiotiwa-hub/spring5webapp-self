package guru.springframework.spring5webapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import guru.springframework.spring5webapp.model.BaseItem;
import guru.springframework.spring5webapp.model.BeerStyleEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerDTO extends BaseItem {

        @Builder
    public BeerDTO(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String beerName,
                       BeerStyleEnum beerStyle, String upc, Integer quantityOnHand, BigDecimal price) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
    }

    private String beerName;
    private BeerStyleEnum beerStyle;
    private String upc;
    private Integer quantityOnHand;

    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private BigDecimal price;
}

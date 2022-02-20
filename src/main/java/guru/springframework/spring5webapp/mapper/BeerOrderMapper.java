package guru.springframework.spring5webapp.mapper;

import guru.springframework.spring5webapp.dto.BeerOrderDTO;
import guru.springframework.spring5webapp.model.BeerOrder;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDTO beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDTO beerOrderDTO);
}

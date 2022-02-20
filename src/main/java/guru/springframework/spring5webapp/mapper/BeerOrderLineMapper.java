package guru.springframework.spring5webapp.mapper;


import guru.springframework.spring5webapp.dto.BeerOrderLineDTO;
import guru.springframework.spring5webapp.model.BeerOrderLine;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {
    BeerOrderLineDTO beerOrderLineToDTO(BeerOrderLine beerOrderLine);

    BeerOrderLine DTOToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);
}

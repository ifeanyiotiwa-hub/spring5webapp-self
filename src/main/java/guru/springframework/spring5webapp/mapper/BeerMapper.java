package guru.springframework.spring5webapp.mapper;

import guru.springframework.spring5webapp.dto.BeerDTO;
import guru.springframework.spring5webapp.model.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOToBeer(BeerDTO beerDTO);
}
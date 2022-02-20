package guru.springframework.spring5webapp.business.service;

import guru.springframework.spring5webapp.dto.BeerDTO;
import guru.springframework.spring5webapp.model.BeerPagedList;
import guru.springframework.spring5webapp.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDTO findBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDTO saveBeer(BeerDTO beerDto);

    void updateBeer(UUID beerId, BeerDTO beerDto);

    void deleteById(UUID beerId);

    BeerDTO findBeerByUpc(String upc);
}

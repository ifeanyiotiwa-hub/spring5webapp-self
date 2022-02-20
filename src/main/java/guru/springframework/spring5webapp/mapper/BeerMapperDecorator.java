package guru.springframework.spring5webapp.mapper;

import guru.springframework.spring5webapp.dto.BeerDTO;
import guru.springframework.spring5webapp.model.Beer;
import guru.springframework.spring5webapp.model.BeerInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerMapper beerMapper;

    @Autowired
    @Qualifier("delegate")
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {

        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);

        if(beer.getBeerInventory() != null && beer.getBeerInventory().size() > 0) {
            beerDTO.setQuantityOnHand(beer.getBeerInventory()
                    .stream().map(BeerInventory::getQuantityOnHand)
                    .reduce(0, Integer::sum));

        }

        return beerDTO;
    }
}

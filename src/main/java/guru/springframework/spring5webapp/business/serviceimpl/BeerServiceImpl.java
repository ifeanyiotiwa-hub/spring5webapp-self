package guru.springframework.spring5webapp.business.serviceimpl;

import guru.springframework.spring5webapp.business.service.BeerService;
import guru.springframework.spring5webapp.dto.BeerDTO;
import guru.springframework.spring5webapp.mapper.BeerMapper;
import guru.springframework.spring5webapp.model.Beer;
import guru.springframework.spring5webapp.model.BeerPagedList;
import guru.springframework.spring5webapp.model.BeerStyleEnum;
import guru.springframework.spring5webapp.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        log.debug("Listing Beers");

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //Search Both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }


        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage.getContent()
                                                       .stream().map(beerMapper::beerToBeerDTO)
                                                                .collect(Collectors.toList()),
                                               PageRequest.of(beerPage.getPageable().getPageNumber(),
                                                              beerPage.getPageable().getPageSize()),
                                               beerPage.getTotalElements());

        } else {
            beerPagedList = new BeerPagedList(beerPage.getContent().stream()
                                                                    .map(beerMapper::beerToBeerDTO)
                                                                    .collect(Collectors.toList()),
                                              PageRequest.of(beerPage.getPageable().getPageNumber(),
                                                              beerPage.getPageable().getPageSize()),
                                              beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Override
    public BeerDTO findBeerById(UUID beerId, Boolean showInventoryOnHand) {
        log.debug("Finding Beer by id: " + beerId);

        Optional<Beer> optBeer = beerRepository.findById(beerId);

        if (optBeer.isPresent()) {
            Beer beer = optBeer.get();
            log.debug("Found BeerId: " + beerId);
            if (showInventoryOnHand) {
                return beerMapper.beerToBeerDTO(beer);
            } else {
                return beerMapper.beerToBeerDTO(beer);
            }
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + beerId);
        }
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beerDTO) {
        Optional<Beer> optBeer = beerRepository.findById(beerId);

        optBeer.ifPresentOrElse(beer -> {
            beer.setBeerName(beerDTO.getBeerName());
            beer.setBeerStyle(beerDTO.getBeerStyle());
            beer.setPrice(beerDTO.getPrice());
            beer.setUpc(beerDTO.getUpc());
            beerRepository.save(beer);
        }, () -> {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + beerId);
        });
    }

    @Override
    public void deleteById(UUID beerId) {
        beerRepository.deleteById(beerId);
    }

    @Override
    public BeerDTO findBeerByUpc(String upc) {
        return beerMapper.beerToBeerDTO(beerRepository.findByUpc(upc));
    }
}

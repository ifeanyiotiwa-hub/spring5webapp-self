package guru.springframework.spring5webapp.mapper;

import guru.springframework.spring5webapp.dto.BeerOrderLineDTO;
import guru.springframework.spring5webapp.model.BeerOrderLine;
import guru.springframework.spring5webapp.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper{
    private BeerRepository beerRepository;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerRepository(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDTO beerOrderLineToDTO(BeerOrderLine beerOrderLine) {
        BeerOrderLineDTO beerOrderLineDTO = beerOrderLineMapper.beerOrderLineToDTO(beerOrderLine);
        beerOrderLineDTO.setBeerId(beerOrderLine.getBeer().getId());
        return beerOrderLineDTO;
    }

    @Override
    public BeerOrderLine DTOToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO) {
        BeerOrderLine beerOrderLine = beerOrderLineMapper.DTOToBeerOrderLine(beerOrderLineDTO);
        beerOrderLine.setBeer(beerRepository.getOne(beerOrderLineDTO.getBeerId()));
        beerOrderLine.setOrderQuantity(0);
        return beerOrderLine;
    }
}

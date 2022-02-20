package guru.springframework.spring5webapp.business.serviceimpl;

import guru.springframework.spring5webapp.business.service.BreweryService;
import guru.springframework.spring5webapp.model.Brewery;
import guru.springframework.spring5webapp.repository.BreweryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BreweryServiceImpl implements BreweryService {
    private final BreweryRepository breweryRepository;


    @Override
    public List<Brewery> getAllBreweries() {
        return breweryRepository.findAll();
    }
}

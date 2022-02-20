package guru.springframework.spring5webapp.repository;

import guru.springframework.spring5webapp.model.Beer;
import guru.springframework.spring5webapp.model.BeerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {
    List<BeerInventory> findAllByBeer(Beer beer);
}

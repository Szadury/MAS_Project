package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.ResourceNotFoundException;
import com.pjwstk.MAS.BeerBar.models.Beer;
import com.pjwstk.MAS.BeerBar.repositories.BeerRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class BeerController {
    private final BeerRepository beerRepository;

    public BeerController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @GetMapping("/")
    public List<Beer> getBeers() {
        return beerRepository.findAll();
    }

    @PostMapping("/beers")
    public Beer createBeer(@Valid @RequestBody Beer beer) {
        return beerRepository.save(beer);
    }

    @PutMapping("/beers/{beerId}")
    public Beer updateBeer(@PathVariable Long beerId,
                                   @Valid @RequestBody Beer beerRequest) {
        return beerRepository.findById(beerId)
                .map(beer -> {
                    beer.setName(beerRequest.getName());
                    beer.setDescription(beerRequest.getDescription());
                    return beerRepository.save(beer);
                }).orElseThrow(() -> new ResourceNotFoundException("Beer not found with id " + beerId));
    }

}

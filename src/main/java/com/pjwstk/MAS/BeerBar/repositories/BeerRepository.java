package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    @Override
    List<Beer> findAll();
}

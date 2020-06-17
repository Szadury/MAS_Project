package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {
    @Override
    List<Bar> findAll();


}

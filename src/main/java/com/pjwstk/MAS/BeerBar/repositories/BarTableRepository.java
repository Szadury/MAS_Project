package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarTableRepository extends JpaRepository<BarTable, Long> {
    @Query("SELECT bt FROM BarTable bt WHERE bt.bar = :bar")
    List<BarTable> getBarTablesByBar(@Param("bar") Bar bar);

    @Query("Select bt FROM BarTable bt where bt.id = :tableId")
    BarTable getById(@Param("tableId") int tableId);

}

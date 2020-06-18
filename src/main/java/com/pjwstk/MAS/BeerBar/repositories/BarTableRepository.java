package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.BarTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BarTableRepository extends JpaRepository<BarTable, Long> {
    @Query("SELECT bt FROM BarTable bt WHERE bt.barId = :barId")
    Iterable<BarTable> getBarTablesByBarID(@Param("barId") int barId);

}

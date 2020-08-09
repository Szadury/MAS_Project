package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumUserRepository  extends JpaRepository<PremiumUser, Long> {

    @Query("Select pu from PremiumUser pu where pu.userModel.id = :userId")
    PremiumUser findPremiumUserIdWithUserModelId(@Param("userId") int userId);


}

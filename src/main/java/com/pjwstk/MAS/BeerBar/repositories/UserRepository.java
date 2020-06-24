package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Override
    List<UserModel> findAll();

    @Query("SELECT u FROM UserModel u WHERE u.username = :username AND u.password = :password")
    Iterable<UserModel> findUserWithPassword(@Param("username") String username, @Param("password") String password);


}

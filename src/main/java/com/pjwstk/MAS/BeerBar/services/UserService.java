package com.pjwstk.MAS.BeerBar.services;

import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import com.pjwstk.MAS.BeerBar.models.UserModel;
import com.pjwstk.MAS.BeerBar.repositories.PremiumUserRepository;
import com.pjwstk.MAS.BeerBar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PremiumUserRepository premiumUserRepository;

    @Autowired
    UserRepository userRepository;


    public PremiumUser findPremiumUserByUserModel(int userModelId) {
        return premiumUserRepository.findPremiumUserIdWithUserModelId(userModelId);
    }

    public Iterable<UserModel> findUserWithPassword(String username, String password){
        return userRepository.findUserWithPassword(username, password);
    }
}

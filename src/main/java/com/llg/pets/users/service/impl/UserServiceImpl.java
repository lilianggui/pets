package com.llg.pets.users.service.impl;

import com.llg.pets.users.entity.Users;
import com.llg.pets.users.repository.UserRepository;
import com.llg.pets.users.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Users login(Users users) {
        return userRepository.findByUserName(users.getUserName());
    }
}

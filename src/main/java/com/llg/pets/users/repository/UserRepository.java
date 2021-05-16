package com.llg.pets.users.repository;

import com.llg.pets.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer>{
    Users findByUserName(String userName);
}

package com.hexaware.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.usermicroservice.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {


}

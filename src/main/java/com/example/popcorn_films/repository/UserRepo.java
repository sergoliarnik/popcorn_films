package com.example.popcorn_films.repository;

import com.example.popcorn_films.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}

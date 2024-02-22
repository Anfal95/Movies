package com.movie.movies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.movies.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findById(Long userId);
    User findOneById(Long userId);
    List<User> findAll();

    Optional<User> findOneByEmail(String subject);
    
    User findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}

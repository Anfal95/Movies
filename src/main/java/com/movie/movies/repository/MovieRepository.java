package com.movie.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movies.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	Movie findOneById (Long movieId);

}

package com.movie.movies.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.movies.entity.Movie;
import com.movie.movies.entity.Star;
import com.movie.movies.exceptions.BaseException;
import com.movie.movies.service.impl.MovieServiceImpl;


@RestController
@RequestMapping(path = "/api/movies")
@CrossOrigin(origins = "http://localhost:3002")
public class MovieController {
	
	private final MovieServiceImpl movieService;
	
	public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }
	
	@GetMapping
    public ResponseEntity<List<Map<String, Object>>> getLatestMovies() {
		
		ResponseEntity<List<Map<String, Object>>> latestMovies = movieService.getLatestMovies();
		
        return latestMovies;
    }
	
	@GetMapping("/search")
	@CrossOrigin(origins = "http://localhost:3002")
    public ResponseEntity<List<Map<String, Object>>>  searchMovie(@RequestParam String query) {
		
		ResponseEntity<List<Map<String, Object>>> searchedMovie = movieService.searchMovie(query);
        return searchedMovie;
    }
	
	@GetMapping("/details/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public Movie getMovieDetails(@PathVariable Long movieId) {
        return movieService.getMovieDetails(movieId);
    }
	
	@GetMapping("/addToFavorits/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public Movie addToFavorits(@PathVariable Long movieId) {
		
        return movieService.addToFavorits(movieId);
    }
	
	@GetMapping("/checkIfFavorite/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public boolean checkIfFavorite(@PathVariable Long movieId) {
		
        return movieService.checkIfFavorite(movieId);
    }
	
	@GetMapping("/removeFromFavorits/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public boolean removeFromFavorits(@PathVariable Long movieId) {
        return movieService.removeFromFavorits(movieId);
    }
	
	@GetMapping("/shareImdbLink/{movieTitle}")
	@CrossOrigin(origins = "http://localhost:3002")
    public String shareIMDbLink(@PathVariable String movieTitle) {
        String imdbLink = "https://www.imdb.com/find?q=" + movieTitle.replace(" ", "+");
        return imdbLink;
    }
	
	@GetMapping("/cast/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public ResponseEntity<Map<String, Object>> movieCast(@PathVariable Long movieId) {
		
		ResponseEntity<Map<String, Object>> movieCast = movieService.movieCast(movieId);
        return movieCast;
    }
	
	@GetMapping("/posters/{movieId}")
    public ResponseEntity<Map<String, Object>> moviePosters(@PathVariable Long movieId) {
		ResponseEntity<Map<String, Object>> moviePosters = movieService.moviePosters(movieId);
        return moviePosters;
    }
	
	@GetMapping("/star/{starId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public Star stars(@PathVariable Long starId) {
        return movieService.stars(starId);
    }
	
	@GetMapping("/starMovies/{starId}")
    public ResponseEntity<Map<String, Object>> starMovies(@PathVariable Long starId) {
		ResponseEntity<Map<String, Object>> starMovies = movieService.starMovies(starId);
        return starMovies;
    }
	
	@GetMapping("/similar/{movieId}")
	@CrossOrigin(origins = "http://localhost:3002")
    public ResponseEntity<List<Map<String, Object>>> getSimilar(@PathVariable Long movieId) throws BaseException{
		ResponseEntity<List<Map<String, Object>>> similarMovies = movieService.getSimilar(movieId);
        return similarMovies;
    }
}

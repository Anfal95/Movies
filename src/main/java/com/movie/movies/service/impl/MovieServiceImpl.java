package com.movie.movies.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movie.movies.entity.Movie;
import com.movie.movies.entity.Star;
import com.movie.movies.exceptions.BaseException;
import com.movie.movies.repository.MovieRepository;
import com.movie.movies.service.MovieService;


@Service
public class MovieServiceImpl implements MovieService {
	
	private final MovieRepository movieRepository;
	
	@Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public MovieServiceImpl(RestTemplate restTemplate, MovieRepository movieRepository) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }

	public List<Movie> getAllMovies() {
		
		List<Movie> allMovies = new ArrayList<>();

		allMovies = movieRepository.findAll();

        return allMovies;
	}
	
	public ResponseEntity<List<Map<String, Object>>> getLatestMovies() {
        String url = "https://api.themoviedb.org/3/discover/movie?primary_release_year=2024&api_key=" + apiKey;
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        List<Map<String, Object>> movies = (List<Map<String, Object>>) responseBody.get("results");
        
        if(movies.isEmpty()) {
        	throw new BaseException("There are no latest movies", "LATEST_MOVIES_NOT_FOUND");
        }
        
        return ResponseEntity.ok().body(movies);
    }
	
	public ResponseEntity<List<Map<String, Object>>> searchMovie(String query) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + query;
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        List<Map<String, Object>> movies = (List<Map<String, Object>>) responseBody.get("results");
        
        if(movies.isEmpty()) {
        	throw new BaseException("There are no movie with this name", "MOVIE_NOT_FOUND");
        }
        
        return ResponseEntity.ok().body(movies);
    }

	public Movie getMovieDetails(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, Movie.class);

	}

	public Movie addToFavorits(Long movieId) {
		Movie favMovie = getMovieDetails(movieId);
		movieRepository.save(favMovie);
		return favMovie;
	}

	public boolean removeFromFavorits(Long movieId) {
		
		Movie favMovie = movieRepository.findOneById(movieId);
		
		if(favMovie != null) {
			movieRepository.deleteById(movieId);
			
			return true;
		}
		
		return false;
	}
	
	public boolean checkIfFavorite(Long movieId) {
		
		Movie favMovie = movieRepository.findOneById(movieId);
		
		if(favMovie != null) {
			
			return true;
		}
		
		return false;
	}
	
	

	public ResponseEntity<List<Map<String, Object>>> getSimilar(Long movieId) throws BaseException{
		
		String url = "https://api.themoviedb.org/3/movie/"+ movieId +"/similar?api_key=" + apiKey;
		
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        List<Map<String, Object>> similatMovies = (List<Map<String, Object>>) responseBody.get("results");
        
//        if(similatMovies.isEmpty()) {
//        	throw new BaseException("There are no similar movies for this movie", "SIMILAR_MOVIE_NOT_FOUND");
//        }
        
        return ResponseEntity.ok().body(similatMovies);
	}
	
	public ResponseEntity<Map<String, Object>> movieCast(Long movieId) {
			
		String url = "https://api.themoviedb.org/3/movie/"+ movieId +"/credits?api_key=" + apiKey;
		
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        Map<String, Object> movieCast = responseBody;
        
        return ResponseEntity.ok().body(movieCast);
	}
	
	public ResponseEntity<Map<String, Object>> moviePosters(Long movieId) {
		
		String url = "https://api.themoviedb.org/3/movie/"+ movieId +"/images?api_key=" + apiKey;
		
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        Map<String, Object> moviePosters = responseBody;

        
        return ResponseEntity.ok().body(moviePosters);
	}

	public Star stars(Long starId) {
		String url = "https://api.themoviedb.org/3/person/" + starId + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, Star.class);
	}

	public ResponseEntity<Map<String, Object>> starMovies(Long starId) {
		String url = "https://api.themoviedb.org/3/person/"+ starId +"/movie_credits?api_key=" + apiKey;
		
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

        // Extract the list of movies from the response
        Map<String, Object> responseBody = responseEntity.getBody();
        Map<String, Object> starMovies = responseBody;
        
        
        if(starMovies.isEmpty()) {
        	throw new BaseException("There are no movies for this star", "STAR_MOVIES_NOT_FOUND");
        }
        
        return ResponseEntity.ok().body(responseBody);
	}

}

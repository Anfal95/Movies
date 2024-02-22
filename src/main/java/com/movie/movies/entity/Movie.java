package com.movie.movies.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "movie")
@AllArgsConstructor
public class Movie {
	
	@Id
    private Long id;
//	private String Name;
//	private String Description;
//	private String Poster;
//	private String Genres;	
//	private boolean fav;
//	private Date release_date;
	private String title;
    private String overview;
    private String release_date;
    private String poster_path;
    
    
    private List<Map<String,Object>> genres;
    
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public List<Map<String, Object>> getGenres() {
		return genres;
	}
	public void setGenres(List<Map<String, Object>> genres) {
		this.genres = genres;
	}
	
	
	
    
}

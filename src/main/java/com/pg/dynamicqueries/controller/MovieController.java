/**
 * 
 */
package com.pg.dynamicqueries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pg.dynamicqueries.entity.Movie;
import com.pg.dynamicqueries.repository.MovieRepository;
import com.pg.dynamicqueries.service.GenericJpaSpecification;
import com.pg.dynamicqueries.util.SearchCriteria;
import com.pg.dynamicqueries.util.SearchOperation;
import com.pg.dynamicqueries.util.SearchRequest;

/**
 * @author Praroop Gupta
 *
 */

@RestController
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping("/api/movies")
	public ResponseEntity<List<Movie>> getMovieDetails(
			@RequestParam(value = "searchRequest", required = true) String searchRequest) {
		Gson gson = new Gson();
		SearchRequest request = gson.fromJson(searchRequest , SearchRequest.class);
		GenericJpaSpecification<Movie> query = new GenericJpaSpecification<>();
		query.add(new SearchCriteria("genre", request.getGenre(), SearchOperation.EQUALS_IGNORE_CASE));
		return new ResponseEntity<>(movieRepository.findAll(query), new HttpHeaders(), HttpStatus.OK);

	}
}

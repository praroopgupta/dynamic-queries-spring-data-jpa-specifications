package com.pg.dynamicqueries;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pg.dynamicqueries.entity.Movie;
import com.pg.dynamicqueries.repository.MovieRepository;
import com.pg.dynamicqueries.service.MovieSpecification;
import com.pg.dynamicqueries.util.SearchCriteria;
import com.pg.dynamicqueries.util.SearchOperation;

@SpringBootApplication
public class DynamicQueriesSpringDataJpaSpecificationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicQueriesSpringDataJpaSpecificationsApplication.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@Bean
    public CommandLineRunner specificationsDemo(MovieRepository movieRepository) {
		
        return args -> {

            // create new movies
        	/**
            movieRepository.saveAll(Arrays.asList(
                    new Movie("Now You See Me", "Drama", 8.2, new Date(2010-1900, 11, 21)),
                    new Movie("The Godfather 2", "Crime", 8.7, new Date(2016-1900, 01, 21)),
                    new Movie("Goal", "Sport", 7.3, new Date(2010-1900, 11, 28)),
                    new Movie("Iron Man 2", "Action", 9.3, new Date(2019-1900, 11, 21)),
                    new Movie("Joker 2", "Drama", 8.4, new Date(2016-1900, 11, 21)),
                    new Movie("Hulk", "Action", 8.9, new Date(2010-1900, 04, 21))
            ));
            */
        	
            // search movies
            MovieSpecification msTitleRating = new MovieSpecification();
            msTitleRating.add(new SearchCriteria("title", "", SearchOperation.MATCH));
            //msTitleRating.add(new SearchCriteria("rating", "9",SearchOperation.GREATER_THAN));
            msTitleRating.add(new SearchCriteria("releaseDate", "", SearchOperation.BEFORE));
            //msTitleRating.add(new SearchCriteria("releaseDate", null, new Date(2016-1900, 02, 01), SearchOperation.AFTER));
            List<Movie> msTitleRatingList = movieRepository.findAll(msTitleRating);
            for(Movie movie: msTitleRatingList) {
            	System.out.println("Title: "+movie.getTitle()+"\tRating: "+movie.getRating()+"\tGenre: "+movie.getGenre()+"\tReleased Date: "+movie.getReleaseDate());
            }
			
         };
    }
}

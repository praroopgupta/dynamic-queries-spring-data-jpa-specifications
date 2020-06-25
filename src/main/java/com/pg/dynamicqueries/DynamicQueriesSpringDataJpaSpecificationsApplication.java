package com.pg.dynamicqueries;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pg.dynamicqueries.entity.Movie;
import com.pg.dynamicqueries.repository.MovieRepository;

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
        	
            movieRepository.saveAll(Arrays.asList(
                    new Movie("Now You See Me", "Drama", 8.2, new Date(2010-1900, 11, 21)),
                    new Movie("The Godfather 2", "Crime", 8.7, new Date(2016-1900, 01, 21)),
                    new Movie("Goal", "Sport", 7.3, new Date(2010-1900, 11, 28)),
                    new Movie("Iron Man 2", "Action", 9.3, new Date(2019-1900, 11, 21)),
                    new Movie("Joker 2", "Drama", 8.4, new Date(2016-1900, 11, 21)),
                    new Movie("Hulk", "Action", 8.9, new Date(2010-1900, 04, 21))
            ));
            	
         };
    }
}

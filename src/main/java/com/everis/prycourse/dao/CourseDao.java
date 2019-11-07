package com.everis.prycourse.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.prycourse.documents.Course;

import reactor.core.publisher.Flux;

public interface CourseDao extends ReactiveMongoRepository<Course, String>{
	
	@Query("{ $or : [ { name : ?0  }, { state : ?0 }]}")
	public Flux<Course> obtenerPorName(String name);
	

}

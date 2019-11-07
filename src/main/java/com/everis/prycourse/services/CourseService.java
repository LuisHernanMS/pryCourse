package com.everis.prycourse.services;

import com.everis.prycourse.documents.Course;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService {
	
	public Flux<Course> findAll();
	
	public Mono<Course> findById(String id);
	
	public Flux<Course> findByName(String name);
	
	public Mono<Course> save(Course course);
	
	public Mono<Void> delete(Course course);

}

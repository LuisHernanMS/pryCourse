package com.everis.prycourse.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.everis.prycourse.documents.Course;
import com.everis.prycourse.services.CourseService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	@Autowired
	private CourseService service;
	
	
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), Course.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> forname(ServerRequest request){
		String name = request.pathVariable("name");
		
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(service.findByName(name), Course.class)
					.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	
	
	public Mono<ServerResponse> crear(ServerRequest request){
		
		Mono<Course> course= request.bodyToMono(Course.class);
		
		return course.flatMap(p->{
				return service.save(p);
		}).flatMap(p->ServerResponse.created(URI.create("api/course/".concat(p.getId())))
				.body(fromObject(p)));
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<Course> student= request.bodyToMono(Course.class);
		String id = request.pathVariable("id");
		
		Mono<Course> courseDB = service.findById(id);
		
		return courseDB.zipWith(student, (db,req)->{
			db.setName(req.getName());
			db.setMaximum(req.getMaximum());
			db.setMinimum(req.getMinimum());
			db.setnDTeacher(req.getnDTeacher());
			db.setState(req.getState());
			return db;
		}).flatMap(p->ServerResponse.created(URI.create("/api/course".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(p),Course.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		Mono<Course> courseDB = service.findById(id);
		return courseDB.flatMap(q->service.delete(q).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
}

package com.everis.prycourse.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.everis.prycourse.documents.Course;
import com.everis.prycourse.services.CourseService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import reactor.core.publisher.Mono;

@Component
public class CourseHandler {
	
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
		Mono<Course> course = request.bodyToMono(Course.class);
		return course.flatMap(p -> {
			
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("pryteacher");
			DBCollection dbCollection = db.getCollection("teacher");
			DBObject query = new BasicDBObject("numberDocument", p.getnDTeacher());
			Integer result = dbCollection.find(query).count();
			if(result>0) {
				return service.save(p);
			}else {
				return null;
			}
			
		}).flatMap(p->ServerResponse.created(URI.create("api/v2/course/".concat(p.getId())))
				.body(fromObject(p)));
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		
		Mono<Course> course = request.bodyToMono(Course.class);
		String id = request.pathVariable("id");
		
		Mono<Course> courseBD = service.findById(id);
		
		return courseBD.zipWith(course, (db,req)->{
			db.setMaximum(req.getMaximum());
			db.setMinimum(req.getMinimum());
			db.setName(req.getName());
			db.setState(req.getState());
			db.setnDTeacher(req.getnDTeacher());
			return db;
		}).flatMap(p->ServerResponse.created(URI.create("/api/v2/course".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(p),Course.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		Mono<Course> courseDB = service.findById(id);
		
		return courseDB.flatMap(p->service.delete(p).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

}

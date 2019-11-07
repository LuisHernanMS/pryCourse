package com.everis.prycourse.services;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.prycourse.dao.CourseDao;
import com.everis.prycourse.documents.Course;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseDao dao;

	@Override
	public Flux<Course> findAll() {
		return dao.findAll();
	}

	@Override
	public Flux<Course> findByName(String name) {
		return dao.obtenerPorName(name);
	}

	@Override
	public Mono<Course> save(Course course) {
		
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("pryteacher");
		DBCollection dbCollection = db.getCollection("teacher");
		DBObject query = new BasicDBObject("numberDocument", course.getnDTeacher());
		Integer result = dbCollection.find(query).count();
		if(result>0) {
			return dao.save(course);
		}else {
			return Mono.error(new ServiceUnavailableException("The teacher corresponding to ID "+course.getnDTeacher()+" was not found."));
		}
	}

	@Override
	public Mono<Void> delete(Course course) {
		return dao.delete(course);
	}

	@Override
	public Mono<Course> findById(String id) {
		return dao.findById(id);
	}

}

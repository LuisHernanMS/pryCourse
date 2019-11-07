package com.everis.prycourse;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.everis.prycourse.controller.CourseController;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

	@Bean
	public RouterFunction<ServerResponse> routes(CourseController controller){
		return RouterFunctions.route(GET("/api/course"), controller::listar)
				.andRoute(GET("/api/course/{name}"), controller::forname)
				.andRoute(POST("/api/course"), controller::crear)
				.andRoute(PUT("/api/course/{id}"), controller::editar)
				.andRoute(DELETE("/api/course/{id}"), controller::eliminar);
	}
	
}

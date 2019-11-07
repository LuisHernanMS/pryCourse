package com.everis.prycourse.documents;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course")
public class Course {

	@Id
	private String id;
	
	@NotEmpty
	String name;
	
	@NotEmpty
	String state;
	
	@NotEmpty
	String minimum;
	
	@NotEmpty
	String maximum;
	
	@NotEmpty
	String nDTeacher;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}
	
	

	public String getnDTeacher() {
		return nDTeacher;
	}

	public void setnDTeacher(String nDTeacher) {
		this.nDTeacher = nDTeacher;
	}

	public Course(@NotEmpty String name, @NotEmpty String state, @NotEmpty String minimum, @NotEmpty String maximum, @NotEmpty String nDTeacher) {
		super();
		this.name = name;
		this.state = state;
		this.minimum = minimum;
		this.maximum = maximum;
		this.nDTeacher = nDTeacher;
	}
	
	
	
	//@NotEmpty
	//String nota;
	
	
	
}

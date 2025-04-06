package com.JHU.models;

import java.util.List;

public class Registrar {
	
	private Integer course_number;
	private List<Integer> student_id;
	
	public Registrar(Integer course_number, List<Integer> student_id) {
		super();
		this.course_number = course_number;
		this.student_id = student_id;
	}

	public Integer getCourse_number() {
		return course_number;
	}

	public void setCourse_number(Integer course_number) {
		this.course_number = course_number;
	}

	public List<Integer> getStudent_id() {
		return student_id;
	}

	public void setStudent_id(List<Integer> student_id) {
		this.student_id = student_id;
	}

	
	

}

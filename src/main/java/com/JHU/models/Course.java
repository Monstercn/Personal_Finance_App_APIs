package com.JHU.models;

public class Course {

	private Integer course_number;
	private String course_title;
	
	public Course(Integer course_number, String course_title) {
		super();
		this.course_number = course_number;
		this.course_title = course_title;
	}
	
	public Integer getCourse_number() {
		return course_number;
	}
	public void setCourse_number(Integer course_number) {
		this.course_number = course_number;
	}
	public String getCourse_title() {
		return course_title;
	}
	public void setCourse_title(String course_title) {
		this.course_title = course_title;
	}



}

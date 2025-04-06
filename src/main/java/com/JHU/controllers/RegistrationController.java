package com.JHU.controllers;

import com.JHU.models.Course;
import com.JHU.models.Registrar;
import com.JHU.models.Students;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.validation.Valid;


@RestController
public class RegistrationController {
	Map<Integer, Students> studentList = new HashMap<Integer, Students>();
	Map<Integer, Course> courseList = new HashMap<Integer, Course>();
	Map<Integer, Registrar> registrarList = new HashMap<Integer, Registrar>();
	// private final AtomicLong counter = new AtomicLong();

	public RegistrationController() {
		studentList.put(1, new Students(1, "Tom", "Huchtson", "01/01/2000", "tom@123.com"));
		courseList.put(1, new Course(1, "Course 1"));
		List<Integer> studentList1 = new ArrayList<>();
		studentList1.add(1);
		studentList1.add(2);
		registrarList.put(1, new Registrar(1, studentList1));
	}

	// SECTION 1: student URIs
	// -------------------------------------------------------------------------------------------------
	// get student by ID.
	// Test URL http://localhost:8080/students/get?id=1
	@GetMapping(value = "/api/students/get")
	public ResponseEntity<?> getStudentbyID(@RequestParam(value = "id") int id) {
		Students itemToReturn = null;

		for (Entry<Integer, Students> entry : studentList.entrySet()) {
			Integer cur_id = entry.getKey();
			if (cur_id == id) {
				Students s = studentList.get(id);
				return ResponseEntity.ok(s);
			}
		}
		String message = "ID provided does not exist.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	// get student by ID.
	// Test URL http://localhost:8080/students/getall
	@GetMapping(value = "/api/students/getall")
	public ResponseEntity<?> getStudentList() {
		return ResponseEntity.ok(studentList);
	}

	// Create new student,
	// Test URL http://localhost:8080/students/add
	// Test JSON {"student_id":2,
	// "first_name":"Adam","last_name":"Smart","dateOfBirth":"01/01/2021","email":"abc@ase.com"}
	@PostMapping(value = "/api/students/add")
	public ResponseEntity<?> addToStudentList(@Valid @RequestBody Students student) {
		Integer student_id = student.getStudent_id();
		String firstName = student.getFirst_name();
		String lastName = student.getLast_name();
		String dateOfBirth = student.getDateOfBirth();
		String email = student.getEmail();
		String message = null;

		// Validate birthday follows MM/DD/YYYY format
		if (!isValidate_date(dateOfBirth)) {
			message = "Invalid birth date format, please follow MM/DD/YYYY";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Validate email format
		else if (!isValidEmail(email)) {
			message = "Invalid email format, please follow **@**.***";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Validate if all required fields are provided
		else if (student_id == 0 || lastName == null || dateOfBirth == null || email == null) {
			message = "Only first name is optional. Please provide all other information";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Passed all validation, add student
		else {
			for (Entry<Integer, Students> entry : studentList.entrySet()) {
				Integer cur_id = entry.getKey();
				if (cur_id == student_id) {
					studentList.put(cur_id, student);
					message = "Student data updated: First Name = " + firstName;
				} else {
					message = "Student added: First Name = " + firstName;
					studentList.put(student_id, student);
				}
			}
			return ResponseEntity.ok(message);
		}
	}

	// Delete student
	// Test URL http://localhost:8080/students/del?id=1
	@DeleteMapping(value = "/apistudents/del")
	public ResponseEntity<?> removeStudent(@RequestParam(value = "id") Integer id) {
		String message = null;
		for (Entry<Integer, Students> entry : studentList.entrySet()) {
			Integer cur_id = entry.getKey();
			if (cur_id == id) {
				studentList.remove(id);
				message = "Student deleted: First Name = " + entry.getValue().getFirst_name();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			}
		}
		message = "Student not found ";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	// SECTION 2: Course URIs
	// -------------------------------------------------------------------------------------------------
	// get course by ID.
	// Test URL http://localhost:8080/courses/get?id=1
	@GetMapping(value = "/api/courses/get")
	public ResponseEntity<?> getCoursebyID(@RequestParam(value = "id") int id) {
		Course itemToReturn = null;

		for (Entry<Integer, Course> entry : courseList.entrySet()) {
			Integer cur_id = entry.getKey();
			if (cur_id == id) {
				Course c = courseList.get(id);
				return ResponseEntity.ok(c);
			}
		}
		String message = "ID provided does not exist.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	// get student by ID.
	// Test URL http://localhost:8080/courses/getall
	@GetMapping(value = "/api/courses/getall")
	public ResponseEntity<?> getCourseList() {
		return ResponseEntity.ok(courseList);
	}

	// Create new course,
	// Test URL http://localhost:8080/courses/add
	// Test JSON {"course_number":2,"course_title":"Course2"}
	@PostMapping(value = "/api/courses/add")
	public ResponseEntity<?> addToCourseList(@Valid @RequestBody Course course) {
		Integer courseNum = course.getCourse_number();
		String courseTitle = course.getCourse_title();
		String message = null;

		// Validate if all required fields are provided
		if (courseNum == 0 || courseTitle == null) {
			message = "Please provide all course information";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Passed all validation, add student
		else {
			for (Entry<Integer, Course> entry : courseList.entrySet()) {
				Integer cur_id = entry.getKey();
				if (cur_id == courseNum) {
					courseList.put(cur_id, course);
					message = "Course data updated: Course Name = " + courseTitle;
				} else {
					message = "Student added: Course Name = " + courseTitle;
					courseList.put(courseNum, course);
				}
			}
			return ResponseEntity.ok(message);
		}
	}

	// Delete student
	// Test URL http://localhost:8080/courses/del?id=1
	@DeleteMapping(value = "/api/courses/del")
	public ResponseEntity<?> removeCourse(@RequestParam(value = "id") Integer id) {
		String message = null;
		for (Entry<Integer, Course> entry : courseList.entrySet()) {
			Integer cur_id = entry.getKey();
			if (cur_id == id) {
				courseList.remove(id);
				message = "Course deleted: Course Name = " + entry.getValue().getCourse_title();
				return ResponseEntity.ok(message);
			}
		}
		message = "Course not found ";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	// SECTION 3: Registrar services
	// -------------------------------------------------------------------------------------------------
	// Add registrar
	// Test json body {"course_number":1,"student_id": [1]}
	@PostMapping(value = "/api/registrar/add")
	public ResponseEntity<?> addregistrarList(@Valid @RequestBody Registrar registrar) {
		Integer courseNum = registrar.getCourse_number();
		Integer studentId = registrar.getStudent_id().get(0);
		String message = null;

		// Validate if all required fields are provided
		if (courseNum == 0 || studentId == 0) {
			message = "Please provide course and student info";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Validate and add registration if passing following validations
		else {
			for (Entry<Integer, Registrar> entry : registrarList.entrySet()) {
				Integer cur_id = entry.getKey();
				if (cur_id == courseNum) {
					if (entry.getValue().getStudent_id().contains(studentId)) {
						message = "Student already registered to this course";
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
					} else if (entry.getValue().getStudent_id().size() >= 15) {
						message = "Max registration (15) has reached. ";
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
					} else {
						entry.getValue().getStudent_id().add(studentId);
						message = "Student registered to course";
						return ResponseEntity.ok(registrarList);
					}
				}
			}
			message = "You are the first student register to current course";
			registrarList.put(courseNum, registrar);
			return ResponseEntity.ok(registrarList);
		}
	}

	// get registrar by ID.
	// Test URL http://localhost:8080/registrar/get?id=1
	@GetMapping(value = "/api/registrar/get")
	public ResponseEntity<?> getRegistrarbyID(@RequestParam(value = "id") int id) {
		Registrar itemToReturn = null;

		for (Entry<Integer, Registrar> entry : registrarList.entrySet()) {
			Integer cur_id = entry.getKey();
			if (cur_id == id) {
				Registrar r = registrarList.get(id);
				return ResponseEntity.ok(r);
			}
		}
		String message = "ID provided does not exist.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	// Delete student
	// Test URL http://localhost:8080/registrar/del 
	@DeleteMapping(value = "/api/registrar/del")
	public ResponseEntity<?> delregistrarList(@Valid @RequestBody Registrar registrar) {
		Integer courseNum = registrar.getCourse_number();
		Integer studentId = registrar.getStudent_id().get(0);
		String message = null;

		// Validate if all required fields are provided
		if (courseNum == 0 || studentId == 0) {
			message = "Please provide course and student info";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		// Validate and add registration if passing following validations
		else {
			for (Entry<Integer, Registrar> entry : registrarList.entrySet()) {
				Integer cur_id = entry.getKey();
				if (cur_id == courseNum) {
					if (entry.getValue().getStudent_id().contains(studentId)) {
						entry.getValue().getStudent_id().remove(studentId);
						message = "Student delete from registrar";
						return ResponseEntity.ok(message);
					}
				}
			}
			message = "student has not registered to this course";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}

	// SECTION 4: Supporting functions
	// -------------------------------------------------------------------------------------------------
	// Date validation function
	public static Boolean isValidate_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		try {
			LocalDate.parse(date, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Basic format
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
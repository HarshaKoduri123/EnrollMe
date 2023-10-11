package com.example.EnrollMe.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


import com.example.EnrollMe.objects.CourseDTO;
import com.example.EnrollMe.objects.CourseEnrolmentDTO;
import com.example.EnrollMe.queryresults.CourseEnrolmentQueryResult;
import com.example.EnrollMe.services.CourseEnrolmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EnrollMe.models.Course;
import com.example.EnrollMe.requests.CourseEnrolmentRequest;

@RestController
@RequestMapping("/api/v1/enrollments")
public class CourseEnrolmentController {
    private final CourseEnrolmentService courseEnrolmentService;
    

    public CourseEnrolmentController(CourseEnrolmentService courseEnrolmentService) {
        this.courseEnrolmentService = courseEnrolmentService;
        
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> enrollments(Principal principal) {
        List<Course> courses = courseEnrolmentService.getAllEnrolledCoursesByUsername(principal.getName());

        List<CourseDTO> responseCourses = courses.stream().map(
                (course) -> {
                    CourseDTO responseCourse = new CourseDTO();

                    responseCourse.setIdentifier(course.getIdentifier());
                    responseCourse.setTitle(course.getTitle());
                    responseCourse.setTeacher(course.getTeacher());
                   
                    responseCourse.setEnrolled(true);

                    return responseCourse;
                }
        ).collect(Collectors.toList());

        return new ResponseEntity<>(responseCourses, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CourseEnrolmentDTO> enrollIn(@RequestBody CourseEnrolmentRequest request, Principal principal) {
        CourseEnrolmentQueryResult enrolment = courseEnrolmentService.enrollIn(principal.getName(), request.getCourseIdentifier());

        CourseEnrolmentDTO responseEnrolment = new CourseEnrolmentDTO();

        responseEnrolment.setName(enrolment.getUser().getName());
        responseEnrolment.setUsername(enrolment.getUser().getUsername());
        responseEnrolment.setCourse(enrolment.getCourse());

        return new ResponseEntity<>(responseEnrolment, HttpStatus.OK);
    }
}

package com.example.EnrollMe.services;

import com.example.EnrollMe.models.Course;
import com.example.EnrollMe.queryresults.CourseEnrolmentQueryResult;
import com.example.EnrollMe.repositories.CourseRepository;
import com.example.EnrollMe.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseEnrolmentService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseEnrolmentService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<Course> getAllEnrolledCoursesByUsername(String username) {
        return courseRepository.findAllEnrolledCoursesByUsername(username);
    }

    public boolean getEnrolmentStatus(String username, String courseIdentifier) {
        return userRepository.findEnrolmentStatus(username, courseIdentifier);
    }

    public CourseEnrolmentQueryResult enrollIn(String username, String courseIdentifier) {
        return userRepository.createEnrolmentRelationship(username, courseIdentifier);
    }
}

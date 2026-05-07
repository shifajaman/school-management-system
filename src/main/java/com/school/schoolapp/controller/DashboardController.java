package com.school.schoolapp.controller;

import com.school.schoolapp.repository.StudentRepository;
import com.school.schoolapp.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DashboardController(StudentRepository studentRepository,
                               CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("studentCount", studentRepository.count());
        model.addAttribute("courseCount", courseRepository.count());

        return "dashboard";
    }
}
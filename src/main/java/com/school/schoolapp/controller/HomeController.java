package com.school.schoolapp.controller;

import com.school.schoolapp.repository.CourseRepository;
import com.school.schoolapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("studentCount",
                studentRepository.count());

        model.addAttribute("courseCount",
                courseRepository.count());

        return "home";
    }
}
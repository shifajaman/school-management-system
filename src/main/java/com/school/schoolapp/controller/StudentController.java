package com.school.schoolapp.controller;

import com.school.schoolapp.entity.Course;
import com.school.schoolapp.entity.Student;
import com.school.schoolapp.repository.CourseRepository;
import com.school.schoolapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseRepository.findAll());
        return "add-student";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student,
                              @RequestParam Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow();
        student.setCourse(course);
        studentRepository.save(student);

        return "redirect:/students/list";
    }

    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students/list";
    }
}
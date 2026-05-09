package com.school.schoolapp.controller;

import com.school.schoolapp.entity.Course;
import com.school.schoolapp.entity.Student;
import com.school.schoolapp.repository.CourseRepository;
import com.school.schoolapp.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // LIST STUDENTS
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "student-list";
    }

    // ADD FORM
    @GetMapping("/add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseRepository.findAll());
        return "add-student";
    }

    // SAVE STUDENT (WITH VALIDATION)
    @PostMapping("/save")
    public String saveStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            @RequestParam(required = false) Long courseId,
            Model model) {

        if (courseId == null) {
            result.rejectValue("course", "error.student", "Course is required");
        }

        if (result.hasErrors()) {
            model.addAttribute("courses", courseRepository.findAll());
            return "add-student";
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.setCourse(course);
        studentRepository.save(student);

        return "redirect:/students/list";
    }

    // EDIT STUDENT
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("student", student);
        model.addAttribute("courses", courseRepository.findAll());
        return "add-student";
    }

    // DELETE STUDENT
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students/list";
    }
}
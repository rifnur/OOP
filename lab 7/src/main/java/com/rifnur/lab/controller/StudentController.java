package com.rifnur.lab.controller;

import com.rifnur.lab.persist.RoleRepository;
import com.rifnur.lab.service.StudentRepr;
import com.rifnur.lab.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    private final RoleRepository roleRepository;

    @Autowired
    public StudentController(StudentService studentService, RoleRepository roleRepository) {
        this.studentService = studentService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                           @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField) {
        logger.info("List page requested");

        Page<StudentRepr> students = studentService.findWithFilter(
                nameFilter.orElse(null),
                ageMinFilter.orElse(null),
                ageMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(3),
                sortField.orElse(null)
        );
        model.addAttribute("students", students);
        return "student";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model,
                           Authentication auth, HttpServletRequest req) {
        logger.info("Edit page for id {} requested", id);

        // auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().stream().anyMatch(ath -> ath.getAuthority().equals("ROLE_ADMIN"));
        req.isUserInRole("ROLE_ADMIN");

        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("student", studentService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "student_form";
    }

    @Secured({"SUPER_ADMIN"})
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("student") StudentRepr student, BindingResult result, Model model) {
        logger.info("Update endpoint requested");

        model.addAttribute("roles", roleRepository.findAll());
        if (result.hasErrors()) {
            return "student_form";
        }
        if (!student.getPassword().equals(student.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "student_form";
        }

        logger.info("Updating student with id {}", student.getId());
        studentService.save(student);
        return "redirect:/student";
    }

    @GetMapping("/new")
    public String create(Model model) {
        logger.info("Create new student request");

        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("student", new StudentRepr());
        return "student_form";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Long id) {
        logger.info("Student delete request");

        studentService.delete(id);
        return "redirect:/student";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}

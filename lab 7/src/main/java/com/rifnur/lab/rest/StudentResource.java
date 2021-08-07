package com.rifnur.lab.rest;

import com.rifnur.lab.controller.BadRequestException;
import com.rifnur.lab.controller.NotFoundException;
import com.rifnur.lab.service.StudentRepr;
import com.rifnur.lab.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// http://localhost:8080/spring-mvc-app/swagger-ui/index.html
// http://localhost:8080/spring-mvc-app/v3/api-docs/

// https://www.baeldung.com/spring-rest-openapi-documentation

@Tag(name = "Student resource API", description = "API to manipulate Student resource ...")
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/v1/student")
public class StudentResource {

    private final StudentService studentService;

    @Autowired
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<StudentRepr> findAll() {
        return studentService.findAll().stream()
                .peek(u -> u.setPassword(null))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public StudentRepr findById(@PathVariable("id") Long id) {
        StudentRepr studentRepr = studentService.findById(id)
                .orElseThrow(NotFoundException::new);
        studentRepr.setPassword(null);
        return studentRepr;
    }

    @GetMapping("filter")
    public Page<StudentRepr> listPage(
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                           @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                           @Parameter(example = "1") @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField) {

        return studentService.findWithFilter(
                nameFilter.orElse(null),
                ageMinFilter.orElse(null),
                ageMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(3),
                sortField.orElse(null)
        );
    }

    @Secured("SUPER_ADMIN")
    @PostMapping(consumes = "application/json")
    public StudentRepr create(@RequestBody StudentRepr studentRepr) {
        if (studentRepr.getId() != null) {
            throw new BadRequestException();
        }
        studentService.save(studentRepr);
        return studentRepr;
    }

    @Secured("SUPER_ADMIN")
    @PutMapping(consumes = "application/json")
    public void update(@RequestBody StudentRepr studentRepr) {
        if (studentRepr.getId() == null) {
            throw new BadRequestException();
        }
        studentService.save(studentRepr);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        studentService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.NOT_FOUND);
    }
}

package com.rifnur.lab.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentRepr> findAll();

    Page<StudentRepr> findWithFilter(String nameFilter, Integer minAge, Integer maxAge,
                                     Integer page, Integer size, String sortField);

    Optional<StudentRepr> findById(long id);

    void save(StudentRepr student);

    void delete(long id);
}

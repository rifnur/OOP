package com.rifnur.lab.service;

import com.rifnur.lab.persist.Student;
import com.rifnur.lab.persist.StudentRepository;
import com.rifnur.lab.persist.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<StudentRepr> findAll() {
        return studentRepository.findAll().stream()
                .map(StudentRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<StudentRepr> findWithFilter(String nameFilter, Integer minAge, Integer maxAge,
                                            Integer page, Integer size, String sortField) {
        Specification<Student> spec = Specification.where(null);
        if (nameFilter != null && !nameFilter.isBlank()) {
            spec = spec.and(StudentSpecification.nameLike(nameFilter));
        }
        if (minAge != null) {
            spec = spec.and(StudentSpecification.minAge(minAge));
        }
        if (maxAge != null) {
            spec = spec.and(StudentSpecification.maxAge(maxAge));
        }
        if (sortField != null && !sortField.isBlank()) {
            return studentRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                    .map(StudentRepr::new);
        }
        return studentRepository.findAll(spec, PageRequest.of(page, size))
                .map(StudentRepr::new);
    }

    @Transactional
    @Override
    public Optional<StudentRepr> findById(long id) {
       return studentRepository.findById(id)
                .map(StudentRepr::new);
    }

    @Transactional
    @Override
    public void save(StudentRepr student) {
        Student studentToSave = new Student(student);
        studentToSave.setPassword(passwordEncoder.encode(studentToSave.getPassword()));
        studentRepository.save(studentToSave);
        if (student.getId() == null) {
            student.setId(studentToSave.getId());
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        studentRepository.deleteById(id);
    }
}

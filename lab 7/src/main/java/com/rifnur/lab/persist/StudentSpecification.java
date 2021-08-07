package com.rifnur.lab.persist;

import org.springframework.data.jpa.domain.Specification;

public final class StudentSpecification {

    public static Specification<Student> nameLike(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Student> minAge(Integer minAge) {
        return (root, query, cb) -> cb.ge(root.get("age"), minAge);
    }

    public static Specification<Student> maxAge(Integer maxAge) {
        return (root, query, cb) -> cb.le(root.get("age"), maxAge);
    }
}

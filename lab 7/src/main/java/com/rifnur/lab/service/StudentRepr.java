package com.rifnur.lab.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rifnur.lab.persist.Role;
import com.rifnur.lab.persist.Student;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// DTO
public class StudentRepr {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @JsonIgnore
    @NotEmpty
    private String matchingPassword;

    private Integer age;

    private Set<Role> roles;

    public StudentRepr() {
    }

    public StudentRepr(String name) {
        this.name = name;
    }

    public StudentRepr(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.roles = new HashSet<>(student.getRoles());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRepr studentRepr = (StudentRepr) o;
        return id.equals(studentRepr.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

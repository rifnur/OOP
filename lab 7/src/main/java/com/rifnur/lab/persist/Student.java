package com.rifnur.lab.persist;

import com.rifnur.lab.service.StudentRepr;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
@NamedQuery(name = "studentsWithRoles",
        query = "select new com.rifnur.lab.persist.StudentRole(s.name, r.name) " +
                "from Student s" +
                " left join s.roles r")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, length = 512)
    private String password;

    @Column
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "students_roles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(StudentRepr user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.age = user.getAge();
        this.roles = user.getRoles();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String toString() {
        return"Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}

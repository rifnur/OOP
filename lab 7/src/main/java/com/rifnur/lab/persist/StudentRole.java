package com.rifnur.lab.persist;

public class StudentRole {

    private final String name;

    private final String roleName;

    public StudentRole(String name, String roleName) {
        this.name = name;
        this.roleName = roleName;
    }

    public String getUsername() {
        return name;
    }

    public String getRoleName() {
        return roleName;
    }
}

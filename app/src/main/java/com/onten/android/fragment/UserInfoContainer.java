package com.onten.android.fragment;

/**
 * Created by Peiying.Lim on 17/3/2017.
 */

public class UserInfoContainer {

    String adminno;
    String name;
    String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminno() {
        return adminno;
    }

    public void setAdminno(String adminno) {
        this.adminno = adminno;
    }

    String password;
    String course;
}

package com.empRadar.ui.user;

public class UserCureentEA {
    private String uid;
    private String photo;
    private String uri;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String pass;
    private String department;


    public UserCureentEA() {
    }

    public UserCureentEA(String uid, String photo, String uri, String name, String email, String phone, String location, String pass, String department) {
        this.uid = uid;
        this.photo = photo;
        this.uri = uri;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.pass = pass;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

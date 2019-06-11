package com.kit.megaphone.datas;

public class UserData {

    private String email;
    private String name;
    private String age;
    private String address;

    public UserData() {}

    public UserData(String email, String name, String age, String address) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.address = address;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

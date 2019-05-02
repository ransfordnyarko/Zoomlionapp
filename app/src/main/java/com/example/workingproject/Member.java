package com.example.workingproject;

public class Member {
    private  String mail;
    private String code;

    public Member() {
    }


    public Member(String mail, String code) {
        this.mail = mail;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

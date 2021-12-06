package com.frederik.springboot.cruddemo.entity;

public class Login {
    
    private String email;
    private String pw;
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    
    public Login() {
	
    }

}

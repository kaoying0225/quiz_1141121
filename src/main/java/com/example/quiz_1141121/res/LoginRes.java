package com.example.quiz_1141121.res;



public class LoginRes extends BasicRes {

	private String email;
	
	private String name;
	
	private String phone;
	
	private int age;

	public LoginRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRes(String email) {
		super();
		this.email = email;
	}

	public LoginRes(int code, String message, String email, String name, String phone, int age) {
		super(code, message);
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.age = age;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}

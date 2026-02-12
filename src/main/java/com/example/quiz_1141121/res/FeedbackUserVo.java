package com.example.quiz_1141121.res;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz_1141121.req.AnswerVo;

public class FeedbackUserVo {

	private String userName;
	
	private String phone;
	
	private String email;
	
	private int age;
	
	private LocalDate fillinData;
	
	private List<AnswerVo> answerVoList;
	
	public FeedbackUserVo() {
		super();
	}

	public FeedbackUserVo(String userName, String phone, String email, int age, LocalDate fillinData) {
		super();
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinData = fillinData;
	}

	public FeedbackUserVo(String userName, String phone, String email, int age, LocalDate fillinData,
			List<AnswerVo> answerVoList) {
		super();
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinData = fillinData;
		this.answerVoList = answerVoList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getFillinData() {
		return fillinData;
	}

	public void setFillinData(LocalDate fillinData) {
		this.fillinData = fillinData;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<AnswerVo> getAnswerVoList() {
		return answerVoList;
	}

	public void setAnswerVoList(List<AnswerVo> answerVoList) {
		this.answerVoList = answerVoList;
	}
	
}

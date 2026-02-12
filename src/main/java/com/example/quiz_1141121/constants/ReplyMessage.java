package com.example.quiz_1141121.constants;

public enum ReplyMessage {

	SUCCESS(200, "Success!!"),
	TITLE_ERROR(400, "Title Error!"),
	DESCRIPTION_ERROR(400, "Description Error!"),
	START_DATE_ERROR(400, "Start Date Error!"),
	END_DATE_ERROR(400,"End Date Errow!"),
	QUESTION_ID_ERROR(400, "Question Id Error!"),
	QUESTION_ERROR(400, "Question Error!"),
	TYPE_ERROR(400, "Type Error!"),
	OPTIONS_ERROR(400, "Option Error!"),
	QUIZ_ID_ERROR(400, "Quiz Id Error!"),
	QUIZ_ID_MISSMATCH(400, "Quiz Id Missmatch!"),
	QUIZ_UPDATE_FORBIDDEN(400, "Quiz Update Forbidden!"),
	QUIZ_NOT_FOUND(404, "Quiz Not Found!"),
	USER_EMAIL_ERROR(404, "User Email Errow!"),
	USER_NAME_ERROR(404, "User Name Errow!"),
	USER_AGE_ERROR(404, "User Age Errow!"),
	USER_PASSWORD_ERROR(404, "User Password Errow!"),
	USER_EMAIL_EXISTED(404, "User Email Existed!"),
	ANSWER_REQUIRED(400, "Answer Required!");
	
	private int code;

	private String message;

	private ReplyMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

package com.example.quiz_1141121.entity;

import com.example.quiz_1141121.constants.ValidationMsg;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "question")
@IdClass(value = QuestionId.class)
public class Question {

	@Id
	@Column(name = "quiz_id")
	private int quizId;

	@Min(value = 1, message = ValidationMsg.QUESTION_ID_ERROR)
	@Id
	@Column(name = "question_id")
	private int questionId;

	@NotBlank(message = ValidationMsg.QUESTION_ERROR)
	@Column(name = "question")
	private String question;

	@Column(name = "type")
	private String type;

	@Column(name = "is_required")
	private boolean required;

	@Column(name = "options")
	private String options;

	public Question() {
		super();
	}

	public Question(@Min(value = 1, message = "Question Id Error!") int questionId,
			@NotBlank(message = "Question Error!") String question, String type, boolean required, String options) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.options = options;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

}

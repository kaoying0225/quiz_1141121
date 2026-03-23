package com.example.quiz_1141121.res;

import java.util.List;

import com.example.quiz_1141121.req.AnswerVo;

public class GetAnswerRes extends BasicRes {
	
	private List<AnswerVo> answerVoList;

	public GetAnswerRes() {
		super();
	}

	public GetAnswerRes(int code, String message, List<AnswerVo> answerVoList) {
		super(code, message);
		this.answerVoList = answerVoList;
	}

	public GetAnswerRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public List<AnswerVo> getAnswerVoList() {
		return answerVoList;
	}

	public void setAnswerVoList(List<AnswerVo> answerVoList) {
		this.answerVoList = answerVoList;
	}
}

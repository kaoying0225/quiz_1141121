package com.example.quiz_1141121.res;

import java.util.List;

public class GetFillinbackUserRes extends BasicRes {

	private List<FeedbackUserVo> userVoList;

	public GetFillinbackUserRes() {
		super();
	}

	public GetFillinbackUserRes(int code, String message) {
		super(code, message);
	}

	public GetFillinbackUserRes(int code, String message, List<FeedbackUserVo> userVoList) {
		super(code, message);
		this.userVoList = userVoList;
	}

	public List<FeedbackUserVo> getUserVoList() {
		return userVoList;
	}

	public void setUserVoList(List<FeedbackUserVo> userVoList) {
		this.userVoList = userVoList;
	}
	
}

package com.example.quiz_1141121.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.dao.UserDao;
import com.example.quiz_1141121.entity.User;
import com.example.quiz_1141121.req.LoginReq;
import com.example.quiz_1141121.req.RegisterReq;
import com.example.quiz_1141121.req.UpdatePassword;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.LoginRes;

@Service
public class UserService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserDao userDao;
	
	/* 新增 user */
	public BasicRes Register(RegisterReq req) {
		/* 參數檢查 */
		BasicRes res = checkParms(req);
		if(res != null) {
			return res;
		}
		/* 檢查 email 是否已存在 */
		if(userDao.getEmailCount(req.getEmail()) == 1) {
			return new BasicRes(ReplyMessage.USER_EMAIL_EXISTED.getCode(), //
					ReplyMessage.USER_EMAIL_EXISTED.getMessage());
		}
		/* 新增: 要把密碼加密 */
		try {
			userDao.insert(req.getEmail(), req.getName(), encoder.encode(req.getPassword()),//
					req.getPhone(), req.getAge());
		} catch (Exception e) {
			throw e;
		}
		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
	
	/* 參數檢查 */
	public BasicRes checkParms(RegisterReq req) {
		if(!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getPassword())) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getName())) {
			return new BasicRes(ReplyMessage.USER_NAME_ERROR.getCode(), //
					ReplyMessage.USER_NAME_ERROR.getMessage());
		}
		if(req.getAge() < 18) {
			return new BasicRes(ReplyMessage.USER_AGE_ERROR.getCode(), //
					ReplyMessage.USER_AGE_ERROR.getMessage());
		}
		return null;
	}
	
	// 登入
	public BasicRes login(LoginReq req) {
		if(!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getPassword())) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
		// 判斷帳號是否有在資料庫
		User user = userDao.getByEmail(req.getEmail());
		if(user == null) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());
		}
		/* 比對舊密碼 */
		boolean result = encoder.matches(req.getPassword(), user.getPassword());
		// !result 等同於 result == false
		if (!result) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
	// 取得使用者基本資料
	public LoginRes getUser(String email) {
		User user = userDao.getByEmail(email);
		return new LoginRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), user.getEmail(),//
				user.getName(), user.getPhone(), user.getAge());
	}
	// 更改密碼
	public BasicRes updatePassword(UpdatePassword req) {
		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getPassword()) || !StringUtils.hasText(req.getNewPassword())) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
		// 如果找不到getByEmail()資料的時候 user 會是 null
		User user = userDao.getByEmail(req.getEmail());
		if(user == null) {
			return new BasicRes(ReplyMessage.USER_EMAIL_EXISTED.getCode(), //
					ReplyMessage.USER_EMAIL_EXISTED.getMessage());
		}
		/* 比對舊密碼 */
		boolean result = encoder.matches(req.getPassword(), user.getPassword());
		// !result 等同於 result == false
		if (!result) {
			System.out.println("密碼比對錯誤!!");
			return new BasicRes(ReplyMessage.USER_PASSWORD_ERROR.getCode(), //
					ReplyMessage.USER_PASSWORD_ERROR.getMessage());
		}
		// 更新密碼
		int res = userDao.upsert(req.getEmail(), encoder.encode(req.getNewPassword()));
		/*
		 * update 的語法 其返回的值可以是 void 或 int: 1. int = 0，沒有資料被更新成功 2. int >
		 * 0(被更新成功的資料筆數)，表示有資料被更新成功 可以利用這兩個數值來回傳資料是否有被更新成功，但也可以一律都回傳資料更新成功， 因為 update
		 * 語法正確的情形下，執行都會成功，只是差別在於是否有無資料被更新成功
		 */
		if(res == 0) {
			return new BasicRes(ReplyMessage.USER_PASSWORD_NOT_UPDATE.getCode(), //
					ReplyMessage.USER_PASSWORD_NOT_UPDATE.getMessage());
		}
		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
}

package com.example.quiz_1141121.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141121.entity.Question;
import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.req.DeleteReq;
import com.example.quiz_1141121.req.FeedBackReq;
import com.example.quiz_1141121.req.FillinReq;
import com.example.quiz_1141121.req.LoginReq;
import com.example.quiz_1141121.req.RegisterReq;
import com.example.quiz_1141121.req.UpdatePassword;
import com.example.quiz_1141121.req.UpdateReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.res.FeedBackRes;
import com.example.quiz_1141121.res.GetAnswerRes;
import com.example.quiz_1141121.res.GetFillinbackUserRes;
import com.example.quiz_1141121.res.GetQuestionRes;
import com.example.quiz_1141121.res.GetQuizRes;
import com.example.quiz_1141121.res.LoginRes;
import com.example.quiz_1141121.res.UpdateRes;
import com.example.quiz_1141121.service.FillinService;
import com.example.quiz_1141121.service.QuizService;
import com.example.quiz_1141121.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class QuizController {
	
	private final String ATTRIBUTE_KEY = "check_result";
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private FillinService fillinService;
	
	@Autowired
	private UserService userService;
	
	// 建立問卷
	/* @Valid: 讓 CreateReq 中的屬性限制生效*/
	@PostMapping("/quiz/create")
	public CreateRes create(@Valid @RequestBody CreateReq req) {
		if(req.getQuestionList() != null) {
			System.out.println(req.getQuestionList().size());
			for(Question q : req.getQuestionList()) {
				System.out.println(q.getQuestion() + q.getOptions());
			}
		}else {
			System.out.println("無資料");
		}
		System.out.println("成功接收前端資料，" + req.getStartDate() + req.getEndDate());
		System.out.println("Service是否存在:" + (quizService != null));
		return quizService.create(req);
	}
	
	// 取得列表頁
	@GetMapping("quiz/getAll")
	public GetQuizRes getQuizList() {
		return quizService.getQuizList();
	}
	// 取的問卷裡的資料與問題
	/* API 的路徑： http://localhost:8080/quiz/get_question_list?quizId=1
	 * ?quizId 是 @RequestParam("quizId") 裡面的 quizId */
	@GetMapping("quiz/get_question_list")
	public GetQuestionRes getQuestionList(@RequestParam("quizId") int quizId) {
		return quizService.getQuestionList(quizId);
	}
	
	// 更新問卷
	@PostMapping("quiz/update")
	public UpdateRes Update(@RequestBody UpdateReq req) {
		System.out.println(req.getQuizId() + req.getTitle() + req.getStartDate());
		for(Question q : req.getQuestionList()) {
			System.out.println(q.getQuestion() + q.getOptions());
		}
		return quizService.Update(req);
	}
	// 刪除多筆
	// @Hidden: 可以在 swagger 上隱藏此段
	@Hidden
	@PostMapping("quiz/delete")
	public BasicRes delete(@RequestBody DeleteReq req) {
		return quizService.delete(req);
	}
	// 刪除單筆
	/* API 的路徑： http://localhost:8080/quiz/delete?quizId=1 */
	@GetMapping("quiz/delete")
	public BasicRes delete(@RequestParam("quizId") int quizId) {
		return quizService.delete(quizId);
	}
	// 填寫問卷
	@PostMapping("/quiz/fillin")
	public BasicRes fillin(@RequestBody FillinReq req) {
		return fillinService.fillin(req);
	}
	// 註冊
	@PostMapping(value = "register")
	public BasicRes register(@RequestBody RegisterReq req) {
		return userService.Register(req);
	}
	// 登入
	@PostMapping(value = "login")
	public BasicRes login(@RequestBody LoginReq req, HttpSession session) {
		/* 修改 session_id 的存活時間: 預設時間是30分鐘
		 * 在有效的存活時間內持續有跟 server 溝通，相同的 session_id 就不會失效 */
//		session.setMaxInactiveInterval(600); // 單位: 秒
		BasicRes res = userService.login(req);
		/* 將 成功的結果 存到 session 中: 第一個參數是可自行定義的字串，用在後續取得對應的第二個參數用 */
		session.setAttribute(ATTRIBUTE_KEY, res);
		return res;
	}
	// 取得使用者基本資料
	@GetMapping("get_user")
	public LoginRes getUser(@RequestParam("email") String email) {
		return userService.getUser(email);
	}
	// 取得使用者填寫該問卷的答案
	@PostMapping("/open_quiz_view")
	public GetFillinbackUserRes getAnswerByIdAndEmail(@RequestBody FeedBackReq req) {
		return fillinService.getAnswerByIdAndEmail(req.getQuizId(), req.getEmail());
	}
	// 取得使用者填寫該問卷的答案
	@PostMapping("quiz/feedBack")
	public FeedBackRes feedBack(@RequestBody FeedBackReq req) {
		return fillinService.feedBack(req);
	}
	// 取的該問卷的所有使用者的填寫紀錄
	@PostMapping("/get_all_fillin_users")
	public GetFillinbackUserRes getAllFillinUsers(@RequestBody int quizId) {
		return fillinService.getAllFillinUsers(quizId);
	}
	@PostMapping("/count")
	public int count(@RequestBody FillinReq req) {
		return fillinService.count(req.getQuizId(), req.getEmail());
	}
	// 更新密碼
	@PostMapping("/update_password")
	public BasicRes updatePassword(@RequestBody UpdatePassword req) {
		return userService.updatePassword(req);
	}
	// 取的該問卷的所有答案
	@GetMapping("get_answer")
	public GetAnswerRes GetAnswer(@RequestParam("quizId") int quizId) {
		return quizService.GetAnswer(quizId);
	}
}

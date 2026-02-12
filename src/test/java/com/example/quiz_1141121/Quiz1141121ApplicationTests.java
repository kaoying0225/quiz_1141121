package com.example.quiz_1141121;


import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz_1141121.dao.QuestionDao;
import com.example.quiz_1141121.dao.QuizDao;
import com.example.quiz_1141121.service.QuizService;


@SpringBootTest
class Quiz1141121ApplicationTests {

	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuizService quizService;
	
	@Test
	public void creatTest() {
		quizDao.insert("午餐調查", "午餐調查", LocalDate.of(2026, 2, 15), LocalDate.of(2026, 2, 25), false);
	}
	
	@Test
	public void creatTest1() {
//		quizService.insert(1, 1, "午餐吃什麼", "Single", true, "牛排");
	}

}

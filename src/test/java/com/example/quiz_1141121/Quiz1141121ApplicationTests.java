package com.example.quiz_1141121;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.dao.QuestionDao;
import com.example.quiz_1141121.dao.QuizDao;
import com.example.quiz_1141121.entity.Question;
import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.service.FillinService;
import com.example.quiz_1141121.service.QuizService;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class Quiz1141121ApplicationTests {

	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private FillinService fillinService;
	
	@Autowired
	private QuizService quizService;
	
	/* Before: 用來創建測試資料
	 * All: 創建初始的資料 
	 * Each: 根據每個方法要測試的內容，修改測試資料 */
	@BeforeEach
	public void beforeEachTest() {
		System.out.println("==== Before Each ====");
	}
	
	@BeforeAll
	public void beforeAllTest() {
		System.out.println("==== Before All ====");
	}
	
	/* After: 用來刪除測試資料
	 * All: 最後刪除所有的測試資料 
	 * Each: 可以在每一次的測試之前，將有修改的測試資料再一次初始化 */
	@AfterEach
	public void afterEachTest() {
		System.out.println("==== After Each ====");
	}
	
	@AfterAll
	public void afterAllTest() {
		System.out.println("==== After All ====");
	}
	
	/* 單元測試 */
//	@Test
//	public void test() {
//		List<Question> list = new ArrayList<>(List.of(new Question(1, 1, "午餐調查", "Single", true, "牛排")));
//		CreateReq req = new CreateReq("午餐調查", "午餐調查", LocalDate.of(2026, 5, 15),// 
//				LocalDate.of(2026, 8, 25), false, list);
//		CreateRes res = quizService.create(req);
//		/* 測試邏輯 */
//		/* 測試 tittle 沒資料*/
//		Assert.isTrue(res.getMessage().equals(ReplyMessage.TITLE_ERROR.getMessage()), "測試 tittle 沒資料錯誤!!");
//	}
	
	@Test
	public void creatTest() {
		quizDao.insert("午餐調查", "午餐調查", LocalDate.of(2026, 2, 15), LocalDate.of(2026, 2, 25), false);
	}
	
	// 物件轉為字串
	@Test
	public void objectMapperTest() {
		String urlStr = "https://api.pin-yi.me/taiwan-calendar/2026";
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resStr = template.getForEntity(urlStr, String.class);
		System.out.println(resStr.getStatusCode());
		String resBody = resStr.getBody();
		System.out.println(resBody);
		/* 將字串轉換成物件 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			List list = mapper.readValue(resBody, List.class);
			List<Map<String, Object>> list1 = mapper.readValue(resBody, new TypeReference<>() {});
			System.out.println(list);
			System.out.println(list1);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 物件轉為字串
	@Test
	public void calculateInsurance() {
		Map<String, Object> map = new HashMap<>();
		map.put("date", "20260101");
		map.put("year", "2026");
		System.out.println(map);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String mapStr = mapper.writeValueAsString(map);
			System.out.println(mapStr);
			// map.toString() --> "{date=20260101, year=2026}"
			System.out.println(map.toString());
			System.out.println("==============================");
			Map<String, Object> newMap = mapper.readValue(mapStr, Map.class);
			System.out.println("newMap : " + newMap);
			System.out.println("===================");
			// 下面執行會錯誤
			Map<String, Object> newMap1 = mapper.readValue(map.toString(), Map.class);
			System.out.println("newMap1 : " + newMap1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.example.quiz_1141121.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.constants.Type;
import com.example.quiz_1141121.dao.FillinDao;
import com.example.quiz_1141121.dao.QuestionDao;
import com.example.quiz_1141121.dao.QuizDao;
import com.example.quiz_1141121.entity.Fillin;
import com.example.quiz_1141121.entity.Question;
import com.example.quiz_1141121.entity.Quiz;
import com.example.quiz_1141121.req.AnswerVo;
import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.req.DeleteReq;
import com.example.quiz_1141121.req.UpdateReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.res.GetAnswerRes;
import com.example.quiz_1141121.res.GetQuestionRes;
import com.example.quiz_1141121.res.GetQuizRes;
import com.example.quiz_1141121.res.UpdateRes;

// @EnableScheduling: 使排程生效
@EnableScheduling
@Service
public class QuizService {

	// 日誌(紀錄訊息)
	/* org.slf4j */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private FillinDao fillinDao;
	
	/* 一個方法中若有使用到多個 Dao 或是同一個 Dao 有呼叫多次去對資料作變更(新增、修改、刪除)，
	 * 必須要用 @Transactional，因為這些 Dao 的操作，都屬於同一次的操作，因此資料的變更要嘛全部成功，
	 * 不然就全部失敗，回溯到尚未變更之前
	 * 並且 用了就用了 當失敗 他還是會有使用過
	 * 所以quizId 當失敗後 下一次 他就是 AI 產生的 下一個數值*/
	@Transactional(rollbackFor = Exception.class)
	public CreateRes create(CreateReq req) {
		System.out.println("進入Service" + req.getStartDate());
		/* 參數檢查 */
		CreateRes res = checkParams(req);
		if(res != null) {
			System.out.println("參數檢查不通過");
			return res;
		}
		System.out.println("參數檢查通過");
		try {
			/* 新增問卷(QZ DAO)  -> 取問卷ID(QZ DAO) --> 新增問題(Qtion DAO) */
			// 使用 save?
			quizDao.insert(req.getTitle(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished());
			/* 取得新增問卷的 id */
			int quizId = quizDao.getMaxId();
			System.out.println("取的quizId:" + quizId);
			/* 將 quiz_id 設定到 question 中 */
//		for(Question item : req.getQuestionList()) {
//			item.setQuizId(quizId);
//		}
			/* 因為同樣的 Question item 並且將 quizId 放進去 所以上面的 可以省略了 */
			/* 新增問題 */
			for(Question item : req.getQuestionList()) {
				questionDao.insert(quizId, item.getQuestionId(), item.getQuestion(), //
						item.getType(), item.isRequired(), item.getOptions());
			}
			return new CreateRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			// 主動記錄錯誤訊息
			logger.error(e.getMessage());
			throw e;
		}
//		try {
//			Quiz quiz = quizDao.save(new Quiz(req.getTitle(), req.getDescription(), //
//					req.getStartDate(), req.getEndDate(), req.isPublished()));
//			quizDao.insert(req.getTitle(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished());
//			System.out.println(ReplyMessage.SUCCESS.getMessage());
//		} catch (Exception e) {
//			throw e;
//		}
		
		
	}
	
	private CreateRes checkParams(CreateReq req) {
		/* 參數檢查 */
//		if(!StringUtils.hasText(req.getTitle())) {
//			System.out.println("getTitle錯誤");
//			return new CreateRes(ReplyMessage.TITLE_ERROR.getCode(), //
//					ReplyMessage.TITLE_ERROR.getMessage());
//		}
//		if(!StringUtils.hasText(req.getDescription())) {
//			System.out.println("getDescription錯誤");
//			return new CreateRes(ReplyMessage.DESCRIPTION_ERROR.getCode(), //
//					ReplyMessage.DESCRIPTION_ERROR.getMessage());
//		}
		/* 1. 開始時間不能比今天早 2. 開始時間不能比結束時間晚 */
		if(req.getStartDate() == null || req.getStartDate().isBefore(LocalDate.now()) 
				|| req.getStartDate().isAfter(req.getEndDate())) {
			System.out.println("getStartDate錯誤");
			return new CreateRes(ReplyMessage.START_DATE_ERROR.getCode(), //
					ReplyMessage.START_DATE_ERROR.getMessage());
		}
		/* 1. 結束時間不能比今天早 */
		if(req.getEndDate() == null || req.getEndDate().isBefore(LocalDate.now())) {
			System.out.println("getEndDate錯誤");
			return new CreateRes(ReplyMessage.END_DATE_ERROR.getCode(), //
					ReplyMessage.END_DATE_ERROR.getMessage());
		}
		for(Question item : req.getQuestionList()) {

//			if(item.getQuestionId() <= 0) {
//				System.out.println("getQuestionId錯誤");
//				return new CreateRes(ReplyMessage.QUESTION_ID_ERROR.getCode(), //
//						ReplyMessage.QUESTION_ID_ERROR.getMessage(), item.getQuestionId());
//			}
//			if(!StringUtils.hasText(item.getQuestion())) {
//				System.out.println("getQuestion錯誤");
//				return new CreateRes(ReplyMessage.QUESTION_ERROR.getCode(), //
//						ReplyMessage.QUESTION_ERROR.getMessage(), item.getQuestionId());		
//			}
			
//			if(!StringUtils.hasText(item.getType()) 
//					&& !item.getType().equalsIgnoreCase(Type.SINGLE.getType())
//					|| !item.getType().equalsIgnoreCase(Type.MULTI.getType())
//					|| !item.getType().equalsIgnoreCase(Type.TEXT.getType())) {
//				return new CreateRes(ReplyMessage.TYPE_ERROR.getCode(), //
//						ReplyMessage.TYPE_ERROR.getMessage());		
//			}
			if(!Type.check(item.getType())) {
				System.out.println("getType錯誤");
				return new CreateRes(ReplyMessage.TYPE_ERROR.getCode(), //
						ReplyMessage.TYPE_ERROR.getMessage(), item.getQuestionId());		
			}
			/* 檢查選項`: 因為上面有檢查過 type 一定會是 單、多、簡答 這3種之一，
			 * 所以下面的判斷式可以檢查 type 是 非簡答題 就可以了 */
//			if(item.getType().equalsIgnoreCase(Type.SINGLE.getType())
//					|| item.getType().equalsIgnoreCase(Type.MULTI.getType())) {
//				if(!StringUtils.hasText(item.getOptions())) {
//					return new CreateRes(ReplyMessage.OPTIONS_ERROR.getCode(), //
//							ReplyMessage.OPTIONS_ERROR.getMessage());
//				}
//			}
			if(!item.getType().equalsIgnoreCase(Type.TEXT.getType())) {
				if(!StringUtils.hasText(item.getOptions())) {
					System.out.println("getOptions錯誤");
					return new CreateRes(ReplyMessage.OPTIONS_ERROR.getCode(), //
							ReplyMessage.OPTIONS_ERROR.getMessage(), item.getQuestionId());
				}
			}
			
		}
		System.out.println("參數正確");
		return null;
	}
	
	// 取得列表
	public GetQuizRes getQuizList() {
//		List<Quiz> list = quizDao.getAll(); // 使用匿名
		return new GetQuizRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), quizDao.getAll());
	}
	
	// 取得題目
	public GetQuestionRes getQuestionList(int quizId) {
		return new GetQuestionRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), questionDao.getByQuizId(quizId));
	}
	
	// 更新問卷或問題
	@Transactional(rollbackFor = Exception.class)
	public UpdateRes Update(UpdateReq req) {
		/* 更新要檢查 quizId 因為存在 DB 中的 quizId 一定是大於 0 */
		if(req.getQuizId() <= 0) {
			System.out.println("quizId<=0");
			return new UpdateRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), //
					ReplyMessage.QUIZ_ID_ERROR.getMessage());
		}
		
		/* 檢查 req 中的 quizId 和每個 Question 中的 quizId 是否相同 */
		for(Question item : req.getQuestionList()) {
			if(req.getQuizId() != item.getQuizId()) {
				System.out.println("quizId不相同");
				return new UpdateRes(ReplyMessage.QUIZ_ID_MISSMATCH.getCode(), //
						ReplyMessage.QUIZ_ID_MISSMATCH.getMessage());
			}
		}
		
		/* 檢查更新的問卷是否有存在: 主要取得問卷的發布狀態 */
		Quiz quiz = quizDao.getById(req.getQuizId());
		if(quiz == null) {
			System.out.println("無問卷");
			return new UpdateRes(ReplyMessage.QUIZ_NOT_FOUND.getCode(), //
					ReplyMessage.QUIZ_NOT_FOUND.getMessage());
		}
		
		//當有 || 和 && 時， && 會優先判斷
		/* 檢查是否可以被修改的狀態: 1. 未發布 或 2.已發布且尚未開始(開始日期比現在晚) 
		 * 排除: 已發布且狀態是進行中或已結束 --> 開始日期不是在今天之後 --> 開始日期在今天或今天之前 */
		if(quiz.isPublished() && !req.getStartDate().isAfter(LocalDate.now())) {
			return new UpdateRes(ReplyMessage.QUIZ_UPDATE_FORBIDDEN.getCode(), //
					ReplyMessage.QUIZ_UPDATE_FORBIDDEN.getMessage());
		}
		
		/* 檢查其他參數 */
//		CreateReq res1 = (CreateReq)req;
//		checkParams((CreateReq)req);
		/* 上面可以省略 */
		CreateRes res = checkParams(req);
		System.out.println("參數ok");
		if(res != null) {
			/* 不能將父類別(CreateRes)強制轉型成子類別(UpdateRes)，會報錯
			 * 所以不能寫成 return (UpdateRes)res; */
			System.out.println("res != null");
			return new UpdateRes(res.getCode(), res.getMessage());
		}else {
			System.out.println("res == null");
		}
		try {
			/* 1. 更新問卷 */
			quizDao.update(req.getQuizId(), req.getTitle(), req.getDescription(),//
					req.getStartDate(), req.getEndDate(), req.isPublished());
			/* 2. 刪除同一個 quizId 下的所有 Question */
			questionDao.delete(req.getQuizId());
			/* 3. 新增更新後的問題 */
			for(Question item : req.getQuestionList()) {
				questionDao.insert(req.getQuizId(), item.getQuestionId(), item.getQuestion(), //
						item.getType(), item.isRequired(), item.getOptions());			
			}	
		} catch (Exception e) {
			throw e;
		}
		
		return new UpdateRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	// 刪除問卷(多筆)
	@Transactional(rollbackFor = Exception.class)
	public BasicRes delete(DeleteReq req) {
		/* 檢查參數 */
		if(CollectionUtils.isEmpty(req.getQuizIdList())) {
			return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage());
		}
		for(int id : req.getQuizIdList()) {
			if(id <= 0) {
				return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage());
			}
		}
		try {
			quizDao.delete(req.getQuizIdList());
			questionDao.deleteList(req.getQuizIdList());
		} catch (Exception e) {
			throw e;
		}
		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
	
	// 刪除問卷(單筆)
	@Transactional(rollbackFor = Exception.class)
	public BasicRes delete(int quizId) {
//		List<Integer> list = List.of(quizId);
//		DeleteReq req = new DeleteReq(list);
//		BasicRes res = delete(req);
//		return res;
//		上面可以碼可以濃縮成一行
		return delete(new DeleteReq(List.of(quizId)));
		
//		/* 檢查參數 */
//		if(quizId <= 0) {
//			return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
//					ReplyMessage.SUCCESS.getMessage());
//		}
//		try {
//			quizDao.delete(quizId);
//			questionDao.delete(quizId);
//		} catch (Exception e) {
//			throw e;
//		}
//		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
//				ReplyMessage.SUCCESS.getMessage());
	}
	// 取的該問卷的所有答案
	public GetAnswerRes GetAnswer(int quizId) {
		if(quizId <= 0) {
			return new GetAnswerRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), //
					ReplyMessage.QUIZ_ID_ERROR.getMessage());
		}
		// 取的該問卷的所有問題
		List<Question> quList = questionDao.getByQuizId(quizId);
		// 取的該問卷的所有答案
		List<Fillin> fillinList = fillinDao.getByQuizId(quizId);
		List<AnswerVo> answerVoList = new ArrayList<>();
		for(Fillin fillin: fillinList) {
			for(Question qu: quList) {
				if(fillin.getQuestionId() == qu.getQuestionId()) {
					// 重點：在確對應到題目時，才 new 出一個新的 vo 物件
					AnswerVo vo = new AnswerVo(qu);
					vo.setAnswer(fillin.getAnswer());
					// 這樣加進去的就是獨立且正確的資料了
					answerVoList.add(vo);
					break;
				}
			}
		}
		return new GetAnswerRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), answerVoList);
	}
	
	
//	// 排程寫法 (上方要加@EnableScheduling，@Scheduled才會生效)
//	@Scheduled(fixedRate = 3000)
//	@Scheduled(fixedRate = "${自己取的名稱}") // ${自己取的名稱}要到 application 設定
	public void scheduleTest() {
		// truncatedTo(ChronoUnit.SECONDS): 顯示到秒就好(把毫秒去掉)
		System.out.println(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
	}
	
}

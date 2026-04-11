package com.example.quiz_1141121;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class LambdaTest {

	@Test
	public void test() {
		List<String> list = new ArrayList<>(List.of("A", "B", "C"));
		// foreach
		for(String item : list) {
			System.out.println(item);
		}
		// collection 自訂義 foreach
		list.forEach((item) -> {
			System.out.println(item);
		});
		// 參數個數只有一個時，小括號可以省略 : 沒有參數時，必須要有小括號
		list.forEach(item -> {
			System.out.println(item);
		});
		// 參數個數有2個以上時，必須要有小括號
		Map<Integer, String> map = new HashMap<>(Map.of(1, "A", 2, "B", 3, "C"));
		map.forEach((k, v) -> {
			System.out.printf("%d : %s \n", k, v);
		});
		// ====================
		// foreach 大括號中的程式碼只有一行時，大括號可以省略
		for(String item : list) 
			System.out.println(item);
		// 大括號中的程式碼只有一行時，大括號可以省略且程式碼的後面不能有結尾符號(分號;)
		list.forEach(item -> 
			System.out.println(item)
		);
	}
	
}

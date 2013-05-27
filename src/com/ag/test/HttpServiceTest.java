package com.ag.test;

import java.util.HashMap;
import java.util.Map;

import com.ag.zhaisujie.HttpUtil;

public class HttpServiceTest {

	public static void main(String[] args) throws Exception {
		HttpUtil.getCode("110");
//		HttpUtil.login("110", "11");
		Map map = new HashMap();
		map.put("username", "110");
		map.put("password", "110");
		map.put("id", 1);
		Object result = HttpUtil.getInfoFromServer("getOrderDetail", map);
		System.out.println(result);
	}
}

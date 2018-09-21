package cn.csy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
/**
 * 需要确认的订单列表
 */
@SuppressWarnings("serial")
public class OrdersToConfirmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		List<String> orders = new ArrayList<String>();
		
		HashMap<String,Object> hashMap1 = new HashMap<String,Object>();
		hashMap1.put("合同编号", "Y218-00043");
		hashMap1.put("车号", "167");
		hashMap1.put("浇注方式", "自备泵");
		hashMap1.put("数量", "6.00");
		hashMap1.put("强度等级", "C30");
		hashMap1.put("工程名称", "恒大御景湾");
		hashMap1.put("施工部位", "地面");
		
		HashMap<String,Object> hashMap2 = new HashMap<String,Object>();
		hashMap2.put("合同编号", "Y218-00043");
		hashMap2.put("车号", "168");
		hashMap2.put("浇注方式", "自备泵");
		hashMap2.put("数量", "26.00");
		hashMap2.put("强度等级", "C20");
		hashMap2.put("工程名称", "恒大御景湾");
		hashMap2.put("施工部位", "南墙");
		
		HashMap<String,Object> hashMap3 = new HashMap<String,Object>();
		hashMap3.put("合同编号", "Y218-00043");
		hashMap3.put("车号", "169");
		hashMap3.put("浇注方式", "自备泵");
		hashMap3.put("数量", "16.00");
		hashMap3.put("强度等级", "C15");
		hashMap3.put("工程名称", "恒大御景湾");
		hashMap3.put("施工部位", "北墙");
				
		orders.add(new JSONObject(hashMap1).toString());
		orders.add(new JSONObject(hashMap1).toString());
		orders.add(new JSONObject(hashMap1).toString());		
		
		System.out.println(orders);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}

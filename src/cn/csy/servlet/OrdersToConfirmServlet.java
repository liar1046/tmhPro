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
 * ��Ҫȷ�ϵĶ����б�
 */
@SuppressWarnings("serial")
public class OrdersToConfirmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		List<String> orders = new ArrayList<String>();
		
		HashMap<String,Object> hashMap1 = new HashMap<String,Object>();
		hashMap1.put("��ͬ���", "Y218-00043");
		hashMap1.put("����", "167");
		hashMap1.put("��ע��ʽ", "�Ա���");
		hashMap1.put("����", "6.00");
		hashMap1.put("ǿ�ȵȼ�", "C30");
		hashMap1.put("��������", "���������");
		hashMap1.put("ʩ����λ", "����");
		
		HashMap<String,Object> hashMap2 = new HashMap<String,Object>();
		hashMap2.put("��ͬ���", "Y218-00043");
		hashMap2.put("����", "168");
		hashMap2.put("��ע��ʽ", "�Ա���");
		hashMap2.put("����", "26.00");
		hashMap2.put("ǿ�ȵȼ�", "C20");
		hashMap2.put("��������", "���������");
		hashMap2.put("ʩ����λ", "��ǽ");
		
		HashMap<String,Object> hashMap3 = new HashMap<String,Object>();
		hashMap3.put("��ͬ���", "Y218-00043");
		hashMap3.put("����", "169");
		hashMap3.put("��ע��ʽ", "�Ա���");
		hashMap3.put("����", "16.00");
		hashMap3.put("ǿ�ȵȼ�", "C15");
		hashMap3.put("��������", "���������");
		hashMap3.put("ʩ����λ", "��ǽ");
				
		orders.add(new JSONObject(hashMap1).toString());
		orders.add(new JSONObject(hashMap1).toString());
		orders.add(new JSONObject(hashMap1).toString());		
		
		System.out.println(orders);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}

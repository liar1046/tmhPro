package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.csy.utils.DataUtil;

/**
 * ����ContractId��ȡ��ͬ����ϸ��Ϣ
 * 
 */
@SuppressWarnings("serial")
public class TaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// ��session�л�ȡ��ͬ�ţ���ȡ��ͬ��
		HttpSession session = request.getSession();
		String contractId = (String)session.getAttribute("contractId");
		
		// urlString����Ҫ�޸ģ�����deadcode
		String urlString = "http://125.46.39.104:8088/Client?ContractID="+contractId;
		String data = null;
		try {
			data = DataUtil.getData(urlString);
			out.print(data);
//			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("data", data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

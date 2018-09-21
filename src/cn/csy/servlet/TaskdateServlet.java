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
 * 合同签收量日历 
 *
 * 对应日期的生产量
 */
@SuppressWarnings("serial")
public class TaskdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// 从session中获取合同号，截取合同号
		HttpSession session = request.getSession();
		String contractId = (String) session.getAttribute("contractId");
		System.out.println(contractId);

		// 根据对应接口获取合同签收量及对应日期
		String urlString = "http://125.46.39.104:8088/Client/Fun/getContratCalendar?ContractId=" + contractId;

		String taskDate = null;
		try {
			taskDate = DataUtil.getData(urlString);
			out.print(taskDate);
//			System.out.println(taskDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("taskDate", taskDate);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

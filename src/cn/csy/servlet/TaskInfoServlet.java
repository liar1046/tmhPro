package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.csy.utils.DataUtil;
/**
 * 获取任务的详细信息
 *
 * 根据taskInfo.jsp传过来的taskId获取服务器数据
 */
@SuppressWarnings("serial")
public class TaskInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// 获取后台任务id
		String taskId = request.getParameter("taskId");
//		System.out.println(taskId);
		
		String urlString = "http://125.46.39.104:8088/Client/Fun/getTaskCarList?taskId=" + taskId;
		String taskInfo = null;
		try {
			taskInfo = DataUtil.getData(urlString);
			out.print(taskInfo);
//			System.out.println(taskInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("taskInfo", taskInfo);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

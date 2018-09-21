package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 确认订单
 *
 */
@SuppressWarnings("serial")
public class ConfirmOrderServlet extends HttpServlet {
	
	/**
	 * 用户点击电子签单界面的确认按钮，首先判断用户是否登录，如果没有登录，跳转登录页面，登录后，自动确认订单
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 从session中获取用户登录信息
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if(username != null){
			String confirmAuthority = (String)session.getAttribute("confirmAuthority").toString();
			// 接受页面数据
			String getCarNum = request.getParameter("getCarNum");
			System.out.println("车号：" + getCarNum);
			
			if("1".equals(confirmAuthority)){
				// 跳转页面
				out.write("<h2>确认成功，2秒钟后返回订单列表……</h2>");
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/ordersToConfirm.jsp");
			}else{
				out.write("<h2>您没有改权限，请联系业务员……</h2>");
				response.setHeader("refresh", "1,url=" + request.getContextPath() + "/jsp/ordersToConfirm.jsp");				
			}
		} else{
			// 获取地址栏信息
			if(null != request.getQueryString()){
				session.setAttribute("redirectUrl", request.getRequestURL().append("?").append(request.getQueryString()).toString());
				out.write("<h2>您还未登录，请先登录……，登录成功后，会自动确认订单</h2>");
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/login.jsp");
			}else{
				out.write("<h2>您还未登录，请先登录……，登录成功后，会自动确认订单</h2>");
				session.setAttribute("redirectUrl", request.getRequestURL().toString());
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/login.jsp");
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

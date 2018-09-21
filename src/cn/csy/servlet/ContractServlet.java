package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import cn.csy.utils.DataUtil;
/**
 * 获取合同内容
 *
 * 获取对应接口的合同内容
 */
@SuppressWarnings("serial")
public class ContractServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String contractId = (String) session.getAttribute("contractId");
		
		// urlString变量，获取是个问题
		String urlString = "http://125.46.39.104:8088/Contract/"+contractId;
		String contractData = null;
		try {
			contractData = DataUtil.getData(urlString);
			out.print(contractData);
//			System.out.println(contractData);
			JSONObject jo = new JSONObject(contractData);
			String projectName = (String) jo.get("\u5de5\u7a0b\u540d\u79f0");
			// System.out.println(projectName);

			session.setAttribute("projectName", projectName);
			String sessionid = session.getId(); // 获取sessionid
			Cookie cookie = new Cookie("JSESSIONID", sessionid); // new一个cookie，cookie的名字是JSESSIONID跟带id的cookie一样
			cookie.setPath(request.getContextPath()); // 设置cookie应用范围。getContextPath是获取当前项目的名字。
			cookie.setMaxAge(600000); // 设置有效时间
			response.addCookie(cookie);// 用这个cookie把带id的cookie覆盖掉
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

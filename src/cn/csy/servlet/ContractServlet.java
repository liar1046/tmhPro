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
 * ��ȡ��ͬ����
 *
 * ��ȡ��Ӧ�ӿڵĺ�ͬ����
 */
@SuppressWarnings("serial")
public class ContractServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String contractId = (String) session.getAttribute("contractId");
		
		// urlString��������ȡ�Ǹ�����
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
			String sessionid = session.getId(); // ��ȡsessionid
			Cookie cookie = new Cookie("JSESSIONID", sessionid); // newһ��cookie��cookie��������JSESSIONID����id��cookieһ��
			cookie.setPath(request.getContextPath()); // ����cookieӦ�÷�Χ��getContextPath�ǻ�ȡ��ǰ��Ŀ�����֡�
			cookie.setMaxAge(600000); // ������Чʱ��
			response.addCookie(cookie);// �����cookie�Ѵ�id��cookie���ǵ�
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

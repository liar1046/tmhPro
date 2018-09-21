package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.csy.utils.DataUtil;

/**
 * 用户登录
 * 
 *         1，获取用户前台输入的工程识别码，和本地数据库信息进行对比 2，把登录成功的工程识别码放入session，后续可以判断用户是否登录
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/**
	 * 获取工程识别码，分离任务编号和发货单编号 判断任务编号是否存在，获取任务数据
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		login(request, response, out, session);
		
		String redirectUrl = (String) session.getAttribute("redirectUrl");
		if (redirectUrl != null) {
			
			session.removeAttribute("redirectUrl");
			response.sendRedirect(redirectUrl);
			login(request, response, out, session);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response, PrintWriter out, HttpSession session)
			throws UnsupportedEncodingException {
		String username = request.getParameter("username");

		String contractId = null; // 任务编号
		String counterman = null; // 业务员
		String authority = null; // 权限

		boolean b = true;

		// 第一次登录必须使用工程识别码登录
		String shibiema = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		if (username.matches(shibiema)) {
			Decoder decoder = Base64.getDecoder();
			String projectId = new String(decoder.decode(username), "UTF-8");
			System.out.println("工程识别码：" + projectId);
			contractId = projectId.split("@")[0];
			// 业务员
			counterman = projectId.split("@")[2];

			// 获取权限
			authority = projectId.split("@")[3];
			char[] authorityArr = authority.toCharArray();
			char orderAuthority;
			char confirmAuthority;
			// 下单权限
			orderAuthority = authorityArr[0];
			// 确认订单权限
			confirmAuthority = authorityArr[1];

			session.setAttribute("contractId", contractId);
			session.setAttribute("counterman", counterman);
			session.setAttribute("orderAuthority", orderAuthority);
			session.setAttribute("confirmAuthority", confirmAuthority);

			/**
			 * 根据合同编号获取合同所有正在进行的任务列表
			 */
			String urlString = "http://125.46.39.104:8088/Contract/" + contractId;
			String contractData = "";
			try {
				contractData = DataUtil.getData(urlString);
				// out.print(contractData);
				// System.out.println("contractData:" + contractData);
				// 判断获取到的任务列表中是否包含合同编号
				b = contractData.contains(contractId);
				// System.out.println(b);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!b) {
				out.write("<h1>登录失败，请重新输入工程识别码！！！</h1>");
				// 设置2秒跳到重新登录
				response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
			} else {
				Cookie ck = new Cookie("username", username);
				ck.setPath("/");
				response.addCookie(ck);// 将Cookie写回到客户端
				// out.write("登录成功！");
				// 把用户名即工程识别码放入session中，后续可以根据session判断用户是否登录
				session.setAttribute("username", username);
				response.setHeader("refresh", "0.1;url=" + request.getContextPath() + "/jsp/task.jsp");
			}
		} else {
			String projectName = (String) session.getAttribute("projectName");
			if (projectName == null) {
				out.write("<h1>第一次登陆请使用工程识别码，或者扫描二维码!</h1>");
				response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
			} else {
				if (username.equals(projectName)) {
					contractId = (String) session.getAttribute("contractId");
					String urlString = "http://125.46.39.104:8088/Contract/" + contractId;
					String contractData = "";
					try {
						contractData = DataUtil.getData(urlString);
						// System.out.println("contractData:"+contractData);
						b = contractData.contains(contractId);
						// System.out.println(b);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (!b) {
						out.write("<h1>登录失败，请重新输入工程识别码！！！</h1>");
						// 设置2秒跳到重新登录
						response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
					} else {
						// 登录成功，跳转到主界面，任务列表界面
						response.setHeader("refresh", "0.1;url=" + request.getContextPath() + "/jsp/task.jsp");
					}
				}
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
 * �û���¼
 * 
 *         1����ȡ�û�ǰ̨����Ĺ���ʶ���룬�ͱ������ݿ���Ϣ���жԱ� 2���ѵ�¼�ɹ��Ĺ���ʶ�������session�����������ж��û��Ƿ��¼
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/**
	 * ��ȡ����ʶ���룬���������źͷ�������� �ж��������Ƿ���ڣ���ȡ��������
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

		String contractId = null; // ������
		String counterman = null; // ҵ��Ա
		String authority = null; // Ȩ��

		boolean b = true;

		// ��һ�ε�¼����ʹ�ù���ʶ�����¼
		String shibiema = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		if (username.matches(shibiema)) {
			Decoder decoder = Base64.getDecoder();
			String projectId = new String(decoder.decode(username), "UTF-8");
			System.out.println("����ʶ���룺" + projectId);
			contractId = projectId.split("@")[0];
			// ҵ��Ա
			counterman = projectId.split("@")[2];

			// ��ȡȨ��
			authority = projectId.split("@")[3];
			char[] authorityArr = authority.toCharArray();
			char orderAuthority;
			char confirmAuthority;
			// �µ�Ȩ��
			orderAuthority = authorityArr[0];
			// ȷ�϶���Ȩ��
			confirmAuthority = authorityArr[1];

			session.setAttribute("contractId", contractId);
			session.setAttribute("counterman", counterman);
			session.setAttribute("orderAuthority", orderAuthority);
			session.setAttribute("confirmAuthority", confirmAuthority);

			/**
			 * ���ݺ�ͬ��Ż�ȡ��ͬ�������ڽ��е������б�
			 */
			String urlString = "http://125.46.39.104:8088/Contract/" + contractId;
			String contractData = "";
			try {
				contractData = DataUtil.getData(urlString);
				// out.print(contractData);
				// System.out.println("contractData:" + contractData);
				// �жϻ�ȡ���������б����Ƿ������ͬ���
				b = contractData.contains(contractId);
				// System.out.println(b);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!b) {
				out.write("<h1>��¼ʧ�ܣ����������빤��ʶ���룡����</h1>");
				// ����2���������µ�¼
				response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
			} else {
				Cookie ck = new Cookie("username", username);
				ck.setPath("/");
				response.addCookie(ck);// ��Cookieд�ص��ͻ���
				// out.write("��¼�ɹ���");
				// ���û���������ʶ�������session�У��������Ը���session�ж��û��Ƿ��¼
				session.setAttribute("username", username);
				response.setHeader("refresh", "0.1;url=" + request.getContextPath() + "/jsp/task.jsp");
			}
		} else {
			String projectName = (String) session.getAttribute("projectName");
			if (projectName == null) {
				out.write("<h1>��һ�ε�½��ʹ�ù���ʶ���룬����ɨ���ά��!</h1>");
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
						out.write("<h1>��¼ʧ�ܣ����������빤��ʶ���룡����</h1>");
						// ����2���������µ�¼
						response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
					} else {
						// ��¼�ɹ�����ת�������棬�����б����
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

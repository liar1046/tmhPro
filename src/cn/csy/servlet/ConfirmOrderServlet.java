package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * ȷ�϶���
 *
 */
@SuppressWarnings("serial")
public class ConfirmOrderServlet extends HttpServlet {
	
	/**
	 * �û��������ǩ�������ȷ�ϰ�ť�������ж��û��Ƿ��¼�����û�е�¼����ת��¼ҳ�棬��¼���Զ�ȷ�϶���
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// ��session�л�ȡ�û���¼��Ϣ
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if(username != null){
			String confirmAuthority = (String)session.getAttribute("confirmAuthority").toString();
			// ����ҳ������
			String getCarNum = request.getParameter("getCarNum");
			System.out.println("���ţ�" + getCarNum);
			
			if("1".equals(confirmAuthority)){
				// ��תҳ��
				out.write("<h2>ȷ�ϳɹ���2���Ӻ󷵻ض����б���</h2>");
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/ordersToConfirm.jsp");
			}else{
				out.write("<h2>��û�и�Ȩ�ޣ�����ϵҵ��Ա����</h2>");
				response.setHeader("refresh", "1,url=" + request.getContextPath() + "/jsp/ordersToConfirm.jsp");				
			}
		} else{
			// ��ȡ��ַ����Ϣ
			if(null != request.getQueryString()){
				session.setAttribute("redirectUrl", request.getRequestURL().append("?").append(request.getQueryString()).toString());
				out.write("<h2>����δ��¼�����ȵ�¼��������¼�ɹ��󣬻��Զ�ȷ�϶���</h2>");
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/login.jsp");
			}else{
				out.write("<h2>����δ��¼�����ȵ�¼��������¼�ɹ��󣬻��Զ�ȷ�϶���</h2>");
				session.setAttribute("redirectUrl", request.getRequestURL().toString());
				response.setHeader("refresh", "2,url=" + request.getContextPath() + "/jsp/login.jsp");
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

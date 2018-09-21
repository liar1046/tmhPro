package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.csy.utils.DataUtil;
import net.sf.json.JSONObject;

/**
 * �������
 * 
 * ��ȡ��������������ݣ���װΪjson��������head��Ϣ�����͸�����վ������
 */
@SuppressWarnings("serial")
public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * ��ȡǰ̨���ݣ���װΪjson������ͬhead��Ϣ����������������ȡ������Ϣ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (username == null) {
			out.write("<h2>����δ��¼��2���Ӻ���ת����¼ҳ�桭��</h2>");
			response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
		} else {
			// ��ȡǰ̨����
			String contractId = request.getParameter("contractId");
			String projectName = request.getParameter("projectName");
			String builder = request.getParameter("builder");
			String address = request.getParameter("address");
			String distance = request.getParameter("distance");
			String constructionSite = request.getParameter("constructionSite");
			String type = request.getParameter("type");
			String need = request.getParameter("need");
			String strength = request.getParameter("strength");
			String antifreeze = request.getParameter("antifreeze");
			String impervious = request.getParameter("impervious");
			String collapse = request.getParameter("collapse");
			String concreting = request.getParameter("concreting");
			String deliverTime = request.getParameter("deliverTime");
			String phone = request.getParameter("phone");
			String comment = request.getParameter("comment");
			String entering = request.getParameter("entering");
			String filiale = request.getParameter("filiale");
			
			// �������Ʒŵ�session�У��˺���Ϣ��ʹ��
			session.setAttribute("projectName", projectName);

			// ���ݷ�װΪjson����
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("��ͬ���", contractId);
			jsonObject.put("��������", projectName);
			jsonObject.put("ʩ����λ", builder);
			jsonObject.put("ʩ���ص�", address);
			jsonObject.put("�˾�", distance);
			jsonObject.put("ʩ����λ", constructionSite);
			jsonObject.put("��Ʒ����", type);
			jsonObject.put("����", need);
			jsonObject.put("ǿ�ȵȼ�", strength);
			jsonObject.put("�����ȼ�", antifreeze);
			jsonObject.put("�����ȼ�", impervious);
			jsonObject.put("̮���", collapse);
			jsonObject.put("������ʽ", concreting);
			jsonObject.put("����ʱ��", deliverTime);
			jsonObject.put("��ϵ�绰", phone);
			jsonObject.put("��ע", comment);
			jsonObject.put("¼����", entering);
			jsonObject.put("�ֹ�˾�б�", filiale);

//			System.out.println("ǰ̨����:" + jsonObject);

//			out.write("" + jsonObject);
			out.write("<h1>��ӳɹ����ȴ�����վ������񡭡�</h1>");
			response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/task.jsp");

			// �����ݷ��͵�������
			String urlString = "http://125.46.39.104:8088/Task/New";
			URL url = new URL(urlString);
			HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
			// ���ͷ��Ϣ���¶�����Ȩ��
			httpurlconnection.setRequestProperty("X-Tmh-OrderAuthority", session.getAttribute("orderAuthority").toString());
			
			DataUtil.setData(urlString, jsonObject);
		}
	}

}

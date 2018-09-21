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
 * 添加任务
 * 
 * 获取添加任务界面的数据，封装为json对象，设置head信息，发送给搅拌站服务器
 */
@SuppressWarnings("serial")
public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * 获取前台数据，封装为json对象，连同head信息，传给服务器，获取返回信息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (username == null) {
			out.write("<h2>您还未登录，2秒钟后跳转到登录页面……</h2>");
			response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/login.jsp");
		} else {
			// 获取前台数据
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
			
			// 工程名称放到session中，账号信息绑定使用
			session.setAttribute("projectName", projectName);

			// 数据封装为json对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("合同编号", contractId);
			jsonObject.put("工程名称", projectName);
			jsonObject.put("施工单位", builder);
			jsonObject.put("施工地点", address);
			jsonObject.put("运距", distance);
			jsonObject.put("施工部位", constructionSite);
			jsonObject.put("产品种类", type);
			jsonObject.put("数量", need);
			jsonObject.put("强度等级", strength);
			jsonObject.put("抗冻等级", antifreeze);
			jsonObject.put("抗渗等级", impervious);
			jsonObject.put("坍落度", collapse);
			jsonObject.put("浇筑方式", concreting);
			jsonObject.put("供砼时间", deliverTime);
			jsonObject.put("联系电话", phone);
			jsonObject.put("备注", comment);
			jsonObject.put("录入人", entering);
			jsonObject.put("分公司列表", filiale);

//			System.out.println("前台数据:" + jsonObject);

//			out.write("" + jsonObject);
			out.write("<h1>添加成功，等待搅拌站添加任务……</h1>");
			response.setHeader("refresh", "2;url=" + request.getContextPath() + "/jsp/task.jsp");

			// 把数据发送到服务器
			String urlString = "http://125.46.39.104:8088/Task/New";
			URL url = new URL(urlString);
			HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
			// 添加头信息，下订单的权限
			httpurlconnection.setRequestProperty("X-Tmh-OrderAuthority", session.getAttribute("orderAuthority").toString());
			
			DataUtil.setData(urlString, jsonObject);
		}
	}

}

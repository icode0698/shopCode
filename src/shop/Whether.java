package shop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class Whether extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Whether() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String message = request.getParameter("message");
		System.out.println("message:"+message);
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		if("getStatus".equals(message)){
			if("".equals(session.getAttribute("user"))||session.getAttribute("user")==null){
				json.put("status", "fail");
				json.put("message", "用户未登录或登录会话超时");
				out.write(json.toString());
				out.flush();
				out.close();
			}
			else {
				json.put("status", "success");
				json.put("user", session.getAttribute("user"));
				json.put("message", "用户已登录");
				out.write(json.toString());
				out.flush();
				out.close();
			}
		}
		else{
			session.removeAttribute("user");
			session.invalidate();
			json.put("status", "success");
			json.put("message", "退出登录");
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}

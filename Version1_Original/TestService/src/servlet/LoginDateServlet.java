package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import db.DBUtils;
import domain.BaseBean;
import domain.UserBean;

@WebServlet("/LoginDateServlet")
public class LoginDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginDateServlet() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		String username = request.getParameter("name"); // 获取客户端传过来的参数
		String password = request.getParameter("password");

		response.setContentType("text/html;charset=utf-8");
		if ((username == null || username.equals(""))&&(password == null || password.equals(""))) {
			System.out.println("用户名、密码都为空");
			return;
		} // 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean(); // user的对象
		
		if(dbUtils.isExistInDB(username,password)) {
			data.setCode(1);
			data.setData(userBean);
			data.setMsg("登陆成功");
		
			userBean.setName(username);
			userBean.setPassword(password);
			data.setData(userBean);
		}else {
			data.setCode(0);
			userBean.setName(username);
			userBean.setPassword(password);
			data.setData(userBean);
			data.setMsg("用户名或密码错误");
		}				
		
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// 将对象转化成json字符串
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import db.DBUtils;
import domain.BaseBean;
import domain.UserBean;

/**
 * Servlet implementation class ReadServlet
 */
@WebServlet("/ReadServlet")
public class ReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		
		String nickname = request.getParameter("nickname"); // 获取客户端传过来的参数
		String username = request.getParameter("name"); 
		String sex = request.getParameter("sex");
		String address = request.getParameter("address");
		String money = request.getParameter("money");
		String[] b = new String[4];
		 
		response.setContentType("text/html;charset=utf-8");
		
		//请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();

		BaseBean data = new BaseBean(); // 基类对象，回传给用户的json对象
		UserBean userBean = new UserBean(); // user的对象
		if(username==null || username.equals("")) {
			System.out.println("无参数");
			return ;
		}
		try {
				System.out.println("读取数据库值");
				b = dbUtils.readdata(username);
				nickname = b[0];
				sex = b[1];
				address = b[2];
				money = b[3];
		}
		 catch (SQLException e1) {
			e1.printStackTrace();
		}
		//传值
		userBean.setNickname(nickname);
		userBean.setSex(sex);
		userBean.setAddress(address);
		userBean.setMoney(money);
		//读取
		data.setCode(1);
		data.setMsg("读取成功");
		data.setData(userBean);

		Gson gson = new Gson();
		String json = gson.toJson(data);
		// 将对象转化成json字符串
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流
		}
		dbUtils.closeConnect(); // 关闭数据库
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
package servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class ReadAllServlet
 */
@WebServlet("/ReadAllServlet")
public class ReadAllServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		
		List<UserBean> userList = new ArrayList<UserBean>();

		response.setContentType("text/html;charset=utf-8");
		
		//打开数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();

		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		//UserBean userBean = new UserBean(); // user的对象
		try {
				System.out.println("读取数据库值");
				userList = dbUtils.readalldata();
				if(userList == null){
					System.out.println("无数据");
					return;
				}
		}
		 catch (SQLException e1) {
			e1.printStackTrace();
		}
		//读取
		data.setCode(1);
		data.setMsg("读取成功");
		data.setData(userList);

		Gson gson = new Gson();
		String json = gson.toJson(userList);
		// 将对象转化成json字符串
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流
		}
		dbUtils.closeConnect(); // 关闭数据库连接
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
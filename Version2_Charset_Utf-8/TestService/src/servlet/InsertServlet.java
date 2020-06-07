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

@WebServlet("/InsertDataServlet")
public class InsertServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname"); // 获取客户端传过来的数据
		String username = request.getParameter("name"); 
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String address = request.getParameter("address");
		
		response.setContentType("text/html;charset=utf-8");
		//打开数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的对象
		UserBean userBean = new UserBean(); // user的对象
		if((username==null || username.equals("")) || (password==null || password.equals(""))) {
			System.out.println("无参数");
			return ;
		}
		else {
			dbUtils.insertdata(nickname,sex,address,username,password);
		}
		userBean.setName(username);
		userBean.setPassword(password);
		userBean.setNickname(nickname);
		userBean.setSex(sex);
		userBean.setAddress(address);

		data.setCode(1);
		data.setMsg("插入成功");
		data.setData(userBean);
		
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// 将数据转化成json字符串
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭流
		}
		dbUtils.closeConnect(); // 关闭数据库
	}
}

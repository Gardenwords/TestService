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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
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
		 
		response.setContentType("text/html;charset=gbk");
		
		//请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean(); // user的对象
		if(username==null || username.equals("")) {
			System.out.println("无参数");
			return ;
		}
		try {
			if ((nickname == null || nickname.equals(""))&(sex == null || sex.equals(""))&(address == null || address.equals(""))) {
				System.out.println("昵称、性别、地址都为空");
				return ;
			} 
			else {
				System.out.println("昵称、性别、地址不都为空");
				dbUtils.updatedata(nickname,sex,address,username);
			}
				userBean.setNickname(nickname);
				userBean.setSex(sex);
				userBean.setAddress(address);

				data.setCode(1);
				data.setMsg("修改成功");
				data.setData(userBean);
		} catch (SQLException e1) {
			e1.printStackTrace();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
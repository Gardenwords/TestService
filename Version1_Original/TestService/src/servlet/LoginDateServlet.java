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
		String username = request.getParameter("name"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");

		response.setContentType("text/html;charset=utf-8");
		if ((username == null || username.equals(""))&&(password == null || password.equals(""))) {
			System.out.println("�û��������붼Ϊ��");
			return;
		} // �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		
		if(dbUtils.isExistInDB(username,password)) {
			data.setCode(1);
			data.setData(userBean);
			data.setMsg("��½�ɹ�");
		
			userBean.setName(username);
			userBean.setPassword(password);
			data.setData(userBean);
		}else {
			data.setCode(0);
			userBean.setName(username);
			userBean.setPassword(password);
			data.setData(userBean);
			data.setMsg("�û������������");
		}				
		
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// ������ת����json�ַ���
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		dbUtils.closeConnect(); // �ر����ݿ�����
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
		
		String nickname = request.getParameter("nickname"); // ��ȡ�ͻ��˴������Ĳ��� 
		String username = request.getParameter("name"); 
		String sex = request.getParameter("sex");
		String address = request.getParameter("address");
		String[] b = new String[3];
		 
		response.setContentType("text/html;charset=utf-8");
		
		//�������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();

		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		if(username==null || username.equals("")) {
			System.out.println("�޲���");
			return ;
		}
		try {
				System.out.println("��ȡ���ݿ�ֵ");
				b = dbUtils.readdata(username);
				nickname = b[0];
				sex = b[1];
				address = b[2];
		}
		 catch (SQLException e1) {
			e1.printStackTrace();
		}
		//��ֵ
		userBean.setNickname(nickname);
		userBean.setSex(sex);
		userBean.setAddress(address);
		//��ȡ
		data.setCode(1);
		data.setMsg("��ȡ�ɹ�");
		data.setData(userBean);

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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
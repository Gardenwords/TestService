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
		
		String nickname = request.getParameter("nickname"); // ��ȡ�ͻ��˴������Ĳ��� 
		String username = request.getParameter("name"); 
		String sex = request.getParameter("sex");
		String address = request.getParameter("address");
		 
		response.setContentType("text/html;charset=gbk");
		
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
			if ((nickname == null || nickname.equals(""))&(sex == null || sex.equals(""))&(address == null || address.equals(""))) {
				System.out.println("�ǳơ��Ա𡢵�ַ��Ϊ��");
				return ;
			} 
			else {
				System.out.println("�ǳơ��Ա𡢵�ַ����Ϊ��");
				dbUtils.updatedata(nickname,sex,address,username);
			}
				userBean.setNickname(nickname);
				userBean.setSex(sex);
				userBean.setAddress(address);

				data.setCode(1);
				data.setMsg("�޸ĳɹ�");
				data.setData(userBean);
		} catch (SQLException e1) {
			e1.printStackTrace();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
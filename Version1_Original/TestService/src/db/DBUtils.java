package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;   //����sql�б���������Ҫ���࣬��ʵ�����ӹ���

public class DBUtils {
	private Connection conn=null;
	private String url = "jdbc:mysql://localhost:3306/test1?&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8"; // ָ���������ݿ��URL
	private String user = "root"; // ָ���������ݿ���û���
	private String password = "990622cc"; // ָ���������ݿ������
	private Statement sta;
	private ResultSet rs; // �����ݿ�����

	public void openConnect() {
		try {
			// �������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// �������ݿ�����
			
			if (conn != null) {
				System.out.println("���ݿ����ӳɹ�."); // ���ӳɹ�����ʾ��Ϣ
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());			
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	// ��ò�ѯuser�������ݼ�
	public ResultSet getUser() {
		// ���� statement����
		try {
			sta = conn.createStatement(); // ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	} 
	// �ж����ݿ����Ƿ����ĳ���û�����������,ע��͵�¼��ʱ���ж�
	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false; // ���� statement����
		try {
			System.out.println("�ж��û�������");
			rs = getUser();
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("name").equals(username)) {
						if (rs.getString("password").equals(password)) {
							isFlag = true;
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
	}		
	
	public void setdefault(String username) throws SQLException   //����Ĭ��ֵ
	{
		rs = getUser();
		try
		{
			PreparedStatement psql;
			psql = conn.prepareStatement("update user set nickname = ? where name = ?");
			psql.setString(1, "�ǳ�");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set sex = ? where name = ?");
			psql.setString(1, "��");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set address = ? where name = ?");
			psql.setString(1, null);
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			System.out.println("���ݿ������޸ĳɹ�!");
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] readdata(String username) throws SQLException   //�����ݿ�ֵ
	{
		sta = conn.createStatement(); // ִ��SQL��ѯ���
		rs = sta.executeQuery("select * from user where name = '"+ username+"'");// ��ý����
		String[] a = new String[3];
		try{
        	while(rs.next()) {        		
        		a[0] = rs.getString("nickname");
        		a[1] = rs.getString("sex");
        		a[2] = rs.getString("address");
        	}
        }catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	public void updatedata(String nickname,String sex,String address,String username) throws SQLException {
		rs = getUser();
		try
		{
			PreparedStatement psql;
			psql = conn.prepareStatement("update user set nickname = ? where name = ?");
			if(nickname != ""&& nickname !=null) {
				psql.setString(1, nickname);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			else if(nickname == "") {
				psql.setString(1, null);	
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			psql = conn.prepareStatement("update user set sex = ? where name = ?");
			if(sex != "" && sex != null) {
				psql.setString(1, sex);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			else if(sex == ""){
				psql.setString(1, null);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			psql = conn.prepareStatement("update user set address = ? where name = ?");
			if(address != "" && address!=null) {
				psql.setString(1, address);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			} 
			else if(address == "") {
				psql.setString(1, null);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			
			System.out.println("���ݿ������޸ĳɹ�!");
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	// �ر����ݿ�����
	public void closeConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (sta != null) {
				sta.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("�ر����ݿ�����.");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}

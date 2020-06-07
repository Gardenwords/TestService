package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;   //加入sql中本程序所需要的类，以实现链接功能

public class DBUtils {
	private Connection conn=null;
	private String url = "jdbc:mysql://localhost:3306/test1?&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String password = "990622cc"; // 指定连接数据库的密码
	private Statement sta;
	private ResultSet rs; // 打开数据库连接

	public void openConnect() {
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// 创建数据库连接
			
			if (conn != null) {
				System.out.println("数据库连接成功."); // 连接成功的提示信息
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());			
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	// 获得查询user表后的数据集
	public ResultSet getUser() {
		// 创建 statement对象
		try {
			sta = conn.createStatement(); // 执行SQL查询语句
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	} 
	// 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false; // 创建 statement对象
		try {
			System.out.println("判断用户名密码");
			rs = getUser();
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
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
	
	public void setdefault(String username) throws SQLException   //设置默认值
	{
		rs = getUser();
		try
		{
			PreparedStatement psql;
			psql = conn.prepareStatement("update user set nickname = ? where name = ?");
			psql.setString(1, "昵称");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set sex = ? where name = ?");
			psql.setString(1, "男");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set address = ? where name = ?");
			psql.setString(1, null);
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			System.out.println("数据库数据修改成功!");
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] readdata(String username) throws SQLException   //读数据库值
	{
		sta = conn.createStatement(); // 执行SQL查询语句
		rs = sta.executeQuery("select * from user where name = '"+ username+"'");// 获得结果集
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
			
			System.out.println("数据库数据修改成功!");
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 关闭数据库连接
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
			System.out.println("关闭数据库连接.");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}

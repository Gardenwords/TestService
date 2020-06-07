package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import domain.UserBean;

public class DBUtils {
	private Connection conn=null;
	private String url = "jdbc:mysql://localhost:3306/test1?&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
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
				System.out.println("数据库连接成功."); // 连接成功提示
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString());			
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	// 获得查询user表后的数据集
	public ResultSet getUser() {
		// 创建statement对象
		try {
			sta = conn.createStatement(); // 执行SQL查询语句
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	} 
	// 判断数据库中是否存在某个用户名及其密码，注册和登陆的时候判断
	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false; // 创建statement对象
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
			psql.setString(1, "male");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set address = ? where name = ?");
			psql.setString(1, null);
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			psql = conn.prepareStatement("update user set money = ? where name = ?");
			psql.setString(1, "0");
			psql.setString(2, username);
			psql.executeUpdate();
			psql.close();
			
			System.out.println("数据库数据修改成功");
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] readdata(String username) throws SQLException   //读取数据库值
	{
		sta = conn.createStatement(); // 执行SQL查询语句
		rs = sta.executeQuery("select * from user where name = '"+ username+"'");// 获得结果集
		String[] a = new String[4];
		try{
        	while(rs.next()) {        		
        		a[0] = rs.getString("nickname");
        		a[1] = rs.getString("sex");
        		a[2] = rs.getString("address");
        		a[3] = rs.getString("money");
        	}
        }catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public List<UserBean> readalldata() throws SQLException   //读取数据库值
	{
		sta = conn.createStatement(); // 执行SQL查询语句
		rs = sta.executeQuery("select * from user");// 获得结果集
		List<UserBean> userList = new ArrayList<UserBean>();
		try{
			while(rs.next()){
				UserBean s = new UserBean();
				s.setName(rs.getString("name"));
				s.setNickname(rs.getString("nickname"));
				s.setSex(rs.getString("sex"));
				s.setAddress(rs.getString("address"));
				s.setMoney(rs.getString("money"));
				userList.add(s);
			}
        }catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public void updatedata(String password, String nickname,String sex,String address,String username, String money) throws SQLException {
		rs = getUser();
		try
		{
			PreparedStatement psql;
			psql = conn.prepareStatement("update user set password = ? where name = ?");
			if(password != ""&& password !=null) {
				psql.setString(1, password);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
			else if(password == "") {
				psql.setString(1, null);	
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			}
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
			psql = conn.prepareStatement("update user set money = ? where name = ?");
			if(money != "" && money!=null) {
				psql.setString(1, money);
				psql.setString(2, username);
				psql.executeUpdate();
				psql.close();
			} 
			else if(money == "") {
				psql.setString(1, money);
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
	
	public void insertdata(String nickname, String sex, String address, String username, String password) {
		// TODO Auto-generated method stub
		String sql = "insert into user(name,password,nickname,sex,address,money) values(?,?,?,?,?,0)";
		try {
            InitialContext ctx = new InitialContext();
            /*执行SQL*/
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, nickname);
            ps.setString(4, sex);
            ps.setString(5, address);
            int nrs = ps.executeUpdate();

            System.out.println("数据库数据插入成功!");
        } catch (SQLException se) {
            System.out.println("SQLException: " + se.getMessage());
        } catch (NamingException ne) {
            System.out.println("NamingException: " + ne.getMessage());
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

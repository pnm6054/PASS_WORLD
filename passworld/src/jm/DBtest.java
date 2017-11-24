/*package jm;

import java.sql.*;
import java.util.ArrayList;

import passworld.acdata;

*//**
 * mysql로 회원 정보를 전송하는 Class
 *//*
public class DBtest {

	
	 * private static String DB_ID = "memory"; private static String DB_PASSWORD =
	 * "0238"; private static String DB_NAME = "memory"; private static String DB_IP
	 * = "localhost"; private static int DB_PORT = 3306;
	 * 
	 * private static String DB_Connect = "jdbc:mysql://" + DB_IP + ":" + DB_PORT +
	 * "/" + DB_NAME;
	 
	static Connection conn;
	 PreparedStatement stmt;
	static ResultSet rs;

	public DBtest() {
		connectDB();
	}

	protected void connectDB() {
		try {
			String url = "jdbc:sqlite:ext/test.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.err.println("Error : DB Connect - Driver Manager");
		}
	}

	public static boolean getMember() { // 유저 정보 갱신
		boolean isSuccess = false;
		try {
			acdata data;
			// Data.member_vector.removeAllElements();
			ArrayList<acdata> result = new ArrayList<acdata>();
			//stmt = conn.prepareStatement("select * from test"); // 쿼리문 전송
			//rs = stmt.executeQuery();

			while (rs.next()) { // result set이 더 있을 경우
				acdata data1 = new acdata();
				
				 * data1.siteid = rs.getString("id"); data1.keyword = rs.getString("pw");
				 * data1.id = rs.getString("email"); data1.pw = rs.getString("name");
				 * data1.madedate = Integer.parseInt(rs.getString("pwQuestion")); data1.pwAnswer
				 * = rs.getString("pwAnswer");
				 
				data1.setIndex(rs.getInt("rowid"));
				data1.setSiteid(rs.getString("siteid"));
				data1.setKeyword(rs.getString("keyword"));
				data1.setId(rs.getString("id"));
				data1.setPw(rs.getString("pw"));
				data1.setMadedate(rs.getString("makedate"));

				System.out.println(rs.getInt("rowid") + "\t" + rs.getString("siteid") + "\t" + rs.getString("keyword")
						+ "\t" + rs.getString("id") + "\t" + rs.getString("pw") + "\t" + rs.getString("makedate"));
				result.add(data1);
				; // 벡터에 유저 정보 추가
			}
			isSuccess = true;
		} catch (SQLException e) {

		}
		return isSuccess;
	}

	protected boolean insertMember(String id, String passwd, String email, String name, int question, String answer) {
		boolean isSuccess = false;
		try {
			String sql = "insert into member values(\'" + id + "\', \'" + passwd + "\', \'" + email + "\', \'" + name
					+ "\', " + question + ", \'" + answer + "\');";
			// String sql = "insert into member(id, pw, email, name, pwQuestion, pwAnswer)
			// values('root22', 'root', 'root@naver.com', 'root', 1, 'answer')";
			isSuccess = stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("Error : Insert Member");
		}
		return isSuccess;
	}

	public boolean insertaddress(String name, String phone, String email, String major, int code, String birthday,
			String groupname, String snsAddress, String hash, String gender) {
		boolean isSuccess = false;
		try {
			acdata data;
			// Data.member_vector.removeAllElements();

			stmt = conn.prepareStatement("insert into address(name) values('kangmin')");
			String sql = "insert into address values(\'" + "kangmin" + "\', \'" + phone + "\', \'" + email + "\', \'"
					+ major + "\', " + code + ", \'" + birthday + "\', \'" + groupname + "\', \'" + snsAddress
					+ "\', \'" + hash + "\', \'" + gender + "\');";

			isSuccess = stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("Error : Insert Member");
		}
		System.out.println("success");
		return isSuccess;
	}

	protected void closeDB() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.err.println("Error : DB Close");
		}
	}

	
	 * public int isPWCorrect(String id, String pw){ int i; for(i=0;
	 * i<acdata.member_vector.size(); i++){
	 * if(Data.member_vector.elementAt(i).id.equals(id) &&
	 * Data.member_vector.elementAt(i).pw.equals(pw)){ return i; // 아이디 비번이 일치하면
	 * 로그인한 유저 인덱스 반환 } } System.out.println(Data.member_vector.size()); return -1;
	 * // 존재하지 않을 경우 -1 반환 }
	 
public static void main(String[] args) {
	getMember();
		
		insert.insert(siteid,keyword,id,pw,madedate);
	}
}
*/
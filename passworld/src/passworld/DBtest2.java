package passworld;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


/**
 * connect to ext/main.db schema of main.db's table 'Main'
 * <p/>
 * CREATE TABLE Main ( siteid text not null collate nocase, //The name of the
 * site can not be blank. and They are not case-sensitive.
 * <p/>
 * keyword text default siteid collate nocase, // If the keyword is blank, place
 * the name of the site. and They are not case-sensitive.
 * <p/>
 * id text not null, // The name of the site can not be blank.
 * <p/>
 * pw text default 'UNKNOWN', //If the password is blank, place the String
 * 'UNKNOWN'.
 * <p/>
 * makedate not null default current_date, //If the password is blank, database
 * place the date when registered.
 * <p/>
 * count int not null default 0, //The count of times that password used
 * <p/>
 * unique (siteid,id)); //Duplicate entries can not be registered with the same
 * name as the name and ID of the site
 * 
 * 
 * @author SJ.Kim
 * @version 0.0.1
 */
public class DBtest2 {
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;
	ArrayList<acdata> result = new ArrayList<acdata>();
	
	public DBtest2() {
		connectDB();
	}

	protected void connectDB() {
		try {
			String url = "jdbc:sqlite:ext/main.db";
			conn = DriverManager.getConnection(url);

		} catch (SQLException e) {
			System.err.println("Error : DB Connect - Driver Manager");
		}
	}

	public boolean getMember() { // 유저 정보 갱신
		boolean isSuccess = false;
		try {

			stmt = conn.prepareStatement("select rowid,* from Main"); // 쿼리문 전송
			rs = stmt.executeQuery();

			while (rs.next()) { // result set이 더 있을 경우
				acdata data1 = new acdata();
				data1.setIndex(rs.getInt("rowid"));
				data1.setSiteid(rs.getString("siteid"));
				data1.setKeyword(rs.getString("keyword"));
				data1.setId(rs.getString("id"));
				data1.setPw(rs.getString("pw"));
				data1.setMadedate(rs.getString("makedate"));

				System.out.println(rs.getInt("rowid") + "\t" + rs.getString("siteid") + "\t" + rs.getString("keyword")
						+ "\t" + rs.getString("id") + "\t" + rs.getString("pw") + "\t" + rs.getString("makedate"));
				result.add(data1);
			}
			isSuccess = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isSuccess;
	}

	protected boolean insertMember(String siteid, String id, String pw) {
		boolean isSuccess = true;
		String sql = "INSERT INTO Main(siteid, id, pw) VALUES(?,?,?)";
		try {
			//String sql = "insert into Main(siteid,id,pw) values(\'" + siteid + "\', \'" + id + "\', \'" + pw + "\');";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, siteid);
			stmt.setString(2, id);
			stmt.setString(3, pw);
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Error : Insert Member\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	public boolean insertaddress(String name, String phone, String email, String major, int code, String birthday,
			String groupname, String snsAddress, String hash, String gender) {
		boolean isSuccess = false;
		try {
			/*MemberInfo m;
			Data.member_vector.removeAllElements();*/

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
}
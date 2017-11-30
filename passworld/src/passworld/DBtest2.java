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
	acdata tmpdata = new acdata();

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

	public boolean search(String word) { // 유저 정보 갱신
		boolean isSuccess = false;
		try {
			System.out.println(word);
			stmt = conn.prepareStatement("SELECT rowid,*" + "FROM Main WHERE siteid like ? or keyword like ?"); // 쿼리문
																												// 전송
			stmt.setString(1, '%' + word + '%');
			stmt.setString(2, '%' + word + '%');
			rs = stmt.executeQuery();

			while (rs.next()) { // result set이 더 있을 경우
				acdata data1 = new acdata();
				data1.setIndex(rs.getInt("rowid"));
				data1.setSiteid(rs.getString("siteid"));
				data1.setKeyword(rs.getString("keyword"));
				data1.setId(rs.getString("id"));
				data1.setPw(rs.getString("pw"));
				data1.setMadedate(rs.getString("makedate"));
				data1.setcount(rs.getInt("count"));

				System.out.println(rs.getInt("rowid") + "\t" + rs.getString("siteid") + "\t" + rs.getString("keyword")
						+ "\t" + rs.getString("id") + "\t" + rs.getString("pw") + "\t" + rs.getString("makedate")+
						"\t" + rs.getInt("count"));
				result.add(data1);
			}
			isSuccess = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isSuccess;
	}
	
	public boolean search(int rowid) { // 유저 정보 갱신
		boolean isSuccess = false;
		try {
			System.out.println(rowid);
			stmt = conn.prepareStatement("SELECT rowid,*" + "FROM Main WHERE rowid = ?"); // 쿼리문전송
																				
			stmt.setInt(1, rowid);
			rs = stmt.executeQuery();

			// result set이 더 있을 경우
			tmpdata.setIndex(rs.getInt("rowid"));
			tmpdata.setSiteid(rs.getString("siteid"));
			tmpdata.setKeyword(rs.getString("keyword"));
			tmpdata.setId(rs.getString("id"));
			tmpdata.setPw(rs.getString("pw"));
			tmpdata.setMadedate(rs.getString("makedate"));
			tmpdata.setcount(rs.getInt("count"));

			System.out.println(rs.getInt("rowid") + "\t" + rs.getString("siteid") + "\t" + rs.getString("keyword")
					+ "\t" + rs.getString("id") + "\t" + rs.getString("pw") + "\t" + rs.getString("makedate")+
					"\t" + rs.getInt("count"));
			isSuccess = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isSuccess;
	}
	

	protected boolean insertAccount(String siteid, String keyword, String id, String pw, String makedate) {
		boolean isSuccess = true;
		String sql = "INSERT INTO Main(siteid, keyword, id, pw, makedate) VALUES(?,?,?,?,?)";
		try {
			// String sql = "insert into Main(siteid,id,pw) values(\'" + siteid + "\', \'" +
			// id + "\', \'" + pw + "\');";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, siteid);
			stmt.setString(2, keyword);
			stmt.setString(3, id);
			stmt.setString(4, pw);
			stmt.setString(5, makedate);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error : Insert Account\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	
	protected boolean deleteAccount(int rowid) {
		boolean isSuccess = true;
		String sql = "DELETE FROM Main WHERE rowid = ?";
		try {
			// set the corresponding param
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rowid);
			System.out.println(sql);
			// execute the delete statement
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error : Can't delete\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}
	
	protected boolean updateAccount(acdata data) {
		boolean isSuccess = true;
		String sql = "update from Main set siteid = ?, keyword = ?, id = ?, pw = ?, makedate = currentdate where rowid = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, data.getId());
			stmt.setString(2, data.getKeyword());
			stmt.setString(3, data.getId());
			stmt.setString(4, data.getPw());
			stmt.setInt(5,data.getIndex());
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error : Can't update\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
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
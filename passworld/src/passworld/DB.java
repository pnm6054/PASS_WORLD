package passworld;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

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
 * @version 0.1.1
 */
public class DB {
	Connection conn;
	PreparedStatement stmt;
	Statement smt;
	ResultSet rs;
	ArrayList<acdata> result = new ArrayList<acdata>();
	acdata tmpdata = new acdata();
	Aria aria = new Aria();
	String otp_username;
	String otp_SECRET_KEY;

	public DB() {
		connectDB();
	}

	protected void connectDB() {
		try {
			String url = "jdbc:sqlite:c:/reso/main.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.err.println("Error : DB Connect - Driver Manager");
		}
	}

	protected boolean search(String word) { // 검색 기능
		boolean isSuccess = false;
		try {
			System.out.println(word);
			stmt = conn.prepareStatement("SELECT rowid,*" + "FROM Main WHERE siteid like ? or keyword like ? order by count desc"); // 쿼리문
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
				data1.setPw(aria.Decrypt(rs.getString("pw")));
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
	
	protected boolean search(int rowid) { // 유저 정보 갱신
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
			tmpdata.setPw(aria.Decrypt(rs.getString("pw")));
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
	
	protected boolean searchPw(String pw) { // 동일 비밀번호 여부 검색
		boolean isSuccess = false;
		try {
			System.out.println(pw);
			stmt = conn.prepareStatement("SELECT rowid,*" + "FROM Main WHERE pw = ?"); // 쿼리문전송
																				
			stmt.setString(1, aria.Encrypt(pw));
			rs = stmt.executeQuery();
			int i = 0;
			while (rs.next()) { // result set이 더 있을 경우
				i++;
			}
			System.out.println(i);
			if(i<1)	isSuccess = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isSuccess;
	}

	protected boolean insertAccount(String siteid, String keyword, String id, String pw, String makedate) {
		boolean isSuccess = true;
		String sql;
		if(makedate.toString().length()==0) {
		sql = "INSERT INTO Main(siteid, keyword, id, pw, makedate) VALUES(?,?,?,?,current_date)";
		}else {
			sql = "INSERT INTO Main(siteid, keyword, id, pw, makedate) VALUES(?,?,?,?,?)";
		}
		try {
			// String sql = "insert into Main(siteid,id,pw) values(\'" + siteid + "\', \'" +
			// id + "\', \'" + pw + "\');";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, siteid);
			stmt.setString(2, keyword);
			stmt.setString(3, id);
			stmt.setString(4, aria.Encrypt(pw));
			if(makedate.toString().length()!=0) stmt.setString(5, makedate);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error : Insert Account\n");
			System.out.println(e.getMessage());
			if(e.getMessage().contains("UNIQUE")) //무결성 에러 발생시 출력하는 메세지
			{
				JOptionPane.showMessageDialog(null, "항목이 이미 등록되어 있습니다.");
			}
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
	
	protected boolean updateAccount(String siteid, String keyword, String id, String pw, int index) {
		boolean isSuccess = true;
		String sql = "update Main set siteid = ?, keyword = ?, id = ?, pw = ?, makedate = current_date where rowid = ?";
		try {
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, siteid);
			stmt.setString(2, keyword);
			stmt.setString(3, id);
			stmt.setString(4, aria.Encrypt(pw));
			stmt.setInt(5, index);
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error : Can't update\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}
	
	protected boolean updateAccount(int index) {
		boolean isSuccess = true;
		String sql = "update Main set count = count+1 where rowid = ?";
		try {
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, index);
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error : Can't update count\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}
	protected boolean resettable() {
		boolean isSuccess = true;
		String sql = "CREATE TABLE Main (siteid text not null collate nocase, keyword text default siteid collate nocase, id text not null, pw text default 'UNKNOWN', makedate not null default current_date, count int not null default 0, unique (siteid,id));";
		try {
			System.out.println(sql);
			stmt = conn.prepareStatement("drop table Main");
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error : Can't reset main table\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}
	
    /**
     * This method save register information in database.
     * @param username
     * @param secretcode
     */
	protected boolean registerInfo(String username, String secretcode) {
		boolean isSuccess = true;
		String sql = "update Google_Auth set secretcode = ?, username = ? where rowid = 1";
		resettable();
		try {
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, aria.Encrypt(secretcode));
			stmt.setString(2, username);
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.err.println("Error : Can't update otp info\n");
			System.out.println(e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
    }
	/** connect to db for loading Information to login
     * 
     * @param conn
     * @param stat
     * @param rs
     */
	protected void loadInfo() {
		boolean isSuccess = true;
		try {
			smt = conn.createStatement();
			rs = smt.executeQuery("select * from Google_Auth");
			otp_username = rs.getString("username");
			otp_SECRET_KEY = aria.Decrypt(rs.getString("secretcode"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            isSuccess = false;
        }
    }

	protected void closeDB() {
		System.out.println("Connection to SQLite has been closed");
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.err.println("Error : DB Close");
		}
	}
}
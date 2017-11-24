package passworld;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
/** connect to ext/main.db
 * schema of main.db's table 'Main'
 * <p/>
 * CREATE TABLE Main (
 * siteid text not null collate nocase, //The name of the site can not be blank. and They are not case-sensitive.
 * <p/>
 * keyword text default siteid collate nocase, // If the keyword is blank, place the name of the site. and They are not case-sensitive.
 * <p/>
 * id text not null, // The name of the site can not be blank.
 * <p/>
 * pw text default 'UNKNOWN', //If the password is blank, place the String 'UNKNOWN'.
 * <p/>
 * makedate not null default current_date, //If the password is blank, database place the date when registered.
 * <p/>
 * count int not null default 0, //The count of times that password used
 * <p/>
 * unique (siteid,id)); //Duplicate entries can not be registered with the same name as the name and ID of the site
 * 
 * 
 * @author SJ.Kim
 * @version 0.0.1
 */
public class DB {
	public static Connection connect() {
		Connection conn = null;
		try {
			// db parameters
			String url = "jdbc:sqlite:ext/main.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			            
			System.out.println("Connection to SQLite has been established.");  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
		/*finally {
			try {
					if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}*/
		return conn;
	}
	
	public static void main(String[] args) {

		select.selectAll();
		/*insert.insert(siteid,keyword,id,pw,madedate);*/
	}
}
/**
 * Connect to the database
 * Insert a new row into the main table
 * <p/>
 * INSERT INTO Main(siteid, keyword, id, pw, makedate) VALUES(?,?,?,?,?)
 * <p/>
 * sqlite doesn't allow registering 'null' value.
 * To register a value you do not know, simply remove that and register the item.
 * <p/>
 * To do this, method recieve the object that include the account data.
 * and use 'if' construction to analysis what the objects have.
 * Based on analyzed results select the method of registration.
 * <p/>
 * @author SJ.Kim
 *
 */
class insert extends DB{
	
	/*public insert(acdata new_data) {
		siteid = new_data.getSiteid();
		keyword = new_data.getKeyword();
		id = new_data.getId();
		pw = new_data.getPw();
		madedate = new_data.getMadedate();
		if()
	}*/
    /**
     * Connect to the database
	 * Insert a new row into the main table
     *
     * @param acdata
     */
	public static void insert(acdata data) {
		String sql = "INSERT INTO Main(siteid, keyword, id, pw, makedate) VALUES(?,?,?,?,?)";
		try (Connection conn = DB.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
					/*pstmt.setString(1, siteid);
					pstmt.setString(2, keyword);
					pstmt.setString(3, id);
					pstmt.setString(4, pw);
					pstmt.setString(5, madedate);*/
					pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
     * Connect to the database
	 * Insert a new row into the main table
     *
     * @param siteid
     * @param id
     * @param pw
     * @param madedate
     */
	public insert(String siteid, String id, String pw, String madedate) {
		String sql = "INSERT INTO Main(siteid, id, pw, madedate) VALUES(?,?,?,?)";
     
			try (Connection conn = DB.connect();
					PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, siteid);
				pstmt.setString(2, id);
				pstmt.setString(3, pw);
				pstmt.setString(4, madedate);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
    /**
     * Connect to the database
	 * Insert a new row into the main table
     *
     * @param siteid
     * @param id
     * @param pw
     */
	public insert(String siteid, String id, String pw) {
		String sql = "INSERT INTO Main(siteid, id, pw) VALUES(?,?,?)";
			try (Connection conn = DB.connect();
					PreparedStatement pstmt = conn.prepareStatement(sql)) {
						pstmt.setString(1, siteid);
						pstmt.setString(2, id);
						pstmt.setString(3, pw);
						pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
}
/**
 * SELECT rowid,* FROM Main WHERE siteid like ? or keyword like ?
 * <p/>
 * Get the row whose keyword same with specific site's name or keyword
 * or get the row whose number same with specific number
 * <p/>
 * The data that you import from the database is stored in the object.
 * And these objects are stored in arraylist to transfer to other method.
 * <p/>
 * when query end, the query results are stored in 'result set'.
 * but the 'result set' are terminated when method end.
 * to solve this problem, put the result set's data in the ArrayList .
 * <p/>
 * @author SJ.Kim
 *
 */
class select extends DB {
	/**
     * Connect to the database
     * select all rows in the test table
     */
	public static ArrayList<acdata> selectAll(){
		ArrayList<acdata> result = new ArrayList<acdata>();
		String sql = "SELECT rowid, siteid, keyword, id, pw, makedate FROM Main";
		try (Connection conn = DB.connect();
				Statement stmt  = conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
			// loop through the result set
			while (rs.next()) {
				acdata data1 = new acdata();
				System.out.println(rs.getInt("rowid") +  "\t" + 
								   rs.getString("siteid") + "\t" +
								   rs.getString("keyword") + "\t" +
								   rs.getString("id") + "\t" +
								   rs.getString("pw") + "\t" +
								   rs.getString("makedate"));
				data1.setIndex(rs.getInt("rowid"));
				data1.setSiteid(rs.getString("siteid"));
				data1.setKeyword(rs.getString("keyword"));
				data1.setId(rs.getString("id"));
				data1.setPw(rs.getString("pw"));
				data1.setMadedate(rs.getString("makedate"));
				result.add(data1);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	/**
	* Get the test whose keyword same with specific siteid or keyword
	* @param keyword
	*/
	public static ArrayList<acdata> search(String word){
							String sql = "SELECT rowid,*"
							+ "FROM Main WHERE siteid like ? or keyword like ?";
		ArrayList<acdata> result = new ArrayList<acdata>();
		try (Connection conn = DB.connect();
			PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
			// set the value
			pstmt.setString(1,'%'+word+'%');
			pstmt.setString(2,'%'+word+'%');
			//
			ResultSet rs  = pstmt.executeQuery();
            
			// loop through the result set
			while (rs.next()) {
				acdata data1 = new acdata();
				System.out.println(rs.getString("siteid") +  "\t" + 
								   rs.getString("keyword") + "\t" +
								   rs.getString("id") + "\t" +
								   rs.getString("pw") + "\t" +
								   rs.getString("makedate"));
				data1.setIndex(rs.getInt("rowid"));
				data1.setSiteid(rs.getString("siteid"));
				data1.setKeyword(rs.getString("keyword"));
				data1.setId(rs.getString("id"));
				data1.setPw(rs.getString("pw"));
				data1.setMadedate(rs.getString("makedate"));
				result.add(data1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	/**
	* Get the Main whose number same with specific number
	* @param no
	*/	
	public void selectone(int no){
		String sql = "SELECT ? FROM Main";
		try (Connection conn = DB.connect();
				PreparedStatement pstmt  = conn.prepareStatement(sql)){
				// set the value
				pstmt.setInt(1,no);
				//
				ResultSet rs  = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("siteid") +  "\t" + 
								   rs.getString("keyword") + "\t" +
								   rs.getString("id") + "\t" +
								   rs.getString("pw") + "\t" +
								   rs.getString("madedate"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<acdata> rs2vector(ResultSet rs) {
		return null;
	}
}
/**
 * Update data of a warehouse specified by the row number
 * @author SJ.Kim
 */
class update extends DB {
		/**
	     * Update data of a warehouse specified by the id
	     *
	     * @param id
	     * @param name name of the test
	     * @param capacity capacity of the test
	     */
		public void update(int rowid, String keyw, String pw) {
			String sql = "UPDATE Main SET keyword = ?, pw = ? , "
						  + "madedate = current_date "
						  + "WHERE rowid = ?";
	 
			try (Connection conn = DB.connect();
					PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// set the corresponding param
				pstmt.setString(1, keyw);
	            pstmt.setString(2, pw);
	            pstmt.setInt(3, rowid);
	            // update 
	            pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}	 
}
/**
 * DELETE FROM Main WHERE rowid = ?
 * Delete a test specified by the rowid
 * @author SJ.Kim
 */
class delete extends DB {
	/**
	 * DELETE FROM Main WHERE rowid = ?
	 * Delete a test specified by the rowid
	 *
	 * @param rowid
	 */
	public void delete(int rowid) {
		String sql = "DELETE FROM Main WHERE rowid = ?";
	     
		try (Connection conn = DB.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
	     
			// set the corresponding param
			pstmt.setInt(1, rowid);
			// execute the delete statement
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}   
}
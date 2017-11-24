package passworld;

/**
 * the instance for storing user's account information
 * @author SJ.Kim
 * @version 0.0.1
 */
public class acdata {
	/**
	 * @param index rowid of data in db. (user can't see this data)
	 */
	 int index;
	/**
	 * @param siteid the site's name
	 */
	 String siteid;
	/**
	 * @param keyword the keyword for searching
	 */
	 String keyword;
	/**
	 * @param id user's id of site
	 */
	 String id;
	/**
	 * @param pw user's password of site
	 */
	 String pw;
	/**
	 * @param madedate date when information changed. default : date when information added
	 */
	 String madedate;
	
	
	/**
	 * Returns the rowid of account data
	 * @return index
	 */
	public int getIndex() {return index;}
	/**
	 * Set the index Value of instance
	 * @param index rowid from db
	 */
	public void setIndex(int index) {this.index = index;}
	/**
	 * Returns the site's name
	 * @return siteid
	 */
	public String getSiteid() {return siteid;}
	/**
	 * Set the site's name Value of instance
	 * @param siteid site's name value from db
	 */
	public void setSiteid(String siteid) {this.siteid = siteid;}
	/**
	 * Returns the keyword for search
	 * @return keyword
	 */
	public String getKeyword() {return keyword;}
	/**
	 * Set the keyword Values of instance
	 * @param keyword site's keyword from db
	 */
	public void setKeyword(String keyword) {this.keyword = keyword;}
	/**
	 * Returns the user's id of site
	 * @return id
	 */
	public String getId() {return id;}
	/**
	 * Set the id Value of instance
	 * @param Id site's id value of user from db
	 */
	public void setId(String Id) {this.id = Id;}
	/**
	 * Returns the user's password of site
	 * @return pw
	 */
	public String getPw() {return pw;}
	/**
	 * Set the password Value of instance
	 * @param pw site's password value of user from db
	 */
	public void setPw(String pw) {this.pw = pw;}
	/**
	 * Returns the date when password created or added
	 * @return madedate
	 */
	public String getMadedate() {return madedate;}
	/**
	 * Set the madedate Value of instance
	 * @param madedate created date from db
	 */
	public void setMadedate(String madedate) {this.madedate = madedate;}
}
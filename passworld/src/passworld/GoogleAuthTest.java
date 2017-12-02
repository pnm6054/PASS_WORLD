/*
 * Copyright (c) 2014-2015 Enrico M. Crisostomo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *   * Neither the name of the author nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package passworld;

import com.warrenstrange.googleauth.*;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder;


import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Not really a unit test, but it shows the basic usage of this package.
 * To properly test the authenticator, manual intervention and multiple steps
 * are required:
 * <ol>
 * <li>Run the test in order to generate the required information for a
 * Google Authenticator application to be configured.</li>
 * <li>Set the <code>SECRET_KEY</code> field with the value generated by the
 * <code>GoogleAuthTest#createCredentials</code> method.</li>
 * <li>Generate the current code with the Google Authenticator application and
 * set the <code>VALIDATION_CODE</code> accordingly.</li>
 * <li>Check that the <code>#authorise</code> method correctly validates the
 * data when invoking the <code>GoogleAuthenticator#authorize</code> method.
 * </li>
 * </ol>
 */

public class GoogleAuthTest
{

    // Change this to the saved secret from the running the above test.

	/**
	 * @param SECRET_KEY 
	 * @param VALIDATION_CODE
	 * @param username
	 */
    private static String SECRET_KEY = "2QV45ZM5LWEEBO6L";
    private static int VALIDATION_CODE = 911556;
    private static String username = "Default";
    DBtest2 db = new DBtest2();
    
    public GoogleAuthTest() {
    	loadInfo();
    	setupMockCredentialRepository();
    }
   
    public static void setupMockCredentialRepository()
    {
        System.setProperty(
                CredentialRepositoryMock.MOCK_SECRET_KEY_NAME,
                SECRET_KEY);
    }

    private static byte[] hexStr2Bytes(String hex)
    {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        System.arraycopy(bArray, 1, ret, 0, ret.length);

        return ret;
    }
    /*public static void main(String[] args) {
    	
    	setupMockCredentialRepository();
    	
    	//createCredentialsForUser();
    	System.out.println(SECRET_KEY);
    	authoriseUser("201561");

    	System.out.println(VALIDATION_CODE);
    }*/
    
    public static void createCredentialsForUser(String name, String email)
    {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

        final GoogleAuthenticatorKey key =
                googleAuthenticator.createCredentials(name);
        final String SECRET_KEY = key.getKey();
        //final List<Integer> scratchCodes = key.getScratchCodes();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL(name, email, key);

        registerInfo(name,SECRET_KEY);
        System.out.println("Please register (otpauth uri): " + otpAuthURL);
        System.out.println("Secret key is " + SECRET_KEY);
    }

    public static boolean authoriseUser(String VALIDATION_CODE)
    {
        GoogleAuthenticatorConfigBuilder gacb =
                new GoogleAuthenticatorConfigBuilder()
                        .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30))
                        .setWindowSize(5)
                        .setCodeDigits(6);
        GoogleAuthenticator ga = new GoogleAuthenticator(gacb.build());
        boolean isCodeValid = ga.authorizeUser(username, Integer.parseInt(VALIDATION_CODE));

        System.out.println("Check VALIDATION_CODE = " + isCodeValid);
        
        return isCodeValid;
    }
    
    /** connect to db for loading Information to login
     * 
     * @param
     */
    private void loadInfo() {
    	Connection conn = null;
    	Statement stat = null;
    	ResultSet rs = null;
		try {
			// db parameters
			String url = "jdbc:sqlite:ext/main.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			            
			System.out.println("Connection to SQLite has been established.");
			
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from Google_Auth");
			username = rs.getString("username");
			SECRET_KEY = rs.getString("secretcode");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void registerInfo(String username, String secretcode) {
    	Connection conn = null;
    	PreparedStatement pstat = null;
    	int result = 0;
		try {
			// db parameters
			String url = "jdbc:sqlite:ext/main.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			            
			System.out.println("Connection to SQLite has been established.");
			
			pstat = conn.prepareStatement("update Google_Auth set secretcode = ?, username = ? where rowid = 1");
			pstat.setString(1, secretcode);
			pstat.setString(2, username);
			result = pstat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("계정 등록 에러" + e.getMessage());
        }
    }
}

package passworld;

import com.warrenstrange.googleauth.*;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;


/**
 * Not really a unit test, but it shows the basic usage of GoogleAuth.jar.
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

/**
 *@author jinyoung.Y
 *This class related 'googleauth.jar'.
 */
public class GoogleAuthTest
{

    // Change this to the saved secret from the running the above test.

	/**
	 *
	 * 
	 * @param username name of user
	 */
    private static String SECRET_KEY = "2QV45ZM5LWEEBO6L";
    private static int VALIDATION_CODE = 911556;
    private static String username = "Default";
    DB db = new DB();
    
    
    public GoogleAuthTest() {
    	
    	db.loadInfo();
    	username = db.otp_username;
    	SECRET_KEY = db.otp_SECRET_KEY;
    	setupMockCredentialRepository();
    	db.closeDB();
    }
   
    
    public static void setupMockCredentialRepository()
    {
        System.setProperty(
        		"com.warrenstrange.googleauth.CredentialRepositoryMock.secret.name",
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
    
    /**
     * 'main' method is test method to test
     * whether this works correctly without other class.
     * */
    
    /**
     * This method is create credentials for user.<p/>
     * key is credential for generate secret key.<p/>
     * SECRET_KEY is the secret key.<p/>
     * This method shows the secret key and QRcode after running this method.<p/>
     * 
     * 
     * @param name name of user
     * @param email email of user
     * @return String for compareing with otp code user typed
     */
    public static String createCredentialsForUser(String name, String email)
    {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        DB db = new DB();
        final GoogleAuthenticatorKey key =
                googleAuthenticator.createCredentials(name);
        final String SECRET_KEY = key.getKey();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL(name, email, key);
        db.registerInfo(name,SECRET_KEY);
        System.out.println("Please register (otpauth uri): " + otpAuthURL);
        System.out.println("Secret key is " + SECRET_KEY);
        new QRcode(otpAuthURL); //QR코드 창 출력
        db.closeDB();
        return SECRET_KEY;
    }

    /**
     * 'authoriseUser' method determines whether the OTP code generated by user 
     * and the OTP code generated by PassWorld are the same.
     * If this method determines they are same, 
     * The user has the right to Log-In.
     * 
     * @param VALIDATION_CODE
     * @return true(same) or false(not same)
     */
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
}
    class QRcode extends JDialog implements MouseListener{
        BufferedImage img=null;// 버퍼(메모리)에 이미지를 올릴 때 쓰임
    	Dimension d = getToolkit().getScreenSize(); //화면 크기 측정
        public QRcode(String url){
        	setTitle("클릭하면 닫힘"); 
            setSize(215,240); 
        	setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2); //정중앙에 생성
        	setResizable(false); //크기조절 불가
            setVisible(true);
            try {
                img = ImageIO.read(new URL(url));// 윈도우에선 경로가 \라서 \\라고 입력해줘야 한다.
            } catch (IOException e) {// ImageIO이것을 적으면 catch문으로 예외처리를 해야 한다. 예외처리를 하도록 클래스 설계자가 적어두어서 반드시 해줘야 한다.
                // 입력과 출력이 rfid, 소켓통신, 윈도우, 임베디드 등등 입출력이 너무 다양하기 때문에..
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("에러 나셨습니다!!");// 일부러 없는 파일을 열어서 에러문을 출력해봐라, (이런 프린트문의 출력은 필요없다) 이 줄을 뺀것이 예외처리의 기본형태
                //System.exit(0);// 이 구문은 컴퓨터를 빠져 나와라는 것이다. 인자값 0의 의미는 0일땐 어떤게 실패, 1은 연결되었다 실패, 2는... 숫자로 의미를 부여할 수 있다.
            }
            MyPanel1 panel = new MyPanel1();
            add(panel);
            pack();
            addMouseListener(this);//클릭시 닫히는 액션
        }
        
        class MyPanel1 extends JPanel{
            public void paint(Graphics g){// 페인트 컴포넌트나 페인트나 같다.
                g.drawImage(img, 0, 0, null);
            }        
            public Dimension getPreferredSize(){// 만약 오타등으로 오버라이드가 안되었다면 툴을 이용해 불러와라
                if (img == null)
                    return new Dimension(300, 300);
                else
                    return new Dimension(img.getWidth(null), img.getHeight(null));// 인자값이 (null)이나 ()이나 같다.
            }
        }
        public void mouseClicked(MouseEvent arg0) {super.dispose();}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }

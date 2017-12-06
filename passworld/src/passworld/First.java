package passworld;

import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class First extends JFrame {

	private test_4all main;
	private JPanel contentPane;
	private JPasswordField passwordField;
	boolean bLoginCheck = false;
	GoogleAuthTest OTP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					First frame = new First();
					frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
					Dimension frameSize = frame.getSize();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((screenSize.width - frameSize.width) / 2,
							(screenSize.height - frameSize.height) / 2);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public First() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("c:/reso/1.png"));
		setTitle("PASS WORLD");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		setResizable(false);

		JLabel lblCode = new JLabel("code");
		lblCode.setFont(new Font("굴림", Font.PLAIN, 21));
		lblCode.setBounds(46, 59, 61, 24);
		lblCode.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(lblCode);

		passwordField = new JPasswordField();
		passwordField.setBounds(101, 59, 156, 28);
		contentPane.add(passwordField);
		passwordField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)
			{
			        // Enter키가 눌렸다면
			        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			        	isLoginCheck();
			        }
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});

		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255, 139, 139));
		btnLogin.setFont(new Font("굴림", Font.PLAIN, 16));
		btnLogin.setBounds(269, 61, 75, 26);
		getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		
		JButton button = new JButton("회원등록");
		button.setBackground(Color.WHITE);
		button.setFont(new Font("굴림", Font.PLAIN, 17));
		button.setBounds(221, 135, 123, 36);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "계속 하시겠습니까? \n기등록된 OTP정보는 사라집니다.","경고",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION)
					Register.main(null);
			}
		});

	}

	private static final int DEFAULT_WIDTH = 430;
	private static final int DEFAULT_HEIGHT = 250;

	public void isLoginCheck() {
		OTP = new GoogleAuthTest();
		try {
		if (OTP.authoriseUser(passwordField.getText())) {
			JOptionPane.showMessageDialog(null, "Success");
			bLoginCheck = true;

			// 로그인 성공이라면 매니져창 뛰우기
			if (isLogin()) {
				
				 // 메인창 메소드를 이용해 창뛰우기
				GUI_Main frame = new GUI_Main();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				dispose();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Faild"); // otp코드 미일치시 메세지 출력
			passwordField.setText(""); // otp코드 미일치시 초기화
		}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null,"숫자를 입력해주세요","ERROR",JOptionPane.ERROR_MESSAGE); //미입력 혹은 문자를 입력했을때
		}
	}

	// mainProcess와 연동
	public void setMain(test_4all main) {
		this.main = main;		
	}

	public boolean isLogin() {
		return bLoginCheck;
	}
}
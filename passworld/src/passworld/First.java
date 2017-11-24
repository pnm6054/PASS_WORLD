package passworld;

import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
 

public class First extends JFrame implements ActionListener {

	private test_4all main;
	private JPanel contentPane;
	private JPasswordField passwordField;
	boolean bLoginCheck = false;

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
					frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
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

		setIconImage(Toolkit.getDefaultToolkit().getImage("src/1.png"));
		setTitle("PASS WORLD");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		setResizable(false);
		
		JLabel lblCode = new JLabel("code");
		lblCode.setFont(new Font("����", Font.PLAIN, 21));
		lblCode.setBounds(46, 59, 61, 24);
		lblCode.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(lblCode);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(101, 59, 156, 28);
		contentPane.add(passwordField);
	    
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255,139,139));
		btnLogin.setFont(new Font("����", Font.PLAIN, 16));
		btnLogin.setBounds(269, 61, 75, 26);
		getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("sssss");
                isLoginCheck();
            }
        });
		
		JButton button = new JButton("ȸ�����");
		button.setBackground(Color.WHITE);
		button.setFont(new Font("����", Font.PLAIN, 17));
		button.setBounds(221, 135, 123, 36);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
	}
	public static final int DEFAULT_WIDTH = 430;
	public static final int DEFAULT_HEIGHT = 250;


	public void isLoginCheck(){
		GoogleAuthTest.setupMockCredentialRepository();
        if(GoogleAuthTest.authoriseUser(passwordField.getText())){
            JOptionPane.showMessageDialog(null, "Success");
            bLoginCheck = true;
           
            // �α��� �����̶�� �Ŵ���â �ٿ��
            if(isLogin()){
                GUI_Main main = new GUI_Main(); // ����â �޼ҵ带 �̿��� â�ٿ��
                main.main(null);
                
            }                  
        }else{
            JOptionPane.showMessageDialog(null, "Faild");
        }
    }
   
    // mainProcess�� ����
    public void setMain(test_4all main) {
        this.main = main;
    }
   
 
    public boolean isLogin() {     
        return bLoginCheck;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

class login_Field extends JPanel
{
	public login_Field() {
		
		setBackground(Color.WHITE);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255,139,139));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setFont(new Font("����", Font.PLAIN, 16));
		btnLogin.setBounds(269, 61, 75, 26);
		add(btnLogin);
		
		JLabel lblCode = new JLabel("code");
		lblCode.setFont(new Font("����", Font.PLAIN, 21));
		lblCode.setBounds(46, 59, 61, 24);
		//lblCode.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(lblCode);
	}
	
	
 
}


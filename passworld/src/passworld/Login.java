package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 464);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/1.png"));
		
		JLabel lblSite = new JLabel("Site ¿Ã∏ß");
		lblSite.setFont(new Font("±º∏≤", Font.PLAIN, 17));
		lblSite.setBounds(96, 50, 65, 20);
		contentPane.add(lblSite);
		
		textField = new JTextField();
		textField.setBounds(173, 51, 154, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblKeyWord = new JLabel("Key Word");
		lblKeyWord.setFont(new Font("±º∏≤", Font.PLAIN, 17));
		lblKeyWord.setBounds(90, 80, 71, 20);
		contentPane.add(lblKeyWord);
		

		textField = new JTextField();
		textField.setBounds(173, 82, 154, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		/*JTextArea textArea = new JTextArea();
		textArea.setBounds(173, 82, 154, 21);
		contentPane.add(textArea);*/
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("±º∏≤", Font.PLAIN, 17));
		lblId.setBounds(127, 213, 34, 20);
		contentPane.add(lblId);
		
		textField_1 = new JTextField();
		textField_1.setBounds(173, 214, 154, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("±º∏≤", Font.PLAIN, 17));
		lblPassword.setBounds(90, 243, 71, 20);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(173, 245, 154, 20);
		contentPane.add(passwordField);
		
		JLabel lblPasswordWaw = new JLabel("Password ¿Á¿‘∑¬");
		lblPasswordWaw.setFont(new Font("±º∏≤", Font.PLAIN, 17));
		lblPasswordWaw.setBounds(42, 302, 125, 20);
		contentPane.add(lblPasswordWaw);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(173, 302, 154, 22);
		contentPane.add(passwordField_1);
		
		JButton btnAfd = new JButton("»Æ¿Œ");
		btnAfd.setBackground(new Color(255,139,139));
		btnAfd.setBounds(90, 355, 100, 30);
		contentPane.add(btnAfd);
		
		JButton btnGhkrdls = new JButton("√Îº“");
		btnGhkrdls.setBackground(new Color(255,139,139));
		btnGhkrdls.setBounds(230, 355, 100, 30);
		contentPane.add(btnGhkrdls);
	}
}
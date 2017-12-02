package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField secret_field;
	private JTextField name_Field;
	private JTextField account_Field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 642);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		GoogleAuthTest OTP = new GoogleAuthTest();
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/1.png"));
		
		JLabel name = new JLabel("ÀÌ¸§");
		name.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		name.setBounds(110, 50, 50, 27);
		contentPane.add(name);
		
		JLabel account = new JLabel("°èÁ¤¸í");
		account.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		account.setBounds(103, 87, 50, 27);
		contentPane.add(account);
		
		name_Field = new JTextField();
		name_Field.setBounds(160, 53, 107, 24);
		contentPane.add(name_Field);
		
		account_Field = new JTextField();
		account_Field.setBounds(160, 90, 178, 24);
		contentPane.add(account_Field);
		
		JButton btnregister = new JButton("µî·Ï");
		btnregister.setBackground(new Color(255,139,139));
		btnregister.setBounds(160, 146, 97, 23);
		
		btnregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secret_field.setText(GoogleAuthTest.createCredentialsForUser(name_Field.getText(),account_Field.getText()));
			}
		});
		contentPane.add(btnregister);
		
		JLabel secretcode = new JLabel("Secret code");
		secretcode.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		secretcode.setBounds(58, 199, 95, 27);
		contentPane.add(secretcode);
		
		secret_field = new JTextField();
		secret_field.setBounds(160, 202, 178, 24);
		secret_field.setColumns(10);
		contentPane.add(secret_field);
		
		JLabel manual = new JLabel("* Goolge Authentiator »ç¿ë¹ý");
		manual.setForeground(Color.RED);
		manual.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		manual.setBounds(58, 256, 247, 15);
		contentPane.add(manual);
	}
}
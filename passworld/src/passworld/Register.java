package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
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
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("ext/1.png"));
		
		JLabel name = new JLabel("이름*");
		name.setFont(new Font("굴림", Font.PLAIN, 17));
		name.setBounds(110, 50, 50, 27);
		contentPane.add(name);
		
		JLabel account = new JLabel("계정명*");
		account.setFont(new Font("굴림", Font.PLAIN, 17));
		account.setBounds(94, 87, 60, 27);
		contentPane.add(account);
		
		name_Field = new JTextField();
		name_Field.setBounds(160, 53, 107, 24);
		contentPane.add(name_Field);
		
		account_Field = new JTextField();
		account_Field.setBounds(160, 90, 178, 24);
		contentPane.add(account_Field);
		
		JButton btnregister = new JButton("등록");
		btnregister.setBackground(new Color(255,139,139));
		btnregister.setBounds(160, 146, 97, 23);
		
		btnregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name_Field.getText().toString().length()==0) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else if(account_Field.getText().toString().length()==0 || !account_Field.getText().toString().contains("@")) {
					JOptionPane.showMessageDialog(null, "정확한 이메일을 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else {
				try {secret_field.setText(GoogleAuthTest.createCredentialsForUser(name_Field.getText(),account_Field.getText())); //secretcode 텍스트 필드에 생성된 secret code 출력
				}catch(IllegalArgumentException er){
					JOptionPane.showMessageDialog(null, "에러 발생 - 재시도 해주세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
				}
			}
		});
		contentPane.add(btnregister);
		
		JLabel secretcode = new JLabel("Secret code");
		secretcode.setFont(new Font("굴림", Font.PLAIN, 17));
		secretcode.setBounds(58, 199, 95, 27);
		contentPane.add(secretcode);
		
		secret_field = new JTextField();
		secret_field.setBounds(160, 202, 178, 24);
		secret_field.setColumns(10);
		contentPane.add(secret_field);
		
		JLabel manual = new JLabel("* Goolge Authentiator 사용법");
		manual.setForeground(Color.RED);
		manual.setFont(new Font("굴림", Font.PLAIN, 17));
		manual.setBounds(58, 256, 247, 15);
		contentPane.add(manual);
		
		ImageIcon image_man = new ImageIcon("./3.jpg");
		JLabel manual_img = new JLabel("",image_man,JLabel.CENTER);
		manual_img.setBounds(36,275,355,321);
		contentPane.add(manual_img);
	}
}
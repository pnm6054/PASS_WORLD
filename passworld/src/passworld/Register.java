package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField_2;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 642);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/1.png"));
		
		JLabel label = new JLabel("ÀÌ¸§");
		label.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		label.setBounds(110, 50, 50, 27);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("°èÁ¤¸í");
		label_1.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		label_1.setBounds(103, 87, 50, 27);
		contentPane.add(label_1);
		
		JTextField textField = new JTextField();
		textField.setBounds(160, 53, 107, 24);
		contentPane.add(textField);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(160, 90, 178, 24);
		contentPane.add(textField_1);
		
		JButton btnEmdfhr = new JButton("µî·Ï");
		btnEmdfhr.setBackground(new Color(255,139,139));
		btnEmdfhr.setBounds(160, 146, 97, 23);
		contentPane.add(btnEmdfhr);
		
		JLabel label_2 = new JLabel("Secret code");
		label_2.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		label_2.setBounds(58, 199, 95, 27);
		contentPane.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(160, 202, 178, 24);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_3 = new JLabel("* Goolge Authentiator »ç¿ë¹ý");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		label_3.setBounds(58, 256, 247, 15);
		contentPane.add(label_3);
	}
}
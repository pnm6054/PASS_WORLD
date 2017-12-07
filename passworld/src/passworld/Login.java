package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField field_SN = new JTextField();
	private JTextArea field_KW = new JTextArea();
	private JTextField field_ID = new JTextField();
	private JPasswordField field_PW = new JPasswordField();
	private JPasswordField field_re_PW = new JPasswordField();
	private JTextField field_mkdate = new JTextField();
	private JButton btnsubmit = new JButton("Ȯ��");
	private JTextArea safetycheck;
	DB db = new DB();
	private boolean isChecked = false;
	/**
	 * Create the frame.
	 */
	public Login() {
		
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("c:/reso/1.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = getToolkit().getScreenSize();
		setSize(450, 500);
		setLocation(d.width/2 - getWidth()/2, d.height/2 - getHeight()/2);
        
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		JLabel Sitename = new JLabel("site �̸�");
		Sitename.setBounds(65, 50, 100, 20);
		Sitename.setFont(new Font("����", Font.PLAIN, 17));
		Sitename.setHorizontalAlignment(JLabel.RIGHT);
		
		field_SN = new JTextField();
		field_SN.setBounds(175, 51, 154, 21);
		field_SN.setColumns(10);
		
		JLabel Keyword = new JLabel("Key Word");
		Keyword.setFont(new Font("����", Font.PLAIN, 17));
		Keyword.setBounds(65, 80, 100, 20);
		Keyword.setHorizontalAlignment(JLabel.RIGHT);

		
		field_KW = new JTextArea(1,1);
		field_KW.setBounds(175, 80, 154, 40);
		field_KW.setCaretPosition(field_KW.getText().length());
		field_KW.setLineWrap(true);
		field_KW.setWrapStyleWord(true);
		field_KW.setColumns(10);
		field_KW.setBorder(lineBorder);
		field_KW.setEditable(true);
		field_KW.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)
			{
			        // tabŰ�� ���ȴٸ�
			        if(e.getKeyCode() == KeyEvent.VK_TAB) {
			        	field_ID.requestFocus();
			        }
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		JLabel ID = new JLabel("ID");
		ID.setFont(new Font("����", Font.PLAIN, 17));
		ID.setBounds(65, 130, 100, 20);
		ID.setHorizontalAlignment(JLabel.RIGHT);
		
		field_ID = new JTextField();
		field_ID.setBounds(175, 130, 154, 21);
		field_ID.setColumns(10);
		
		JLabel PW = new JLabel("��ȣ");
		PW.setFont(new Font("����", Font.PLAIN, 17));
		PW.setBounds(65, 160, 100, 20);
		PW.setHorizontalAlignment(JLabel.RIGHT);
		
		field_PW = new JPasswordField();
		field_PW.setBounds(175, 160, 154, 21);
		field_PW.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)
			{
				isChecked = false;
				System.out.println(field_PW.getText().length());
				switch(pwchecker()) {
	        	case 0 : safetycheck.setText("���� - ���� ��й�ȣ ���̴� 8�ڸ� �̻��Դϴ�.\n(��ϺҰ�)"); 
	        			safetycheck.setForeground(Color.red);
	        		break;
	        	case 1 : safetycheck.setText("��� - Ư�����ڰ� ���Ե��� �ʾҽ��ϴ�.\n(��ϰ���)"); 
	        	safetycheck.setForeground(new Color(255,200,4));
	        	break;
	        	case 2 : safetycheck.setText("���� - ������ ��й�ȣ�Դϴ�.\n(��ϰ���"); 
	        	safetycheck.setForeground(Color.green);
	        	break;
	        	}
				
/*			        // tabŰ�� �����ϰ�
			        if(e.getKeyCode() != KeyEvent.VK_TAB) {
			        	
			        }*/
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		safetycheck = new JTextArea();
		safetycheck.setBounds(175, 190, 154, 40);
		safetycheck.setCaretPosition(field_KW.getText().length());
		safetycheck.setLineWrap(true);
		safetycheck.setWrapStyleWord(true);
		safetycheck.setColumns(10);
		safetycheck.setBorder(lineBorder);
		//Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		safetycheck.setEditable(false);
		
		JLabel re_PW = new JLabel("��ȣ ��Ȯ��");
		re_PW.setFont(new Font("����", Font.PLAIN, 17));
		re_PW.setBounds(65, 240, 100, 20);
		re_PW.setHorizontalAlignment(JLabel.RIGHT);

		field_re_PW = new JPasswordField();
		field_re_PW.setBounds(175, 242, 154, 22);
		
		JButton pw_eq = new JButton("��ġ����");
		pw_eq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(field_PW.getText().equals(field_re_PW.getText())){
					if (!db.searchPw(field_PW.getText())) {
						switch (JOptionPane.showConfirmDialog(null, "�̹� ��ϵǾ��ִ� ��й�ȣ �Դϴ�.\n����Ͻðڽ��ϱ�?", "��й�ȣ �ߺ�",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
						case JOptionPane.YES_OPTION:
							JOptionPane.showMessageDialog(null, "��й�ȣ ��ġ");
							isChecked = true;
							break;
						}
					} else {
						JOptionPane.showMessageDialog(null, "��й�ȣ ��ġ");
						isChecked = true;
					}
				}else {
					JOptionPane.showMessageDialog(null, "��й�ȣ ����ġ");
				}
			}
		});
		pw_eq.setBounds(175, 270, 154, 20);
		pw_eq.setBackground(new Color(255,139,139));
		pw_eq.setHorizontalAlignment(JLabel.LEFT);
		contentPane.add(pw_eq);
		
		JLabel makedate = new JLabel("�������");
		makedate.setFont(new Font("����", Font.PLAIN, 17));
		makedate.setBounds(65, 300, 100, 20);
		makedate.setHorizontalAlignment(JLabel.RIGHT);
		
		field_mkdate = new JTextField();
		field_mkdate.setBounds(175, 300, 154, 21);
		field_mkdate.setColumns(10);
		
		btnsubmit = new JButton("Ȯ��");
		btnsubmit.setBackground(new Color(255,139,139));
		btnsubmit.setBounds(105, 380, 100, 30);
	
		btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_PW.getText().toString().length() != 0 && field_ID.getText().toString().length() != 0
						&& field_SN.getText().toString().length() != 0) {
					if (isChecked) {
						if (field_mkdate.getText().toString().length() == 10
								|| field_mkdate.getText().toString().length() == 0) {
							if (db.insertAccount(field_SN.getText(), field_KW.getText(), field_ID.getText(),
									field_PW.getText(), field_mkdate.getText()) == true) {
								JOptionPane.showMessageDialog(null, "��ϼ���");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "��Ͻ���");
							}
						} else {
							JOptionPane.showMessageDialog(null, "YYYY-MM-DD�������� �Է����ּ���\n�������� �Է½� ���� ��¥�� ����˴ϴ�.",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "��й�ȣ ��ġ ���� �˻縦 ���ּ���", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "�ʼ� �׸� ���Է�", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnGhkrdls = new JButton("���");
		btnGhkrdls.setBackground(new Color(255,139,139));
		btnGhkrdls.setBounds(245, 380, 100, 30);
		btnGhkrdls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		contentPane.add(Sitename);
		contentPane.add(field_SN);
		contentPane.add(Keyword);
		contentPane.add(field_KW);
		contentPane.add(ID);
		contentPane.add(field_ID);
		contentPane.add(PW);
		contentPane.add(field_PW);
		contentPane.add(re_PW);
		contentPane.add(field_re_PW);
		contentPane.add(safetycheck);
		contentPane.add(makedate);
		contentPane.add(field_mkdate);
		contentPane.add(pw_eq);
		
		contentPane.add(btnsubmit);
		contentPane.add(btnGhkrdls);
	}
	public Login(acdata data) {
		this();
		field_SN.setText(data.getSiteid());
		field_KW.setText(data.getKeyword());
		field_ID.setText(data.getId());
		field_PW.setText(data.getPw());
		field_re_PW.setText(data.getPw());
		field_mkdate.setText(data.getMadedate());
	
		contentPane.remove(btnsubmit);
		btnsubmit = new JButton("����");
		btnsubmit.setBackground(new Color(255,139,139));
		btnsubmit.setBounds(105, 380, 100, 30);
		btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_PW.getText().toString().length() != 0 && field_ID.getText().toString().length() != 0
						&& field_SN.getText().toString().length() != 0) {
					if (isChecked) {
						if (field_mkdate.getText().toString().length() == 10
								|| field_mkdate.getText().toString().length() == 0) {
							if(db.updateAccount(field_SN.getText(), field_KW.getText(), field_ID.getText(), field_PW.getText(), data.getIndex())) {
								JOptionPane.showMessageDialog(null, "��ϼ���");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "��Ͻ���");
							}
						} else {
							JOptionPane.showMessageDialog(null, "YYYY-MM-DD�������� �Է����ּ���\n�������� �Է½� ���� ��¥�� ����˴ϴ�.",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "��й�ȣ ��ġ ���� �˻縦 ���ּ���", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "�ʼ� �׸� ���Է�", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnsubmit);
	}
	private int pwchecker() {
		if(field_PW.getText().length()>6) {
			String specChar = "[0-9|a-z|A-Z|��-��|��-��|��-��|]*";
			if(!field_PW.getText().matches(specChar)) {
		            System.out.println("��ġ~");
		            return 2;
		        }
			return 1;
		}
		return 0;
	}
}
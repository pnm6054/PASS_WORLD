package passworld;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
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
	private static final String word = "v";
	public main() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 356);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/1.png"));
		
		textField = new JTextField("",20);
		textField.setBounds(55, 44, 259, 24);
		contentPane.add(textField);
		textField.setColumns(10);

        String[] columnName = {"사이트", "키워드", "ID", "Password", "최근변경일"};
        String[][] rowdata = {{"사이트", "키워드", "ID", "Password", "최근변경일"},
        		{"네이버","naver","abcd123","bbbaa","2017-11-04"},
        		{"네이버","naver","abcd123","bbbaa","2017-11-04"}};
        /*
		Vector<String> data_row = new Vector<String>();
        data_row.addElement("사이트");
        data_row.addElement("키워드");
        data_row.addElement("ID");
        data_row.addElement("Password");
        data_row.addElement("최근변경일");*/
        DefaultTableModel dtm = new DefaultTableModel(rowdata,columnName);

        /*ArrayList<acdata> rs = select.selectAll();
		int rsize = rs.size();
		for(int i=0;i<rsize;i++) {
			String []a = {rs.get(i).getSiteid(),rs.get(i).getKeyword(),rs.get(i).getId(),rs.get(i).getPw(),rs.get(i).getMadedate()};
			dtm.addRow(a);
		}
		*/
		JTable table = new JTable(dtm);
        /*table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);*/

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
        table.setBounds(55, 100, 310, 100);
        contentPane.add(table);
		
		JButton btnNewButton = new JButton("등록");
		btnNewButton.setBackground(new Color(255,139,139));
		btnNewButton.setBounds(55, 246, 80, 27);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("수정");
		btnNewButton_1.setBackground(new Color(255,139,139));
		btnNewButton_1.setBounds(175, 246, 80, 27);
		contentPane.add(btnNewButton_1);
		
		
		btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.setBackground(new Color(255,139,139));
		btnNewButton_2.setBounds(290, 246, 80, 27);
		contentPane.add(btnNewButton_2);
		
		ImageIcon search = new ImageIcon("src/2.PNG");
		btnNewButton_3 = new JButton(search);
		btnNewButton_3.setBounds(328, 43, 25, 27);
		contentPane.add(btnNewButton_3);
	}
}




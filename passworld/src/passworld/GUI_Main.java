package passworld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends JFrame {
	JPanel search_field = new JPanel();
	JPanel result_table = new JPanel();
	JPanel func_field = new JPanel();
	ArrayList<acdata> rs_set = null;
	DBtest2 db = new DBtest2();
	
	public GUI_Main() {
		super("PASS WORLD");
		//setTitle("PASS WORLD");
		Dimension d = getToolkit().getScreenSize();
		setSize(450, 500);
        setLocation(d.width/2 - getWidth()/2, d.height/2 - getHeight()/2); 
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);
		contentPane.add(search_field, BorderLayout.NORTH);
		contentPane.add(result_table, BorderLayout.CENTER);
		contentPane.add(func_field, BorderLayout.SOUTH);
		
		Vector<String> data_row = new Vector<String>();
		data_row.addElement("사이트");
		data_row.addElement("키워드");
		data_row.addElement("ID");
		data_row.addElement("Password");
		data_row.addElement("최근변경일");
		DefaultTableModel dtm = new DefaultTableModel(data_row, 0);

		JTable table = new JTable(dtm);
		table.setPreferredScrollableViewportSize(new Dimension(400, 300));
		table.setFillsViewportHeight(true);
		
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		result_table.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);
		
		JTextField keyword_field = new JTextField("", 20);
		keyword_field.setBounds(55, 44, 259, 24);
		keyword_field.setColumns(10);
		search_field.add(keyword_field);
		
		ImageIcon search = new ImageIcon("src/2.PNG");
		JButton search_button = new JButton(search);
		search_button.setBounds(328, 43, 25, 27);
		search_field.add(search_button);
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.setRowCount(0);
				db.search(keyword_field.getText());
				rs_set = db.result;
				int rsize = rs_set.size();
				for (int i = 0; i < rsize; i++) {
					String[] a = { rs_set.get(i).getSiteid(), rs_set.get(i).getKeyword(), rs_set.get(i).getId(),
							rs_set.get(i).getPw(), rs_set.get(i).getMadedate() };
					dtm.addRow(a);
				} 
				rs_set.removeAll(rs_set);
			}
			
		});
		
		
		JCheckBox box= new JCheckBox();
		//table을 리셋할때 쓰는 명령어들 (혹시 몰라서 남겨둠)
		
		/*scrollPane.getViewport().repaint();
		dtm.setRowCount(0);
		table.repaint();
		dtm.fireTableDataChanged();
		table.updateUI();*/
		
		JButton registration = new JButton("등록");
		registration.setBackground(new Color(255, 139, 139));
		// registration.setBorder(BorderFactory.createMatteBorder(5,15,5,5,
		// Color.BLACK));
		registration.setPreferredSize(new Dimension(80, 27));
		registration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
			}
		});

		JButton modification = new JButton("수정");
		modification.setBackground(new Color(255, 139, 139));
		modification.setPreferredSize(new Dimension(80, 27));
		// modification.setBorder(BorderFactory.createMatteBorder(5,5,5,5,
		// Color.BLACK));
		modification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
			}
		});

		JButton deletion = new JButton("삭제");
		deletion.setBackground(new Color(255, 139, 139));
		deletion.setPreferredSize(new Dimension(80, 27));
		// deletion.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
		deletion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("sssss");
			}
		});
		func_field.add(registration);
		func_field.add(modification);
		func_field.add(deletion);
	}

	// public static GUI_Main main(String[] args)
	public static void main(String[] args) {
		GUI_Main frame = new GUI_Main();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// return frame;
	}

}

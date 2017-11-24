package passworld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


 public class GUI_Main extends JFrame
{
	JPanel search_field = new JPanel();
	JPanel result_table = new JPanel();
	JPanel func_field = new JPanel();
	public GUI_Main(){
		setTitle("PASS WORLD");
		setLocation(100,100);
		setSize(450,500);
	    Container contentPane = getContentPane();	
	    contentPane.setLayout(new BorderLayout());
	    contentPane.setBackground(Color.WHITE);
	    contentPane.add(search_field, BorderLayout.NORTH);
	    contentPane.add(result_table, BorderLayout.CENTER);
	    contentPane.add(func_field, BorderLayout.SOUTH);
	    
		JTextField textField = new JTextField("",20);
		textField.setBounds(55, 44, 259, 24);
		textField.setColumns(10);
		search_field.add(textField);
		
		ImageIcon search = new ImageIcon("src/2.PNG");
		JButton search_button = new JButton(search);
		search_button.setBounds(328, 43, 25, 27);
		search_field.add(search_button);
		
		Vector<String> data_row = new Vector<String>();
	    data_row.addElement("사이트");
	    data_row.addElement("키워드");
	    data_row.addElement("ID");
	    data_row.addElement("Password");
	    data_row.addElement("최근변경일");
	    DefaultTableModel dtm = new DefaultTableModel(data_row,0);

	    ArrayList<acdata> rs = select.selectAll();
		int rsize = rs.size();
		for(int i=0;i<rsize;i++) {
			String []a = {rs.get(i).getSiteid(),rs.get(i).getKeyword(),rs.get(i).getId(),rs.get(i).getPw(),rs.get(i).getMadedate()};
			dtm.addRow(a);
		}
		
		JTable table = new JTable(dtm);
	    table.setPreferredScrollableViewportSize(new Dimension(400, 300));
	    table.setFillsViewportHeight(true);
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(table);
	    

	    //Add the scroll pane to this panel.
	    result_table.add(scrollPane);
	    scrollPane.setBackground(Color.WHITE);
		
		JButton registration = new JButton("등록");
		registration.setBackground(new Color(255,139,139));
		//registration.setBorder(BorderFactory.createMatteBorder(5,15,5,5, Color.BLACK));
		registration.setPreferredSize(new Dimension(80, 27));

		JButton modification = new JButton("수정");
		modification.setBackground(new Color(255,139,139));
		modification.setPreferredSize(new Dimension(80, 27));
		//modification.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
		
		JButton deletion = new JButton("삭제");
		deletion.setBackground(new Color(255,139,139));
		deletion.setPreferredSize(new Dimension(80, 27));
		//deletion.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
		
		func_field.add(registration);
		func_field.add(modification);
		func_field.add(deletion);
	}
	public static GUI_Main main(String[] args)
	{  
		GUI_Main frame = new GUI_Main();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	    frame.setVisible(true);
		return null;
	}
}

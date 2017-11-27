package passworld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class GUI_Main extends JFrame {
	JPanel search_field = new JPanel();
	JPanel table_Panel = new JPanel();
	JPanel func_field = new JPanel();
	ArrayList<acdata> rs_set = null;
	DBtest2 db = new DBtest2();
	PageTableModel model;
	JTable result_table;

	public GUI_Main() {
		super("PASS WORLD");
		// setTitle("PASS WORLD");
		Dimension d = getToolkit().getScreenSize();
		setSize(500, 500);
		setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(search_field, BorderLayout.NORTH);
		contentPane.add(table_Panel, BorderLayout.CENTER);
		contentPane.add(func_field, BorderLayout.SOUTH);

		func_field.setBackground(Color.WHITE);
		search_field.setBackground(Color.WHITE);
		table_Panel.setBackground(Color.WHITE);

		model = new PageTableModel();
		TableColumnModel columnModel = new DefaultTableColumnModel();
		TableCellRenderer renderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer cbrenderer = new CheckboxRenderer();

		TableColumn column = new TableColumn(0);
		column.setCellRenderer(cbrenderer);
		column.setHeaderValue(" ");
		column.setPreferredWidth(5);
		columnModel.addColumn(column);

		column = new TableColumn(1);
		column.setHeaderValue("사이트 이름");
		columnModel.addColumn(column);

		column = new TableColumn(2);
		column.setHeaderValue("키워드");
		columnModel.addColumn(column);

		column = new TableColumn(3);
		column.setHeaderValue("ID");
		columnModel.addColumn(column);

		column = new TableColumn(4);
		column.setHeaderValue("PassWord");
		columnModel.addColumn(column);

		column = new TableColumn(5);
		column.setHeaderValue("등록일자");
		columnModel.addColumn(column);

		columnModel.setColumnSelectionAllowed(false); // true일시 (x,y)가 선택 false일시 (x)만 선택

		result_table = new JTable(model, columnModel);
		result_table.setRowHeight(20);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(result_table);

		// Add the scroll pane to this panel.
		table_Panel.add(scrollPane);

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
				db.result.removeAll(db.result); // 남아있는 검색 결과를 초기화
				db.search(keyword_field.getText()); // 검색창에 입력된 텍스트를 search메소드로 전달하여 검색)
				model.addAcInfo(db.result);// 검색된 결과를 table모델에 전달
				model.fireTableDataChanged();// 화면 상에 보이는 표를 업데이트
			}

		});

		/*
		 * table을 리셋할때 쓰는 명령어들 (혹시 몰라서 남겨둠) scrollPane.getViewport().repaint();
		 * dtm.setRowCount(0); table.repaint(); dtm.fireTableDataChanged();
		 * table.updateUI();
		 */

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
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
		JCheckBox checkbox;
		
		public TableCell() {
			// TODO Auto-generated constructor stub
			checkbox = new JCheckBox();
			
			checkbox.addActionListener(e -> {
				System.out.println(result_table.getValueAt(result_table.getSelectedRow(), 1));
			});
		
		}
		
		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			return checkbox;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			// TODO Auto-generated method stub
			return checkbox;
		}
		
	}
	class CheckboxRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component comp = null;
				comp=new JCheckBox();
			return comp;
		}
	}
}

class PageTableModel extends AbstractTableModel {
	private ArrayList<acdata> data;
	Object[][] dataEntries;

	public PageTableModel() {
		data = new ArrayList<acdata>();
	}

	public int getColumnCount() { // 전체 데이터의 열 수를 반환
		return 6;
	}

	public int getRowCount() { // 전체 데이터의 행 수를 반환
		return data.size();
	}

	public void addAcInfo(ArrayList<acdata> data) {
		this.data = data;
	}
	/*public boolean isCellEditable(int row, int col) {
		if(col==0) {
			return true;
		}
		return false;
	}*/
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { // 행과 열에 맞는 데이터를 table모델 위에 그린다.
		acdata tmpdt = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tmpdt.isSelected;
		case 1:
			return tmpdt.getSiteid();
		case 2:
			return tmpdt.getKeyword();
		case 3:
			return tmpdt.getId();
		case 4:
			return tmpdt.getPw();
		case 5:
			return tmpdt.getMadedate();
		// case 6 :
		// return data.get(rowIndex);
		default:
			return "invalid";
		}
	}
	
}




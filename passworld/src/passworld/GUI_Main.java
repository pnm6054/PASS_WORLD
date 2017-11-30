package passworld;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class GUI_Main extends JFrame {
	JPanel search_field = new JPanel();
	JPanel table_Panel = new JPanel();
	JPanel func_field = new JPanel();
	DBtest2 db = new DBtest2();
	PageTableModel model;
	JTable result_table;
	Login lg = new Login();
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();


	public GUI_Main()  {
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
		DefaultTableCellRenderer cbrenderer = new cellCheckRenderer();

		TableColumn column = new TableColumn(0);
		JCheckBox box = new JCheckBox();
		column.setCellRenderer(cbrenderer);
		column.setCellEditor(new DefaultCellEditor(box));
		//column.setCellEditor(checkEditor);
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
		result_table.getTableHeader().setReorderingAllowed(false); //column위치 이동 제한
		result_table.setRowHeight(20);
		result_table.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				// 이벤트가 일어난 객체 얻기
				JTable j = (JTable) e.getComponent();
				// 체크박스를 클릭할때만 값을 얻어 저장
				if(j.getSelectedColumn()==0) model.data.get(j.getSelectedRow()).isSelected=!model.data.get(j.getSelectedRow()).isSelected;
				if(e.getClickCount() == 2) { //더블클릭시 발생 이벤트
					if(j.getSelectedColumn()!=0) { //0열이 아닐때만 발생
						System.out.println(model.data.get(j.getSelectedRow()).getPw()); //해당 열의 비밀번호 출력
						StringSelection strSel = new StringSelection(model.data.get(j.getSelectedRow()).getPw()); //비밀번호 복사
						clipboard.setContents(strSel, null);//복사한 비밀번호 클립보드에 넣기
						}
					}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(result_table);
		

		// Add the scroll pane to this panel.
		table_Panel.add(scrollPane);
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
				int i = 0;
				while( i<db.result.size()) {
					if(db.result.get(i).isSelected==true) {
						System.out.println(db.result.get(i).getIndex());
						db.search(db.result.get(i).getIndex());
						Login.main(null);
						lg.prepareUpdate(db.tmpdata);
					}
				System.out.println(db.result.get(i).getIndex() + " "+ db.result.get(i).isSelected +" "+ db.result.get(i++).getSiteid());
				}
				
			}
		});

		JButton deletion = new JButton("삭제");
		deletion.setBackground(new Color(255, 139, 139));
		deletion.setPreferredSize(new Dimension(80, 27));
		// deletion.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
		deletion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ArrayList<Integer> rowid_list = new ArrayList<Integer>();
				int i = 0;
				while( i<db.result.size()) {
					if(db.result.get(i).isSelected==true) {
						System.out.println(db.result.get(i).getIndex());
						db.deleteAccount(db.result.get(i).getIndex());
					}
				System.out.println(db.result.get(i).getIndex() + " "+ db.result.get(i).isSelected +" "+ db.result.get(i++).getSiteid());
				}
				System.out.println("-----------------");
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

	/*
	 * class TableCell extends AbstractCellEditor implements TableCellEditor,
	 * TableCellRenderer{ JCheckBox checkbox;
	 * 
	 * public TableCell() { // TODO Auto-generated constructor stub checkbox = new
	 * JCheckBox();
	 * 
	 * checkbox.addActionListener(e -> {
	 * System.out.println(result_table.getValueAt(result_table.getSelectedRow(),
	 * 1)); });
	 * 
	 * }
	 * 
	 * @Override public Object getCellEditorValue() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * @Override public Component getTableCellRendererComponent(JTable table, Object
	 * value, boolean isSelected, boolean hasFocus, int row, int column) { // TODO
	 * Auto-generated method stub return checkbox; }
	 * 
	 * @Override public Component getTableCellEditorComponent(JTable table, Object
	 * value, boolean isSelected, int row, int column) { // TODO Auto-generated
	 * method stub return checkbox; }
	 * 
	 * }
	 */
	
	
	JCheckBox box = new JCheckBox();
	DefaultCellEditor checkEditor = new DefaultCellEditor(box) {

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,

				int row, int column) {

			JCheckBox editor = null;
			
			editor = (JCheckBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);

			editor.addActionListener(new ActionListener() {
			      @Override public void actionPerformed(ActionEvent e) {
			    	  model.data.get(row).isSelected=!model.data.get(row).isSelected;
			    	  model.fireTableCellUpdated(row, column);
				      }
				    });
			return editor;

		}
	};
	class cellCheckRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JCheckBox check = new JCheckBox();
			check.setSelected(db.result.get(row).isSelected);
			check.setHorizontalAlignment(SwingConstants.CENTER);

			return check;

		}

	}
	
/*	class JTableCellEventTest extends JFrame  implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = result_table.getSelectedRow();
			  int col = result_table.getSelectedColumn();
			  for (int i = 0; i < result_table.getColumnCount(); i++) {
			   System.out.print(row+col); 
			  }
		}
		public void startEvent(){
			result_table.addMouseListener(this);
			 }


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		 
		}*/
	
	class PageTableModel extends AbstractTableModel {
		private ArrayList<acdata> data;

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

		public boolean isCellEditable(int row, int col) {
			if (col == 0) {
				return true;
			}
			return false;
		}

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

}




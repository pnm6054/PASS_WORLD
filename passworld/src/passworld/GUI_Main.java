package passworld;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.*;

public class GUI_Main extends JFrame {
	JPanel search_field = new JPanel();
	JPanel table_Panel = new JPanel();
	JPanel func_field = new JPanel();
	DB db = new DB();
	PageTableModel model;
	JTable result_table;
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	JCheckBox pw_hider;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public GUI_Main()  {
		super("PASS WORLD");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./1.png"));
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
		DefaultTableCellRenderer statusrenderer = new CellStatusRenderer();
		TableCellRenderer renderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer cbrenderer = new cellCheckRenderer();
		DefaultTableCellRenderer pw_hider = new PW_Hider_Renderer();

		TableColumn column = new TableColumn(0);
		JCheckBox box = new JCheckBox();
		column.setCellRenderer(cbrenderer);
		column.setCellEditor(new DefaultCellEditor(box));
		column.setHeaderValue(" ");
		column.setPreferredWidth(5);
		columnModel.addColumn(column);

		column = new TableColumn(1);
		column.setHeaderValue("����Ʈ �̸�");
		columnModel.addColumn(column);

		column = new TableColumn(2);
		column.setHeaderValue("Ű����");
		columnModel.addColumn(column);

		column = new TableColumn(3);
		column.setHeaderValue("ID");
		columnModel.addColumn(column);

		TableColumn column_pw = new TableColumn(4);
		column_pw.setHeaderValue("PassWord");
		columnModel.addColumn(column_pw);

		column = new TableColumn(5);
		column.setHeaderValue("�������");
		column.setCellRenderer(statusrenderer);
		columnModel.addColumn(column);

		columnModel.setColumnSelectionAllowed(false); // true�Ͻ� (x,y)�� ���� false�Ͻ� (x)�� ����
		
		result_table = new JTable(model, columnModel);
		result_table.getTableHeader().setReorderingAllowed(false); //column��ġ �̵� ����
		result_table.setRowHeight(20);
		result_table.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				// �̺�Ʈ�� �Ͼ ��ü ���
				JTable j = (JTable) e.getComponent();
				// üũ�ڽ��� Ŭ���Ҷ��� ���� ��� ����
				if(j.getSelectedColumn()==0) model.data.get(j.getSelectedRow()).isSelected=!model.data.get(j.getSelectedRow()).isSelected;
				if(e.getClickCount() == 2) { //����Ŭ���� �߻� �̺�Ʈ
					if(j.getSelectedColumn()!=0) { //0���� �ƴҶ��� �߻�
						db.updateAccount(model.data.get(j.getSelectedRow()).getIndex()); //Ŭ���� ������ DB�� �׸� count 1�� �߰�
						System.out.println(model.data.get(j.getSelectedRow()).getPw()); //�ش� ���� ��й�ȣ ���
						StringSelection strSel = new StringSelection(model.data.get(j.getSelectedRow()).getPw()); //��й�ȣ ����
						JOptionPane.showMessageDialog(null, model.data.get(j.getSelectedRow()).getPw() + " ����Ϸ�", "�˸�",JOptionPane.INFORMATION_MESSAGE,null); //��й�ȣ ���� �޼���
						clipboard.setContents(strSel, null);//������ ��й�ȣ Ŭ�����忡 �ֱ�
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
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setPreferredSize(new Dimension(getWidth()-30, getHeight()-100));
		
		JTextField keyword_field = new JTextField("", 20);
		keyword_field.setBounds(55, 44, 259, 24);
		keyword_field.setColumns(10);
		keyword_field.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)
			{
			        // EnterŰ�� ���ȴٸ�
			        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			        	System.out.println("pressed");
			        	db.result.removeAll(db.result); // �����ִ� �˻� ����� �ʱ�ȭ
						db.search(keyword_field.getText()); // �˻�â�� �Էµ� �ؽ�Ʈ�� search�޼ҵ�� �����Ͽ� �˻�)
						model.addAcInfo(db.result);// �˻��� ����� table�𵨿� ����
						model.fireTableDataChanged();// ȭ�� �� ���̴� ǥ�� ������Ʈ
			        }
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		search_field.add(keyword_field);


		ImageIcon search = new ImageIcon("./2.PNG");
		JButton search_button = new JButton(search);
		search_button.setPreferredSize(new Dimension(25, 25));
		search_field.add(search_button);
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.result.removeAll(db.result); // �����ִ� �˻� ����� �ʱ�ȭ
				db.search(keyword_field.getText()); // �˻�â�� �Էµ� �ؽ�Ʈ�� search�޼ҵ�� �����Ͽ� �˻�)
				model.addAcInfo(db.result);// �˻��� ����� table�𵨿� ����
				model.fireTableDataChanged();// ȭ�� �� ���̴� ǥ�� ������Ʈ
			}
		});
		/*
		 * table�� �����Ҷ� ���� ��ɾ�� (Ȥ�� ���� ���ܵ�) scrollPane.getViewport().repaint();
		 * dtm.setRowCount(0); table.repaint(); dtm.fireTableDataChanged();
		 * table.updateUI();
		 */
		
		JCheckBox pw_hider_ck = new JCheckBox(); //��й�ȣ�� �����ִ� ������ ���� üũ�ڽ�
		pw_hider_ck.setLocation(350, 43);
		pw_hider_ck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
				column_pw.setCellRenderer(pw_hider);
				model.fireTableDataChanged();
				}
				else { column_pw.setCellRenderer(renderer);
				model.fireTableDataChanged();}
				}
		});
		
		search_field.add(pw_hider_ck);

		JButton registration = new JButton("���");
		registration.setBackground(new Color(255, 139, 139));
		// registration.setBorder(BorderFactory.createMatteBorder(5,15,5,5,
		// Color.BLACK));
		registration.setPreferredSize(new Dimension(80, 27));
		registration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login lg = new Login();
				lg.setVisible(true);
			}
		});
		
		JButton modification = new JButton("����");
		modification.setBackground(new Color(255, 139, 139));
		modification.setPreferredSize(new Dimension(80, 27));
		//modification.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));
		modification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0; //while���� �ݺ��� ���� ����
				int j = 0; //���õ� �׸��� ������ ����
				int k = 0; //while���� �۵� �� ���õ� �׸��� ��ȣ�� ����
				while( i<db.result.size()) { //���õ� �׸��� ������ ��ȯ
					if(db.result.get(i).isSelected==true) {
						k=i;
						j++;
					}
					System.out.println(db.result.get(i).getIndex() + " "+ db.result.get(i).isSelected +" "+ db.result.get(i++).getSiteid());
					//�̰� ������ ���ѷ��� ����� �� (�����ʿ�)
				}
				switch( j ) //1���� �׸��� ���õǾ��� ���� ����ǰ� �Ѵ�.
				{
					case 0:
						JOptionPane.showMessageDialog(null, "���õ� �׸��� �����ϴ�.");
					break;
					
					case 1:
						
						//DefaultTableModel���� ������ �÷��� ������ ��������
						
						System.out.println(k);
						Login modif = new Login(db.result.get(k));
						modif.setVisible(true);
						//db.search(db.result.get(k).getIndex());
						//���̾�α� ����
						//MemberDialog memberDialog = new MemberDialog(id, name, age, addr);
						//memberDialog.jDialog.setModal(true);
						//memberDialog.jDialog.setVisible(true);
					break;
					
					default:
						JOptionPane.showMessageDialog(null, "�ϳ��� �÷��� �������ּ���.");
					break;
				}
			}
		});

		JButton deletion = new JButton("����");
		deletion.setBackground(new Color(255, 139, 139));
		deletion.setPreferredSize(new Dimension(80, 27));
		// deletion.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
		deletion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "������ �׸��� ������ �� �����ϴ�.\n�����Ͻðڽ��ϱ�?", "���", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(choice==0){
					int i = 0;
					int j = 0;
					while( i<db.result.size()) {
						if(db.result.get(i).isSelected==true) {
							j++;
							System.out.println(db.result.get(i).getIndex());
							db.deleteAccount(db.result.get(i).getIndex());
						}
						System.out.println(db.result.get(i).getIndex() + " "+ db.result.get(i).isSelected +" "+ db.result.get(i++).getSiteid());
					}
					JOptionPane.showMessageDialog(null, j + "�� �׸� ����");
					System.out.println("-----------------");
					}
			}
		});
		func_field.add(registration);
		func_field.add(modification);
		func_field.add(deletion);
	}

	/*// public static GUI_Main main(String[] args)
	public static void main(String[] args) {
		GUI_Main frame = new GUI_Main();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// return frame;
	}*/
	/*
	class DefaultCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if()
			dtcr.setBackground(Color.red);
	}
*/
	class CellStatusRenderer extends DefaultTableCellRenderer {

		Date today = new Date();
			private JLabel label ;
		    /**
		     * 4XX�� �ڵ忡�� ������ ���ڻ�
		     */
		    private ColorUIResource rs = new ColorUIResource(0xff, 0x0, 0x0);
		    //private ColorUIResource rs300 = new ColorUIResource(0xaa, 0x5a, 0xff);
		    
		    public CellStatusRenderer(){
		        this.label = new JLabel();
		        this.label.setOpaque(true);
		        this.label.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		        System.out.println("created StatusCodeRenderer");
		    }
		    
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        Color fg = UIManager.getColor("Table.foreground");
		        Color bg = UIManager.getColor("Table.background");
		        try {
		        Date date_gen;
					date_gen = formatter.parse(db.result.get(row).getMadedate());
				
		        int betweendays = (int) ((today.getTime()-date_gen.getTime())/(24 * 60 * 60 * 1000));
		        if ( betweendays >= 60 ){
		            fg = rs;
		        }
		       /* else if ( betweendays >= 0){
		            fg = rs300;
		        }*/
		        } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        label.setBackground(bg);
		        label.setForeground(fg);
		        label.setText(value.toString());
		        label.setFont(table.getFont());
		        return label;
		    }
		
	}
	
	class cellCheckRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JCheckBox check = new JCheckBox();
			check.setSelected(db.result.get(row).isSelected);
			check.setHorizontalAlignment(SwingConstants.CENTER);

			return check;

		}
	}
	
	class PW_Hider_Renderer extends DefaultTableCellRenderer { //��й�ȣ �κ��� �����ִ� �� ����

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JLabel hided_pw = new JLabel("************");
			

			return hided_pw;

		}
	}
	
	class PageTableModel extends AbstractTableModel {
		private ArrayList<acdata> data;

		public PageTableModel() {
			data = new ArrayList<acdata>();
		}

		public int getColumnCount() { // ��ü �������� �� ���� ��ȯ
			return 6;
		}

		public int getRowCount() { // ��ü �������� �� ���� ��ȯ
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
		public Object getValueAt(int rowIndex, int columnIndex) { // ��� ���� �´� �����͸� table�� ���� �׸���.
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
			default:
				return "invalid";
			}
		}
	}
}
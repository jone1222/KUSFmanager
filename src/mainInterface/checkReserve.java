package mainInterface;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import mainInterface.ciganpyo.TableCell;

public class checkReserve extends JPanel {
	private JPanel chReservePanel, subPanel1, subPanel2, subPanel3;
	private JLabel label1, label2, label3;
	//private JButton cancelBtn, editBtn;
	private JTable table;
	
	public checkReserve() {
		String []a = {"실습실 이름", "09:00 ~ 10:00", "10:00 ~ 11:00", "11:00 ~ 12:00", "12:00 ~ 13:00",
				"13:00 ~ 14:00", "14:00 ~ 15:00", "15:00 ~ 16:00", "16:00 ~ 17:00", "예약 취소", "예약 수정"};
		String [][]b = {
				{"0","0","0","0","0","예약 완료","예약 완료","0","0"}};
		
		DefaultTableModel model = new DefaultTableModel(b,a);
		table = new JTable(model);
		JScrollPane sc = new JScrollPane(table);
		
		table.getColumnModel().getColumn(9).setCellRenderer(new TableCell(1));
		table.getColumnModel().getColumn(9).setCellEditor(new TableCell(1));
		table.getColumnModel().getColumn(10).setCellRenderer(new TableCell(2));
		table.getColumnModel().getColumn(10).setCellEditor(new TableCell(2));
		
		chReservePanel = new JPanel(new BorderLayout(150,150));
		chReservePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100)); // 패널 내 상좌하우 순으로 여백
		
		subPanel1 = new JPanel(new FlowLayout());
		subPanel2 = new JPanel(new BorderLayout());
		subPanel3 = new JPanel(new FlowLayout());
		
		label1 = new JLabel("ㅇㅇㅇ님의 예약 내역입니다.");
		
		//cancelBtn = new JButton("예약취소");
		//editBtn = new JButton("예약수정");
		
		subPanel1.add(label1);
		subPanel2.add(sc, BorderLayout.CENTER);
		subPanel2.setVisible(true);
		
		chReservePanel.add(subPanel1, BorderLayout.NORTH);
		chReservePanel.add(subPanel2, BorderLayout.CENTER);
		chReservePanel.add(subPanel3, BorderLayout.SOUTH);
		
		DefaultTableModel m = (DefaultTableModel)table.getModel();
		table.updateUI();
	}
	
	public JPanel makePanel() {
		return chReservePanel;
	}
	
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;
		
		public TableCell(int temp) {
			if(temp==1) {
				jb = new JButton("취소");
				
				jb.addActionListener(e -> { // 예약 버튼 클릭시 이벤트 처리하기 
					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				});
			} else {
				jb = new JButton("수정");
				
				jb.addActionListener(e -> { // 예약 버튼 클릭시 이벤트 처리하기 
					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				});
			}
		}
		
		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, 
				boolean isSelected, boolean hasFocus, int row, int col) {
			// TODO Auto-generated method stub
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, 
				boolean isSelected, int row, int col) {
			// TODO Auto-generated method stub
			return jb;
		}
		
	}
}

package mainInterface;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

public class ciganpyo extends JPanel {
	
	JPanel show_schedule;
	JTable table;
	
	public ciganpyo() {
		String []a = {"09:00 ~ 10:00", "10:00 ~ 11:00", "11:00 ~ 12:00", "12:00 ~ 13:00",
				"13:00 ~ 14:00", "14:00 ~ 15:00", "15:00 ~ 16:00", "16:00 ~ 17:00"};
		String [][]b = {
				{"0","예약 완료","예약 완료","0","0","예약 완료","0","0","0"}};
		
		//1. 모델과 데이터를 연결
		DefaultTableModel model = new DefaultTableModel(b,a);
		table = new JTable(model);
		
		// 결과적으로는 Jscrollpane 추가한다
		JScrollPane sc = new JScrollPane(table);
		
		for(int i = 0; i < 8; i++) {
			String str = (String) model.getValueAt(0, i);
			if(str.equals("0")) {
				table.getColumnModel().getColumn(i).setCellRenderer(new TableCell());
				table.getColumnModel().getColumn(i).setCellEditor(new TableCell());
			}
		}

		show_schedule = new JPanel(new BorderLayout());
		show_schedule.add(sc, BorderLayout.CENTER);
		show_schedule.setVisible(true);
		
		// show_schedule에테이블 추가
		//원본데이터를 건들지 않고 table 의 매개변수인 model 에 있는 데이터를 변경
		DefaultTableModel m = (DefaultTableModel)table.getModel();
		table.updateUI();
		
	}

	public JPanel get_schedule() {
		return show_schedule;
	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;
		
		public TableCell() {
			jb = new JButton("예약");
			
			jb.addActionListener(e -> { // 예약 버튼 클릭시 이벤트 처리하기 
				System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
			});
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


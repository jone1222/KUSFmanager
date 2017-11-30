package mainInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ciganpyo extends JPanel {
	
	JPanel show_schedule;
	public ciganpyo() {
		String []a = {"a","b","c"};
		String [][]b = {{"a1","a2","a3"},
						{"b2","b2","b3"},
						{"c1","c2","c3"}
		
					};
		//1. 모델과 데이터를 연결
		DefaultTableModel model = new DefaultTableModel(b,a);
		JTable table = new JTable(model);
		
		//  결과적으로는 Jscrollpane 추가한다
		JScrollPane sc = new JScrollPane(table);

		show_schedule = new JPanel();
		show_schedule.add(sc);
		
		// show_schedule에테이블 추가
		//원본데이터를 건들지 않고 table 의 매개변수인 model 에 있는 데이터를 변경
		DefaultTableModel m = (DefaultTableModel)table.getModel();
		m.insertRow(1, new Object[] {"이름","학번","사용시간정보"});
		table.updateUI();
		
		
		
		//
		
	}
			//테이블에 
	public JPanel get_schedule() {
		return show_schedule;
	}
}

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
		//1. �𵨰� �����͸� ����
		DefaultTableModel model = new DefaultTableModel(b,a);
		JTable table = new JTable(model);
		
		//  ��������δ� Jscrollpane �߰��Ѵ�
		JScrollPane sc = new JScrollPane(table);

		show_schedule = new JPanel();
		show_schedule.add(sc);
		
		// show_schedule�����̺� �߰�
		//���������͸� �ǵ��� �ʰ� table �� �Ű������� model �� �ִ� �����͸� ����
		DefaultTableModel m = (DefaultTableModel)table.getModel();
		m.insertRow(1, new Object[] {"�̸�","�й�","���ð�����"});
		table.updateUI();
		
		
		
		//
		
	}
			//���̺� 
	public JPanel get_schedule() {
		return show_schedule;
	}
}

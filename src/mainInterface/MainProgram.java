package mainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;  
import unitDatabase.Database; 

public class MainProgram extends JFrame implements ActionListener {
	private JButton btn1;
	private JFrame frm;
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel label1;
	private JLabel label2;
	
	public MainProgram() {
		frm = new JFrame();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel1 = new JPanel();
		panel2 = new JPanel();

		label1 = new JLabel("�����ϱ�");
		label2 = new JLabel("����Ȯ��");
		btn1 = new JButton("����ȭ��");
		btn1.addActionListener(this);
		
		
		panel1.add(label1);
		panel2.add(label2);
		panel1.add(btn1);
		
		tabbedPane.add("reserve", panel1);
		tabbedPane.add("checkReserve", panel2);
		
		frm.add(tabbedPane);
		frm.setSize(500, 500);
		frm.setTitle("����");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			//panel3 = new JPanel(); ���� �������Դϴ�
		}
	}

	public static void main(String[] args) throws SQLException {
		Database DB = new Database(); 
    		DB.open(); 
    		DB.readMeta(" "); 
		new MainProgram();
	}

}

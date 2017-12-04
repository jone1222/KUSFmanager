package mainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

public class loginPage extends JPanel {
	private JButton loginSubmit;
	private JPanel loginPanel, subPanel1, subPanel2, subPanel3;
	private JLabel id, pw, label;
	private JTextField inputID, inputPW;

	public loginPage() {
		loginPanel = new JPanel(new BorderLayout(150,150));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100)); // �г� �� �����Ͽ� ������ ����
		subPanel1 = new JPanel(new FlowLayout());
		subPanel2 = new JPanel(new GridLayout(2,2));
		subPanel3 = new JPanel(new FlowLayout());
		
		label = new JLabel("�α��� ������");
		id = new JLabel("I D : ");
		pw = new JLabel("P W : ");
		inputID = new JTextField();
		inputPW = new JTextField();
		loginSubmit = new JButton("�α���");
		loginSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // �α����� ���Ǿ�� �� ������ �����ϵ���
				// TODO Auto-generated method stub
				
			}
			
		});
		
		subPanel1.add(label);
		subPanel2.add(id); subPanel2.add(inputID);
		subPanel2.add(pw); subPanel2.add(inputPW);
		subPanel3.add(loginSubmit);
		
		loginPanel.add(subPanel1, BorderLayout.NORTH);
		loginPanel.add(subPanel2, BorderLayout.CENTER);
		loginPanel.add(subPanel3, BorderLayout.SOUTH);
	}
	
	public JPanel makePanel() {
		return loginPanel;
	}
}

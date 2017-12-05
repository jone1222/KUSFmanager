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

	public loginPage() throws IOException {
		loginPanel = new JPanel(new BorderLayout(100,100));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 300, 150, 300)); // 패널 내 상좌하우 순으로 여백
		
		subPanel1 = new JPanel(new FlowLayout());
		subPanel2 = new JPanel(new GridLayout(2,1));
		subPanel3 = new JPanel(new FlowLayout());
		
		BufferedImage img1 = ImageIO.read(new File("img\\kuMark.jpg")); // card1 에 이미지를 보여준다
		JLabel floor1 = new JLabel(new ImageIcon(img1)); 
		floor1.setSize(600, 600);
		floor1.setLocation(10, 0);
		
		//label = new JLabel("로그인 페이지");
		id = new JLabel("I D : ");
		pw = new JLabel("P W : ");
		inputID = new JTextField();
		inputID.setText("I  D");
		inputPW = new JTextField();
		inputPW.setText("P  W");
	
		loginSubmit = new JButton(new ImageIcon("img/loginImg2.png"));
		ImageIcon rolloverIcon = new ImageIcon("img/loginImg.png");
		
		loginSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // 로그인이 허용되어야 탭 접속이 가능하도록
				// TODO Auto-generated method stub
				
			}
			
		});
		
		loginSubmit.setRolloverIcon(rolloverIcon);
		loginSubmit.setBorderPainted(false); 
		loginSubmit.setFocusPainted(false); 
		loginSubmit.setContentAreaFilled(false);
		
		subPanel1.add(floor1);
		//subPanel2.add(id); 
		subPanel2.add(inputID);
		//subPanel2.add(pw); 
		subPanel2.add(inputPW);
		subPanel3.add(loginSubmit);
		
		loginPanel.add(subPanel1, BorderLayout.NORTH);
		loginPanel.add(subPanel2, BorderLayout.CENTER);
		loginPanel.add(subPanel3, BorderLayout.SOUTH);
		//loginPanel.setBackground(Color.WHITE);
	}
	
	public JPanel makePanel() {
		return loginPanel;
	}
}

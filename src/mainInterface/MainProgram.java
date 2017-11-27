package mainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;  
//import unitDatabase.Database; 

public class MainProgram extends JFrame implements ActionListener {
	int screenHeight;
	int screenWidth;
	
	private JButton btn1,btn2,btn3,btn4,btn5,btn6,btn7;
	private JFrame frm;
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JPanel panel2;
	CardLayout card;
	JPanel card1, card2, card3,card4;
	
	Point pt;
	JLabel label;
	
	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel2 = new JPanel();
		
		btn1 = new JButton("다음"); 
		btn1.addActionListener(this); 
		btn1.setBounds(1000, 650, 100, 20);
		
		btn2 = new JButton("이전"); 		
		btn2.addActionListener(this);
		btn2.setBounds(900, 650, 100, 20);
		
		btn3 = new JButton("다음"); 	
		btn3.addActionListener(this);
		btn3.setBounds(1000, 650, 100, 20);
		
		btn4 = new JButton("전화면3"); 		
		btn4.addActionListener(this);
		
		btn5 = new JButton("다음화면3"); 		
		btn5.addActionListener(this);
		
		btn6 = new JButton("전화면4"); 		
		btn6.addActionListener(this);
		
		btn7 = new JButton("다음화면4"); 		
		btn7.addActionListener(this);
		
		card = new CardLayout();
		panel1 = new JPanel(card);
		card1 = new JPanel(); 
		card1.setLayout(null);
		
		BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
		JLabel floor1 = new JLabel(new ImageIcon(img1));
		floor1.setSize(600, 600); floor1.setLocation(10, 0);
		BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
		JLabel floor2 = new JLabel(new ImageIcon(img2));
		floor2.setSize(600, 600); floor2.setLocation(620, 0);
		
		pt = new Point(0, 0);
		label = new JLabel(pt.x+","+pt.y);
		label.setSize(80, 40);
		
		card1.add(floor1);
		card1.add(floor2);
		card1.add(btn1);// 다음으로 눌러서 예약화면 넘어가기
		card1.add(label);
		card1.addMouseListener(new MyMAdapter());
		
		card2 = new JPanel();
		card2.setLayout(null);
		card2.add(new roomPanel("img\\3d.png"));
		card2.add(btn2); // 두번쨰  화면에서 버튼 되돌아가기
		card2.add(btn3);// 두번쨰 화면에서 버튼 다음화면
		
		card3 = new JPanel();
		card3.setLayout(null);
		calendar cal = new calendar();
		card3.add(cal);
		card3.add(btn4);
		card3.add(btn5);
		
		card4 = new JPanel();
		card4.setLayout(null);
		
		panel1.add("1",card1);
		panel1.add("2",card2);
		panel1.add("3",card3);
		panel1.add("4",card4);
		
		tabbedPane.add("reserve", panel1);
		tabbedPane.add("checkReserve", panel2);
		
		c.add(tabbedPane);
		frm.setTitle("예약");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize(); // 스크린의 폭과 높이 정보를 가짐
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;
		frm.setSize(screenWidth, screenHeight); // 사이즈 지정
		frm.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			card.show(panel1, "2");
		}
		else if(e.getSource()==btn2) {
			card.show(panel1, "1");
		}
		else if(e.getSource()==btn3) {
			card.show(panel1, "3");
		}
		else if(e.getSource()==btn4) {
			card.show(panel1, "2");
		}
		else if(e.getSource()==btn5) {
			card.show(panel1, "4");
		}
		
	}
	
	class roomPanel extends JPanel { // 각각의 실습실 정보 
		
		public roomPanel(String imgSrc) throws IOException { // 이미지 경로 받아와서 분류
			BufferedImage img = ImageIO.read(new File(imgSrc));
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setSize(img.getWidth(), img.getHeight());
			imgLabel.setLocation(0, 0);
			this.setSize(img.getWidth(), img.getHeight());
			this.setVisible(true);
		}
	}
	
	class MyMAdapter extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			pt = e.getPoint();
			label.setText(pt.x+","+pt.y);
		}
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

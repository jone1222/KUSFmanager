package mainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		//panel1 = new JPanel(); // �̰� ��� ī�� ���̾ƿ��� ���
		panel2 = new JPanel();
		
		btn1 = new JButton("����"); 
		btn1.addActionListener(this); 
		btn1.setBounds(1000, 650, 100, 20);
		
		btn2 = new JButton("����"); 		
		btn2.addActionListener(this);
		btn2.setBounds(900, 650, 100, 20);
		
		btn3 = new JButton("����"); 	
		btn3.addActionListener(this);
		btn3.setBounds(1000, 650, 100, 20);
		
		btn4 = new JButton("��ȭ��3"); 		
		btn4.addActionListener(this);
		
		btn5 = new JButton("����ȭ��3"); 		
		btn5.addActionListener(this);
		
		btn6 = new JButton("��ȭ��4"); 		
		btn6.addActionListener(this);
		
		btn7 = new JButton("����ȭ��4"); 		
		btn7.addActionListener(this);
		
		card = new CardLayout();
		panel1 = new JPanel(card);
		
		card1 = new JPanel(); 
		card1.setLayout(null);
		
		BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
		JLabel floor1 = new JLabel(new ImageIcon(img1));
		floor1.setSize(600, 600); floor1.setLocation(0, 0);
		BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
		JLabel floor2 = new JLabel(new ImageIcon(img2));
		floor2.setSize(600, 600); floor2.setLocation(650, 0);
		card1.add(floor1);
		card1.add(floor2);
		
		card2 = new JPanel();
		card2.setLayout(null);
		
		
		card3 = new JPanel();
		card3.setLayout(null);
		card4 = new JPanel();
		card4.setLayout(null);
		
		card1.add(btn1);// �������� ������ ����ȭ�� �Ѿ��
		card2.add(btn2); // �ι���  ȭ�鿡�� ��ư �ǵ��ư���
		card2.add(btn3);// �ι��� ȭ�鿡�� ��ư ����ȭ��
		
		panel1.add("1",card1);
		panel1.add("2",card2);
		panel1.add("3",card3);
		panel1.add("4",card4);

		card3.add(btn4);
		card3.add(btn5);
		
		tabbedPane.add("reserve", panel1);
		tabbedPane.add("checkReserve", panel2);
		
		c.add(tabbedPane);
		frm.setTitle("����");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize(); // ��ũ���� ���� ���� ������ ����
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;
		frm.setSize(screenWidth, screenHeight); // ������ ����
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
	
	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

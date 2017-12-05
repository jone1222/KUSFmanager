package mainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
//import unitDatabase.Database; 

public class MainProgram extends JFrame implements ActionListener {
	private int screenHeight;
	private int screenWidth;
	
	private JButton btn1, btn2;
	private JFrame frm;
	private JTabbedPane tabbedPane;
	private JPanel panel1, panel2;
	private CardLayout card;
	private JPanel card1, card2, card3, card4;
	private JPanel panelCard, panelFlow;
	private Point pt;
	private JLabel label1;
	private JSlider slider;
	private ImageIcon bgImage; // �α��� ȭ�� �̹���
	public JLabel bglabel;

	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel2 = new JPanel();

		btn1 = new JButton("����");
		btn1.addActionListener(this);
		btn1.setBounds(1000, 650, 100, 20); // �̰� �ǹؿ� �ִ� �׻� flow layout �ִ°�

		btn2 = new JButton("����"); // �̰͵�
		btn2.addActionListener(this);
		btn2.setBounds(900, 650, 100, 20);

		card = new CardLayout();
		panel1 = new JPanel(new BorderLayout());
		panelCard = new JPanel(card);
		panelFlow = new JPanel(new FlowLayout());
		
		panel1.add(panelCard, BorderLayout.CENTER); // panelcard���� card1, card2, card3, card4 �� �ְ� 
		// card 4���� cardlayout���� �ϳ��� ��ȯ�ؼ� �����ش�
		panel1.add(panelFlow, BorderLayout.SOUTH);
		
		bgImage = new ImageIcon("img/konkukuniv.jpg");
		bglabel = new JLabel(bgImage);
		
		card1 = new JPanel();
		
		/*card1 = new JPanel() {
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(bgImage.getImage(), 0, 0, null);
					//setOpaque(false);
				}
		};*/
		
		BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg")); // card1 �� �̹����� �����ش�
		JLabel floor1 = new JLabel(new ImageIcon(img1)); // 
		floor1.setSize(600, 600);
		floor1.setLocation(10, 0);
		
		BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg")); // card1  �� �̹����� �����ش�
		JLabel floor2 = new JLabel(new ImageIcon(img2));
		floor2.setSize(600, 600);
		floor2.setLocation(620, 0);
		
		pt = new Point(0, 0);
		label1 = new JLabel(pt.x + "," + pt.y);
		label1.setSize(80, 40);		
		card1.add(label1);
		card1.add(floor1);
		card1.add(floor2);
		panelFlow.add(btn2);// �������� ������ ����ȭ�� �Ѿ��
		panelFlow.add(btn1);				
		card1.addMouseListener(new MyMAdapter());
		

       // ************************* card 2 ����

		
		card2 = new JPanel();
		card2.setLayout(new BorderLayout());	
		
		
		
		//************************* card 3 ����
		
		calendar cal = new calendar();
		ciganpyo cig = new ciganpyo(cal);	
		
		card3 = new JPanel(new GridLayout(3, 1));
		JPanel card3_1 = new JPanel(new GridLayout(1,2));
		JPanel card3_2 = new JPanel(new BorderLayout());
		JPanel card3_3 = new JPanel(new GridLayout(2,1));

		card3_1.add(cal.getcal3());
		card3_1.add(cal.getcal2());
		card3_2.add(cig.get_schedule(), BorderLayout.CENTER);		
		
		card3.add(card3_1);
		card3.add(card3_2);
		
		//*********************** card 4 ����		-> ���ݿ� card3�̶� 4��ħ

		//card4 = new JPanel(new GridLayout(2,1));
		JPanel card4_1 = new JPanel(null);
		card4_1.setSize(screenWidth/7,screenHeight/2); 
		// ī�� 4�ȿ� ��� ������ �� �ϳ� �ؿ� �л����� �Է��� ���� gridlayout ���� 2��1�� ����
		
		JLabel label2 = new JLabel("�߰� �л� ���� �Է�");
		label2.setSize(150, 40);
		label2.setLocation(550, 10);

		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMaximum(6);
		slider.setMinimum(1);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(150, 50, 900, 70);
		
		card4_1.add(label2);
		card4_1.add(slider);
		
		JPanel card4_2 = new JPanel();
		studentInfo std_info = new studentInfo();
		std_info.makePanel();
		card4_2.removeAll();
		card4_2.add(std_info.get_stdinfo());
		
		card3_3.add(card4_1);
		card3_3.add(card4_2);
		card3.add(card3_3);
		
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				studentInfo std_info = null;
				if (e.getSource() == slider) {
					card3_3.removeAll();
					card4_2.removeAll();
					std_info = new studentInfo();
					// std_info.get_stdinfo().removeAll();
					std_info.setNum(slider.getValue());
					card4_2.add(std_info.get_stdinfo());
					card3_3.add(card4_1); // �����̴���
					card3_3.add(card4_2); // ���� �Է¹޴� ��
					card3.add(card3_3);   // �����̴��� �Է¹޴� �� ��ü
				}
			}

		});
		
		panelCard.add("1", card1);
		panelCard.add("2", card2);
		panelCard.add("3", card3);
		//panelCard.add("4", card4);
		
		loginPage login = new loginPage();
		
		tabbedPane.add("�α���", login.makePanel());
		tabbedPane.add("�����ϱ�", panel1);
		
		checkReserve cr = new checkReserve();
		tabbedPane.add("����Ȯ��", cr.makePanel());

		c.add(tabbedPane);
		frm.setTitle("����");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize(); // ��ũ���� ���� ���� ������ ����
		screenHeight = screenSize.height-50;
		screenWidth = screenSize.width;
		frm.setSize(screenWidth, screenHeight); // ������ ����
		frm.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			card.next(panelCard);
		} else if (e.getSource() == btn2) {
			card.previous(panelCard);
		}
	}

	class roomPanel extends JPanel { // ������ �ǽ��� ����

		public roomPanel(String imgSrc) throws IOException { // �̹��� ��� �޾ƿͼ� �з�
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
			label1.setText(pt.x + "," + pt.y);
		}
	}

	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

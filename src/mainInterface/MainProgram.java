package mainInterface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import unitClass.Item;
import unitDatabase.Database;

public class MainProgram extends JFrame implements ActionListener {
	int screenHeight;
	int screenWidth;

	private JButton nextBtn, prevBtn;

	private JFrame frm;

	private JTabbedPane tabbedPane;
	private JPanel mainPanel, userPanel;

	private CardLayout card;

	private JPanel mapCard, timeCard, timePart, userlistPart;
	
	private JPanel reservePanel, btnPanel;

	private JPanel roomPanel;
	
	private loginPage loginpage;
	
	private Point pt;
	private JLabel pointLabel;
	private JLabel pointLabel2;
	
	private String m_selectedroom = "아무것도없당";
	
	private JSlider slider;

	private JTextField[] id_textField;
	private JTextField[] name_textField;

	private Database DB;

	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		card = new CardLayout();
		DB = new Database();
		DB.open();

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		userPanel = new ReservePanel();
		mainPanel = new JPanel(new BorderLayout());

		initReservePanel();

		initBtnPanel();

		mainPanel.add(reservePanel, BorderLayout.CENTER);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		
		loginpage = new loginPage();
		
		tabbedPane.add("login",loginpage.makePanel());
		tabbedPane.add("reserve", mainPanel);
		tabbedPane.add("checkReserve", userPanel);

		c.add(tabbedPane);

		
		((ReservePanel)userPanel).updateTable("jone1222");
		
		frm.setTitle("건국대 Smart Factory 예약 프로그램");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenHeight = screenSize.height - 50;
		screenWidth = screenSize.width;
		frm.setSize(screenWidth, screenHeight);
		frm.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextBtn) {
			if (getTopCard().equals("userCard")) {
				for (int i = 0; i < id_textField.length; i++) {
					try {
						System.out.println(DB.isExistUser(id_textField[i].getText(), name_textField[i].getText()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			card.next(reservePanel);

		} else if (e.getSource() == prevBtn) {
			card.previous(reservePanel);
		}
	}

	public String getTopCard() {
		JPanel Card = null;
		for (Component comp : reservePanel.getComponents()) {
			if (comp.isVisible() == true) {
				Card = (JPanel) comp;
			}
		}
		return Card.getName();
	}

	public class roomPanel extends JPanel {

		public roomPanel(String imgSrc) throws IOException {
			BufferedImage img = ImageIO.read(new File(imgSrc));
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setSize(img.getWidth(), img.getHeight());
			// imgLabel.setLocation(0, 0);
			this.setSize(img.getWidth(), img.getHeight());
			this.setVisible(true);
		}
	}

	public class MyMAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			String selectedroom = "";
			Component target = (Component) e.getSource();
			pt = target.getMousePosition();
			if(target.getName().equals("floor2")) {
				if (pt.x > 169 && pt.y > 141 && pt.x < 198 && pt.y < 180) {
					selectedroom = "설계실01";
				}
				if (pt.x > 199 && pt.y > 140 && pt.x < 230 && pt.y < 179) {
					selectedroom = "설계실02";
				}
				if (pt.x > 232 && pt.y > 141 && pt.x < 265 && pt.y < 179) {
					selectedroom = "설계실03";
				}
				if (pt.x > 169 && pt.y > 181 && pt.x < 198 && pt.y < 220) {
					selectedroom = "설계실04";
				}
				if (pt.x > 201 && pt.y > 182 && pt.x < 229 && pt.y < 220) {
					selectedroom = "설계실05";
				}
				if (pt.x > 231 && pt.y > 181 && pt.x < 198 && pt.y < 221) {
					selectedroom = "설계실06";
				}
			}
			else if(target.getName().equals("floor1")) {
				if(pt.x>141&&pt.y>98&&pt.x<249&&pt.y<140) {
					selectedroom = "목공장비실";
				}
				if(pt.x>140&&pt.y>141&&pt.x<250&&pt.y<180) {
					selectedroom = "금속장비실";
				}
				if(pt.x>174&&pt.y>210&&pt.x<254&&pt.y<258) {
					selectedroom = "3d프린트실";
				}
				if(pt.x>257&&pt.y>213&&pt.x<366&&pt.y<294) {
					selectedroom = "전기전자";
				}
				if(pt.x>488&&pt.y>219&&pt.x<630&&pt.y<296) {
					selectedroom = "VR실";
				}
						
			}
			m_selectedroom = selectedroom;
			
			//초기값에 따라 갈리는데 길이정보가 0인거를 구분 
			if(m_selectedroom.length()>0) {
				//"성공"
				((infoPanel)roomPanel).updatelist(m_selectedroom);
			}
			
		}
	}

	void initMapCard() {
		mapCard = new JPanel(new GridLayout(2, 1));
		JPanel mapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mapPanel.setBackground(Color.WHITE);
		roomPanel = new JPanel(new BorderLayout());
		try {
			BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
			// Image dimg = img1.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
			JLabel floor1 = new JLabel(new ImageIcon(img1)); //
			floor1.setSize(img1.getWidth(), img1.getHeight());
			floor1.setLocation(10, 0);
			floor1.setName("floor1");
			floor1.addMouseListener(new MyMAdapter());
			mapPanel.add(floor1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		pt = new Point(0, 0);
		pointLabel = new JLabel(pt.x + "," + pt.y);
		pointLabel.setSize(80, 40);
		//mapPanel.add(pointLabel);
		pointLabel2 = new JLabel(m_selectedroom);
		pointLabel2.setSize(90,30);
		//mapPanel.add(pointLabel2);
		
		try {
			BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
			// Image dimg = img2.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
			JLabel floor2 = new JLabel(new ImageIcon(img2));
			
			floor2.setSize(img2.getWidth(), img2.getHeight());
			floor2.setLocation(620, 0);
			floor2.setName("floor2");
			floor2.addMouseListener(new MyMAdapter());
			mapPanel.add(floor2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		roomPanel = new infoPanel();
		// roomPanel.setSize(mapCard.getWidth(),mapCard.getHeight()/2-50);

		tabbedPane.setBackground(Color.BLUE);

		mapCard.add(mapPanel);
		mapCard.add(roomPanel);
		// mapCard.addMouseListener(new MyMAdapter());
		mapCard.setName("mapCard");
	}


	void initTimeCard() {
		timeCard = new JPanel(new GridLayout(3, 1));
		calendar cal = new calendar();
		ciganpyo cig = new ciganpyo(cal);

//		timeCard.add(cal.getcal1());
//		timeCard.add(cal.getcal2());
//		timeCard.add(cal.getcal3());

		timeCard.add(cig.get_schedule());
		timeCard.setName("timeCard");

		//************************* card 3 시작
		

		JPanel card3_1 = new JPanel(new GridLayout(1,2));
		JPanel card3_2 = new JPanel(new BorderLayout());
		JPanel card3_3 = new JPanel(new GridLayout(2,1));

		card3_1.add(cal.getcal3());
		card3_1.add(cal.getcal2());
		card3_2.add(cig.get_schedule(), BorderLayout.CENTER);		
		
		timeCard.add(card3_1);
		timeCard.add(card3_2);
		
		

	}

	void initListCard() {
		userlistPart = new JPanel(new GridLayout(2, 1));
		JPanel card4_1 = new JPanel(null);

		card4_1.setSize(screenWidth/7,screenHeight/2); 
		// 카드 4안에 명수 선택할 라벨 하나 밑에 학생정보 입력할 라벨을 gridlayout 으로 2대1로 구분
		
		JLabel label2 = new JLabel("추가 학생 정보 입력");
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
		
		userlistPart.add(card4_1);
		userlistPart.add(card4_2);
		timeCard.add(userlistPart);
		

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				studentInfo std_info = null;
				if (e.getSource() == slider) {
					userlistPart.removeAll();
					card4_2.removeAll();
					std_info = new studentInfo();
					// std_info.get_stdinfo().removeAll();
					std_info.setNum(slider.getValue());
					
					id_textField = std_info.get_textsid();
					name_textField = std_info.get_textName();
					card4_2.add(std_info.get_stdinfo());

					userlistPart.add(card4_1);
					userlistPart.add(card4_2);
					timeCard.add(userlistPart);
				}
			}

		});

		userlistPart.setName("userCard");
		
	}

	void initReservePanel() {
		reservePanel = new JPanel(card);

		// initialize Map Card
		initMapCard();

		// initialize Time Card
		initTimeCard();

		// initialize userListCard
		initListCard();

		reservePanel.add("1", mapCard);
		reservePanel.add("3", timeCard);
		//reservePanel.add("4", userlistPart);

	}

	void initBtnPanel() {
		nextBtn = new JButton("다음");
		nextBtn.addActionListener(this);
		nextBtn.setBounds(1000, 650, 100, 20);

		prevBtn = new JButton("이전");
		prevBtn.addActionListener(this);
		prevBtn.setBounds(900, 650, 100, 20);

		btnPanel = new JPanel(new FlowLayout());
		btnPanel.add(prevBtn);
		btnPanel.add(nextBtn);
	}

	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

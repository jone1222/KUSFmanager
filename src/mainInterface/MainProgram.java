package mainInterface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import unitClass.User;
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
	
	private String m_selectedroom;
	private String sTime;
	private String eTime;
	
	private JSlider slider;

	private JTextField[] id_textField;
	private JTextField[] name_textField;

	private Database DB;

	calendar cal;
	ciganpyo cig;
	
	
	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		card = new CardLayout();
		DB = new Database();
		DB.open();

		this.sTime = "";
		this.eTime = "";
		
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
			if (getTopCard().equals("timeCard")) {
				ArrayList<User> users = new ArrayList<>();
				cig.getSelectedTime();
				for (int i = 0; i < id_textField.length; i++) {
				
					try {
						if(DB.isExistUser(id_textField[i].getText(), name_textField[i].getText())) {
							users.add(DB.findUserById(id_textField[i].getText()));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(!DB.makeReservation(users, DB.getridByName(m_selectedroom), cal.getDate(), sTime, eTime, 1)) {
					JOptionPane dialog = new JOptionPane();
					dialog.showMessageDialog(null, "예약에 실패했습니다. 희망 인원 중 예약 횟수를 초과하는 회원이 있습니다.");
				}else {
					JOptionPane dialog = new JOptionPane();
					dialog.showMessageDialog(null, "예약에 성공했습니다");
					m_selectedroom = "";
					eTime = "";
					sTime = "";
					
					tabbedPane.setSelectedIndex(2);
					card.next(reservePanel);
				}

			}
			else {
				card.next(reservePanel);
			}

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
				cig.loadData(m_selectedroom);
			}
			
		}
	}

	public void setTime(String sTime, String eTime) {
		this.sTime = sTime;
		this.eTime = eTime;
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
		cal = new calendar(timeCard);
		cig = new ciganpyo(cal,this);
		
//		timeCard.add(cal.getcal1());
//		timeCard.add(cal.getcal2());
//		timeCard.add(cal.getcal3());

		timeCard.add(cig.init_Schedule(cal.getDate()));
		timeCard.setName("timeCard");

		//************************* card 3 시작
		

		JPanel card3_1 = new JPanel(new GridLayout(1,2));
		JPanel card3_2 = new JPanel(new BorderLayout());

		JPanel cal3 = cal.getcal3();
		JPanel cal2 = cal.getcal2();
		JPanel schedule = cig.get_schedule();

		card3_1.setName("datePart");
		card3_2.setName("timePart");
		cal3.setName("Cal3");
		cal2.setName("Cal2");
		schedule.setName("schedule");
		
		card3_1.add(cal3);
		card3_1.add(cal2);
		card3_2.add(schedule, BorderLayout.CENTER);		
		
		timeCard.add(card3_1);
		timeCard.add(card3_2);
		
		Component[] comps = card3_1.getComponents();
		for(int i = 0 ; i < comps.length; i++) {
			System.out.println(comps[i].getName());
		}
		
		//timeCard.addListener

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

		id_textField = std_info.get_textsid();
		name_textField = std_info.get_textName();
		
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

	public String getSelectedRoom() {
		return this.m_selectedroom;
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

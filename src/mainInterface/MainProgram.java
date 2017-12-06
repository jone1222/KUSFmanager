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

	private JPanel mapCard, timeCard, userlistCard;

	private JPanel reservePanel, btnPanel;

	private loginPage loginpage;
	
	private Point pt;
	private JLabel pointLabel;
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
			if(getTopCard().equals("userCard")) {
				for(int i = 0 ; i < id_textField.length; i++) {
					try {
						System.out.println(DB.isExistUser(id_textField[i].getText(),name_textField[i].getText()));
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
		for(Component comp : reservePanel.getComponents()) {
			if(comp.isVisible()== true) {
				Card = (JPanel)comp;
			}
		}
		return Card.getName();
	}
	public class roomPanel extends JPanel {

		public roomPanel(String imgSrc) throws IOException {
			BufferedImage img = ImageIO.read(new File(imgSrc));
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setSize(img.getWidth(), img.getHeight());
			//imgLabel.setLocation(0, 0);
			this.setSize(img.getWidth(), img.getHeight());
			this.setVisible(true);
		}
	}

	public class MyMAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			pt = ((Component)e.getSource()).getMousePosition();
			if(pt != null)
				pointLabel.setText(pt.x + "," + pt.y);
		}
	}

	void initMapCard() {
		mapCard = new JPanel(new GridLayout(2,1));
		JPanel mapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mapPanel.setBackground(Color.WHITE);
		JPanel roomPanel = new JPanel(new BorderLayout());
		try {
			BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
			//Image dimg = img1.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
			JLabel floor1 = new JLabel(new ImageIcon(img1)); //
			floor1.setSize(img1.getWidth(), img1.getHeight());
			floor1.setLocation(10, 0);
			floor1.addMouseListener(new MyMAdapter());
			mapPanel.add(floor1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
			//Image dimg = img2.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
			JLabel floor2 = new JLabel(new ImageIcon(img2));
			floor2.setSize(img2.getWidth(), img2.getHeight());
			floor2.setLocation(620, 0);
			floor2.addMouseListener(new MyMAdapter());
			mapPanel.add(floor2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		pt = new Point(0, 0);
		pointLabel = new JLabel(pt.x + "," + pt.y);
		pointLabel.setSize(80, 40);
		mapPanel.add(pointLabel);
		
		
		roomPanel = new infoPanel();
		//roomPanel.setSize(mapCard.getWidth(),mapCard.getHeight()/2-50);
		
		roomPanel.setBackground(Color.BLUE);
		
		mapCard.add(mapPanel);
		mapCard.add(roomPanel);
		//mapCard.addMouseListener(new MyMAdapter());
		mapCard.setName("mapCard");
	}

	void initTimeCard() {
		timeCard = new JPanel(new GridLayout(2, 1));
		calendar cal = new calendar();
		ciganpyo cig = new ciganpyo();
		

		timeCard.add(cal.getcal1());
		timeCard.add(cal.getcal2());
		timeCard.add(cal.getcal3());
		
		timeCard.add(cig.get_schedule());
		timeCard.setName("timeCard");
	}

	void initListCard() {
		userlistCard = new JPanel(new GridLayout(2, 1));
		JPanel card4_1 = new JPanel(null);

		// 디버그용
		card4_1.setBackground(Color.CYAN);

		JLabel sliderLabel = new JLabel("인원 수를 선택해 주세요");
		sliderLabel.setSize(300, 40);
		sliderLabel.setLocation(550, 10);

		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMaximum(6);
		slider.setMinimum(1);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(150, 50, 900, 70);

		card4_1.add(sliderLabel);
		card4_1.add(slider);

		JPanel card4_2 = new JPanel(new BorderLayout());
		studentInfo std_info = new studentInfo();
		std_info.makePanel();
		id_textField = std_info.get_textsid();
		name_textField = std_info.get_textName();
		
		card4_2.removeAll();
		card4_2.setBackground(Color.GREEN);

		JLabel inputLabel = new JLabel("학번과 이름을 입력해주세요");
		inputLabel.setSize(300, 40);
		card4_2.add(inputLabel, BorderLayout.NORTH);
		card4_2.add(std_info.get_stdinfo(), BorderLayout.CENTER);

		userlistCard.add(card4_1);
		userlistCard.add(card4_2);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				studentInfo std_info = null;
				if (e.getSource() == slider) {
					card4_2.removeAll();
					std_info = new studentInfo();
					// std_info.get_stdinfo().removeAll();
					std_info.setNum(slider.getValue());
					id_textField = std_info.get_textsid();
					name_textField = std_info.get_textName();
					card4_2.add(std_info.get_stdinfo());
					userlistCard.add(card4_2);
				}
			}

		});
		
		userlistCard.setName("userCard");
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
		reservePanel.add("4", userlistCard);

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

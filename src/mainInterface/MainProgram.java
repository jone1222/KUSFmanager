package mainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import unitDatabase.Database;

public class MainProgram extends JFrame implements ActionListener {
	int screenHeight;
	int screenWidth;

	private JButton nextBtn, prevBtn;

	private JFrame frm;

	private JTabbedPane tabbedPane;
	private JPanel mainPanel, userPanel;

	private CardLayout card;

	private JPanel mapCard, roomCard, timeCard, userlistCard;

	private JPanel reservePanel, btnPanel;

	private Point pt;
	private JLabel pointLabel;
	private JSlider slider;

	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		card = new CardLayout();


		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		userPanel = new JPanel();
		mainPanel = new JPanel(new BorderLayout());
		
		initReservePanel();
		
		initBtnPanel();
		
		mainPanel.add(reservePanel, BorderLayout.CENTER);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		tabbedPane.add("reserve", mainPanel);
		tabbedPane.add("checkReserve", userPanel);

		c.add(tabbedPane);

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
			card.next(reservePanel);
		} else if (e.getSource() == prevBtn) {
			card.previous(reservePanel);
		}
	}

	public class roomPanel extends JPanel {

		public roomPanel(String imgSrc) throws IOException {
			BufferedImage img = ImageIO.read(new File(imgSrc));
			JLabel imgLabel = new JLabel(new ImageIcon(img));
			imgLabel.setSize(img.getWidth(), img.getHeight());
			imgLabel.setLocation(0, 0);
			this.setSize(img.getWidth(), img.getHeight());
			this.setVisible(true);
		}
	}

	public class MyMAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			pt = e.getPoint();
			pointLabel.setText(pt.x + "," + pt.y);
		}
	}

	void initMapCard() {
		mapCard = new JPanel(null);
		try {
			BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
			JLabel floor1 = new JLabel(new ImageIcon(img1)); //
			floor1.setSize(600, 600);
			floor1.setLocation(10, 0);
			mapCard.add(floor1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
			JLabel floor2 = new JLabel(new ImageIcon(img2));
			floor2.setSize(600, 600);
			floor2.setLocation(620, 0);
			mapCard.add(floor2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		pt = new Point(0, 0);
		pointLabel = new JLabel(pt.x + "," + pt.y);
		pointLabel.setSize(80, 40);
		mapCard.add(pointLabel);
		mapCard.addMouseListener(new MyMAdapter());

	}

	void initRoomCard() {
		roomCard = new JPanel();
		roomCard.setLayout(new BorderLayout());
	}

	void initTimeCard() {
		timeCard = new JPanel(new GridLayout(2, 1));
		calendar cal = new calendar();
		ciganpyo cig = new ciganpyo();
		

		timeCard.add(cal.getcal1());
		timeCard.add(cal.getcal2());
		timeCard.add(cal.getcal3());
		
		timeCard.add(cig.get_schedule());
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

					card4_2.add(std_info.get_stdinfo());
					userlistCard.add(card4_2);
				}
			}

		});
	}

	void initReservePanel() {
		reservePanel = new JPanel(card);

		// initialize Map Card
		initMapCard();

		// initialize Room Card
		initRoomCard();

		// initialize Time Card
		initTimeCard();

		// initialize userListCard
		initListCard();

		reservePanel.add("1", mapCard);
		reservePanel.add("2", roomCard);
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

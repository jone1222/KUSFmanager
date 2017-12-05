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
	private ImageIcon bgImage; // 로그인 화면 이미지
	public JLabel bglabel;

	public MainProgram() throws IOException {
		frm = new JFrame();
		Container c = frm.getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel2 = new JPanel();

		btn1 = new JButton("다음");
		btn1.addActionListener(this);
		btn1.setBounds(1000, 650, 100, 20); // 이건 맨밑에 있는 항상 flow layout 있는거

		btn2 = new JButton("이전"); // 이것도
		btn2.addActionListener(this);
		btn2.setBounds(900, 650, 100, 20);

		card = new CardLayout();
		panel1 = new JPanel(new BorderLayout());
		panelCard = new JPanel(card);
		panelFlow = new JPanel(new FlowLayout());
		
		panel1.add(panelCard, BorderLayout.CENTER); // panelcard가에 card1, card2, card3, card4 가 있고 
		// card 4개를 cardlayout으로 하나씩 교환해서 보여준다
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
		
		BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg")); // card1 에 이미지를 보여준다
		JLabel floor1 = new JLabel(new ImageIcon(img1)); // 
		floor1.setSize(600, 600);
		floor1.setLocation(10, 0);
		
		BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg")); // card1  에 이미지를 보여준다
		JLabel floor2 = new JLabel(new ImageIcon(img2));
		floor2.setSize(600, 600);
		floor2.setLocation(620, 0);
		
		pt = new Point(0, 0);
		label1 = new JLabel(pt.x + "," + pt.y);
		label1.setSize(80, 40);		
		card1.add(label1);
		card1.add(floor1);
		card1.add(floor2);
		panelFlow.add(btn2);// 다음으로 눌러서 예약화면 넘어가기
		panelFlow.add(btn1);				
		card1.addMouseListener(new MyMAdapter());
		

       // ************************* card 2 시작

		
		card2 = new JPanel();
		card2.setLayout(new BorderLayout());	
		
		
		
		//************************* card 3 시작
		
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
		
		//*********************** card 4 시작		-> 없앴오 card3이랑 4합침

		//card4 = new JPanel(new GridLayout(2,1));
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
					card3_3.add(card4_1); // 슬라이더만
					card3_3.add(card4_2); // 정보 입력받는 곳
					card3.add(card3_3);   // 슬라이더랑 입력받는 곳 전체
				}
			}

		});
		
		panelCard.add("1", card1);
		panelCard.add("2", card2);
		panelCard.add("3", card3);
		//panelCard.add("4", card4);
		
		loginPage login = new loginPage();
		
		tabbedPane.add("로그인", login.makePanel());
		tabbedPane.add("예약하기", panel1);
		
		checkReserve cr = new checkReserve();
		tabbedPane.add("예약확인", cr.makePanel());

		c.add(tabbedPane);
		frm.setTitle("예약");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize(); // 스크린의 폭과 높이 정보를 가짐
		screenHeight = screenSize.height-50;
		screenWidth = screenSize.width;
		frm.setSize(screenWidth, screenHeight); // 사이즈 지정
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
			label1.setText(pt.x + "," + pt.y);
		}
	}

	public static void main(String[] args) throws SQLException, IOException {
		new MainProgram();
	}

}

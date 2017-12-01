package mainInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import unitDatabase.Database;
import unitClass.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MainProgram extends JFrame implements ActionListener {
	int screenHeight;
	int screenWidth;

	private JButton nextBtn, prevBtn;
	private JFrame frm;
	private JTabbedPane tabbedPane;
	private JPanel mainPanel, userPanel;
	private JPanel reservationPanel,buttonPanel;
	private CardLayout card;
	private JPanel mapCard, roomInfoCard, timeCard, userlistCard;
	private JPanel calan;
	private Point pt;
	private JLabel label1;
	private JSlider slider;

	
	public MainProgram() throws IOException {
		frm = new JFrame();
		frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Container c = frm.getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		userPanel = new JPanel();

		nextBtn = new JButton("다음");
		nextBtn.addActionListener(this);
		
		prevBtn = new JButton("이전");
		prevBtn.addActionListener(this);

		card = new CardLayout();
		mainPanel = new JPanel(new BorderLayout());
		
		//divide mainPanel into two
		reservationPanel = new JPanel(card);
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(prevBtn);
		buttonPanel.add(nextBtn);
		
		mapCard = new JPanel();
		mapCard.setLayout(null);

		BufferedImage img1 = ImageIO.read(new File("img\\floor1.jpg"));
		JLabel floor1 = new JLabel(new ImageIcon(img1));
		floor1.setSize(600, 600);
		floor1.setLocation(10, 0);
		BufferedImage img2 = ImageIO.read(new File("img\\floor2.jpg"));
		JLabel floor2 = new JLabel(new ImageIcon(img2));
		floor2.setSize(600, 600);
		floor2.setLocation(620, 0);

		calan = new JPanel();

		pt = new Point(0, 0);
		label1 = new JLabel(pt.x + "," + pt.y);
		label1.setSize(80, 40);

		mapCard.add(floor1);
		mapCard.add(floor2);
		mapCard.add(label1);
		mapCard.addMouseListener(new MyMAdapter());

		roomInfoCard = new JPanel();
		roomInfoCard.setLayout(null);
		roomInfoCard.add(new roomPanel("img\\3d.png"));

		timeCard = new JPanel(new GridLayout(1, 2));

		timeCard.setLayout(new GridLayout(4, 2));
		calendar cal = new calendar();
		ciganpyo cig = new ciganpyo();

		timeCard.add(cal.getcal1());
		timeCard.add(cal.getcal2());
		timeCard.add(cal.getcal3());
		timeCard.add(cig.get_schedule());

		userlistCard = new JPanel();
		userlistCard.setLayout(new BorderLayout());
		JPanel card4_1 = new JPanel(new GridLayout(2, 2));
		card4_1.setSize(50, 50);
		JPanel card4_2 = new JPanel(new FlowLayout());
		card4_2.setSize(50, 50);

		JLabel label2 = new JLabel("명 수 선택");
		label2.setSize(200, 40);
		label2.setLocation(10, 0);

		JLabel label3 = new JLabel("학생 정보 입력");
		label3.setSize(150, 40);
		label3.setLocation(10, 100);

		card4_1.add(label2);

		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMaximum(10);
		slider.setMinimum(1);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

		slider.setBounds(100, 0, 300, 70);
		card4_1.add(slider);
		card4_1.add(label3);
		JPanel card5 = new JPanel(new GridLayout(2, 2));
		card5.setSize(50, 50);

		studentInfo std_info = new studentInfo();
		std_info.makePanel();
		card5.removeAll();
		card5.add(std_info.get_stdinfo());
		card4_1.add(card5);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				studentInfo std_info = null;
				if (e.getSource() == slider) {
					card5.removeAll();
					std_info = new studentInfo();
					// std_info.get_stdinfo().removeAll();
					std_info.setNum(slider.getValue());

					card5.add(std_info.get_stdinfo());
					card4_1.add(card5);
				}
			}

		});

		userlistCard.add(card4_1, BorderLayout.CENTER);
		userlistCard.add(card4_2, BorderLayout.SOUTH);

		reservationPanel.add("1", mapCard);
		reservationPanel.add("2", roomInfoCard);
		reservationPanel.add("3", timeCard);
		reservationPanel.add("4", userlistCard);

		mainPanel.add(reservationPanel,BorderLayout.CENTER);
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		tabbedPane.add("reserve", mainPanel);
		tabbedPane.add("checkReserve", userPanel);

		c.add(tabbedPane);
		frm.setTitle("예약");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frm.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextBtn) {
			card.next(reservationPanel);
		} else if (e.getSource() == prevBtn) {
			card.previous(reservationPanel);
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

	public static void main(String[] args) throws SQLException, ParseException, IOException {
		new MainProgram();
	}

}

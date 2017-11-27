package mainInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import unitDatabase.Database;

public class MainProgram extends JFrame implements ActionListener {
	private JButton btn1,btn2,btn3,btn4,btn5,btn6,btn7;
	private JFrame frm;
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel label1;
	private JLabel label2;
	CardLayout card;
	JPanel card1, card2, card3,card4;
	
	public MainProgram() {
		frm = new JFrame();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
//		panel1 = new JPanel(); // 이거 대신 카드 레이아웃을 사용
		panel2 = new JPanel();
		
		label1 = new JLabel("예약하기");
		label2 = new JLabel("예약확인");
		btn1 = new JButton("다음화면"); 		
		btn1.addActionListener(this);
		btn2 = new JButton("전화면"); 		
		btn2.addActionListener(this);
		btn3 = new JButton("다음화면"); 		
		btn3.addActionListener(this);
		btn4 = new JButton("전화면"); 		
		btn4.addActionListener(this);
		btn5 = new JButton("다음화면"); 		
		btn5.addActionListener(this);
		btn6 = new JButton("전화면"); 		
		btn6.addActionListener(this);
		btn7 = new JButton("다음화면"); 		
		btn7.addActionListener(this);
		
		
		card = new CardLayout();
		
		panel1 = new JPanel(card);
		card1 = new JPanel();
		card2 = new JPanel();
		card3 = new JPanel();
		card4 = new JPanel();
		
		panel1.add("1",card1);
		panel1.add("2",card2);
		panel1.add("3",card3);
		panel1.add("4",card4);
//		panel1.add(label1);
		panel2.add(label2);
//		panel1.add(btn1);
		card1.add(btn1);// 다음으로 눌러서 예약화면 넘어가기
		
		card2.add(btn2); // 두번쨰  화면에서 버튼 되돌아가기
		card2.add(btn3);// 두번쨰 화면에서 버튼 다음화면
		
		card3.add(btn4);
		card3.add(btn5);
		
		
		tabbedPane.add("reserve", panel1);
		tabbedPane.add("checkReserve", panel2);
		
		frm.add(tabbedPane);
		frm.setSize(500, 500);
		frm.setTitle("예약");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			card.show(panel1, "2");
			//panel3 = new JPanel(); 여기 공부중입니당
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

	public static void main(String[] args) throws SQLException {
		Database DB = new Database();
		DB.open();

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		Date date;
		try {
			date = sdf.parse("18:00");
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

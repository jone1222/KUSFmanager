package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import unitDatabase.Database;

public class loginPage extends JPanel {
	private JButton loginSubmit;
	private JPanel loginPanel, subPanel1, subPanel2, subPanel3;
	private JLabel id, pw, label;
	private JTextField inputID, inputPW;

	private String login_id, login_pw;

	private ImageIcon bgImage; // 로그인 화면 이미지
	private JScrollPane scrollPane;
	
	private boolean isitLogin = false;
	
	private MainProgram m;
	
	public loginPage() throws IOException {
		loginPanel = new JPanel(new BorderLayout(100,100)) {
			public void paintComponent(Graphics g) {
				g.drawImage(bgImage.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		scrollPane = new JScrollPane(loginPanel);
		
		loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 420, 130, 420)); // 패널 내 상좌하우 순으로 여백
		
		subPanel1 = new JPanel(new FlowLayout());
		subPanel1.setOpaque(false);
		subPanel2 = new JPanel(new GridLayout(2,1));
		subPanel2.setOpaque(false);
		subPanel3 = new JPanel(new FlowLayout());
		subPanel3.setOpaque(false);
		
		bgImage = new ImageIcon("img/konkukuniv.jpg");
		
		BufferedImage img1 = ImageIO.read(new File("img\\kuMark.jpg")); // card1 에 이미지를 보여준다
		JLabel floor1 = new JLabel(new ImageIcon(img1)); 
		floor1.setSize(600, 600);
		floor1.setLocation(10, 0);
		
		//label = new JLabel("로그인 페이지");
		id = new JLabel("I D : ");
		pw = new JLabel("P W : ");
		
		inputID = new JTextField();
		inputID.setText("I  D");
		inputPW = new JTextField();

		inputID.setForeground(Color.GRAY);
		inputID.setText("ID를 입력해주세요");
		inputPW.setForeground(Color.GRAY);
		inputPW.setText("비밀번호를 입력해주세요");
		
		inputID.setHorizontalAlignment(SwingConstants.CENTER);
		inputPW.setHorizontalAlignment(SwingConstants.CENTER);		
		
		inputID.addFocusListener(new PlaceHolder());
		inputPW.addFocusListener(new PlaceHolder());
			
		loginSubmit = new JButton(new ImageIcon("img/loginImg2.png"));
		ImageIcon rolloverIcon = new ImageIcon("img/loginImg.png");
		

		loginSubmit.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Database db = new Database();
				db.open();
				try {
					if(db.checkOnLogin(inputID.getText(), inputPW.getText())) {
						login_id = inputID.getText(); login_pw = inputPW.getText();
						isitLogin = true;
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								m.tabbedPane.setEnabledAt(0, false);
								m.tabbedPane.setEnabledAt(1, true);
								m.tabbedPane.setEnabledAt(2, true);
								m.tabbedPane.setSelectedIndex(1);
								m.loginUserID = login_id;
								m.loginUserPW = login_pw;
								System.out.println(m.loginUserID);
								System.out.println(m.loginUserPW);
								((ReservePanel)m.getUserPanel()).updateTable(m.loginUserID);
							}
						});
					}else {
						JOptionPane dialog = new JOptionPane();
						dialog.showMessageDialog(null, "아이디 또는 비밀번호가 잘못되었습니다.");
					}
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				db.close();
				
			}
			
		});
		
		loginSubmit.setRolloverIcon(rolloverIcon);
		loginSubmit.setBorderPainted(false); 
		loginSubmit.setFocusPainted(false); 
		loginSubmit.setContentAreaFilled(false);
		
		subPanel1.add(floor1);
		//subPanel2.add(id); 
		subPanel2.add(inputID);
		//subPanel2.add(pw); 
		subPanel2.add(inputPW);
		subPanel3.add(loginSubmit);
		
		loginPanel.add(subPanel1, BorderLayout.NORTH);
		loginPanel.add(subPanel2, BorderLayout.CENTER);
		loginPanel.add(subPanel3, BorderLayout.SOUTH);
		//loginPanel.setBackground(Color.WHITE);
	}
	
	public void getMP(MainProgram m) {
		this.m = m;
	}
	
	public JScrollPane makePanel() {
		return scrollPane;
	}
	public String[] getLoginUser() {
		return new String[] {this.login_id,this.login_pw};
	}
	public boolean getisitLogin() {
		return isitLogin;
	}
	
	class PlaceHolder implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == inputID) {
				if(inputID.getText().equals("ID를 입력해주세요")) {
					inputID.setText("");
					inputID.setForeground(Color.BLACK);
				}
			}
			else if(arg0.getSource() == inputPW) {
				if(inputPW.getText().equals("비밀번호를 입력해주세요")) {
					inputPW.setText("");
					inputPW.setForeground(Color.BLACK);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == inputID) {
				if(inputID.getText().isEmpty()) {
					inputID.setForeground(Color.GRAY);
					inputID.setText("ID를 입력해주세요");
				}
			}
			else if(arg0.getSource() == inputPW) {
				if(inputPW.getText().isEmpty()) {
					inputPW.setForeground(Color.GRAY);
					inputPW.setText("비밀번호를 입력해주세요");
				}
			}
		}
	}
}

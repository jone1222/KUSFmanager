package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import unitDatabase.Database;

public class loginPage extends JPanel {
	private JButton loginSubmit;
	private JPanel loginPanel, subPanel1, subPanel2, subPanel3;
	private JLabel id, pw, label;
	private JTextField inputID, inputPW;

	private String login_id, login_pw;
	
	
	public loginPage() {
		loginPanel = new JPanel(new BorderLayout(150,150));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100)); // 패널 내 상좌하우 순으로 여백
		subPanel1 = new JPanel(new FlowLayout());
		subPanel2 = new JPanel(new GridLayout(2,2));
		subPanel3 = new JPanel(new FlowLayout());
		
		label = new JLabel("로그인 페이지");
		id = new JLabel("I D : ");
		pw = new JLabel("P W : ");
		
		inputID = new JTextField();
		inputPW = new JTextField();
		
		inputID.setForeground(Color.GRAY);
		inputID.setText("ID를 입력해주세요");
		inputPW.setForeground(Color.GRAY);
		inputPW.setText("비밀번호를 입력해주세요");
		
		
		inputID.addFocusListener(new PlaceHolder());
		inputPW.addFocusListener(new PlaceHolder());
		
		
		loginSubmit = new JButton("로그인");
		loginSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // 로그인이 허용되어야 탭 접속이 가능하도록
				// TODO Auto-generated method stub
				Database db = new Database();
				db.open();
				try {
					if(db.checkOnLogin(inputID.getText(), inputPW.getText())) {
						login_id = inputID.getText(); login_pw = inputPW.getText();
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
		
		subPanel1.add(label);
		subPanel2.add(id); subPanel2.add(inputID);
		subPanel2.add(pw); subPanel2.add(inputPW);
		subPanel3.add(loginSubmit);
		
		loginPanel.add(subPanel1, BorderLayout.NORTH);
		loginPanel.add(subPanel2, BorderLayout.CENTER);
		loginPanel.add(subPanel3, BorderLayout.SOUTH);
	}
	
	public JPanel makePanel() {
		return loginPanel;
	}
	public String[] getLoginUser() {
		return new String[] {this.login_id,this.login_pw};
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

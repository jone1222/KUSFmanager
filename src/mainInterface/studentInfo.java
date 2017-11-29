package mainInterface;

import java.awt.*;

import javax.swing.*;

public class studentInfo extends JPanel{
	JPanel show_stdinfo;
	//JTextField textsid, textname;
	int stdNum;
	
	public studentInfo() {
		this(1);
	}
	
	public studentInfo(int stdNum) {
		this.stdNum = stdNum;
		
		makePanel();
	}
	
	public void makePanel() {
		
		show_stdinfo = new JPanel(new GridLayout(2*stdNum, 2));
		
		JLabel label = new JLabel("이름 : "); 
		for(int i=0; i<stdNum; i++) {
			
			JTextField textname = new JTextField(10);
			show_stdinfo.add(textname);
		}
		
		JLabel label2 = new JLabel("학번 : "); 
		for(int i=0; i<stdNum; i++) {
			JTextField textsid = new JTextField(10);
			show_stdinfo.add(textsid);
		}
		
		show_stdinfo.add(label);
		show_stdinfo.add(label2);
		
		}
	
	
	public void setNum(int stdNum) {
		this.stdNum = stdNum;
	}
	
	public JPanel get_stdinfo() {
		return show_stdinfo;
	}
}

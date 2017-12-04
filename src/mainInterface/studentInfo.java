package mainInterface;

import java.awt.*;

import javax.swing.*;

public class studentInfo extends JPanel{
	private JPanel show_stdinfo;
	private int stdNum;
	private JTextField[] textsid;
	private JTextField[] textName;
	
	public studentInfo() {
		this(1);
	}
	
	public studentInfo(int stdNum) {
		this.stdNum = stdNum;
	}
	
	public void makePanel() {
		show_stdinfo = new JPanel(new GridLayout(2, 2));
		
		JLabel label = new JLabel("이름 : "); 
		show_stdinfo.add(label);
		
		
		textName = new JTextField[stdNum];
		for(int i=0; i<stdNum; i++) {
			textName[i] = new JTextField(10);
			show_stdinfo.add(textName[i]);
		}
		JLabel label2 = new JLabel("학번 : "); 
		show_stdinfo.add(label2);
		textsid = new JTextField[stdNum];
		
		for(int i=0; i<stdNum; i++) {
			textsid[i] = new JTextField(10);
			show_stdinfo.add(textsid[i]);
		}
		
		show_stdinfo.repaint();
	}
	
	public void setNum(int stdNum) {
		this.stdNum = stdNum;
		this.makePanel();
	}
	
	public JPanel get_stdinfo() {
		return show_stdinfo;
	}
	public JTextField[] get_textsid() {
		return textsid;
	}
	public JTextField[] get_textName() {
		return textName;
	}
	
}

package mainInterface;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class studentInfo extends JPanel{
	private JPanel show_stdinfo;
	private int stdNum;
	private JTextField[] textsid;
	private JTextField[] textName;
	
	private MainProgram main;
	
	public studentInfo(int stdNum,MainProgram main) {
		this.stdNum = stdNum;
		this.main = main;
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
			textsid[i].getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					main.updateIsAllText();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					main.updateIsAllText();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					main.updateIsAllText();
				}
				
			});
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

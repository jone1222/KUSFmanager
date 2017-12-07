package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import unitClass.Item;
import unitClass.Room;
import unitDatabase.Database;

public class infoPanel extends JPanel{
	
	GridBagLayout grid;
	GridBagConstraints gridCon;
	
	JPanel roominfoPanel;
	JPanel itemPanel;
	JPanel iteminfoPanel;
	
	ArrayList<Item> items;
	
	TextArea iteminfo;
	
	JList itemList;
	
	TextArea roominfo;
	
	boolean isChanging = false;
	
	public infoPanel() {
		super();
		init();
	}
	public void resetItemInfo() {
		this.iteminfo.setText("");
	}
	public void init() {
		grid = new GridBagLayout();
		gridCon = new GridBagConstraints();
		gridCon.fill = GridBagConstraints.BOTH;
		
		this.setLayout(grid);
	
		roominfoPanel = new JPanel(new BorderLayout());
		roominfo = new TextArea();
		roominfo.setEditable(false);
		Font ft = new Font("Arial", Font.BOLD, 12);
		roominfo.setFont(ft);
		roominfoPanel.add(roominfo,BorderLayout.CENTER);
		roominfoPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 2),"실습실 정보"));
		
		itemPanel = new JPanel();
		itemPanel.setSize(300,400);
		itemPanel.setBorder(new TitledBorder(new LineBorder(Color.black,2), "장비 목록"));
		
		itemList = new JList();
		itemList.setCellRenderer(new RoomItemCustomRenderer());

		itemList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		itemList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		itemList.setVisibleRowCount(1);
		//itemList.setFixedCellHeight(300);
		itemList.setBorder(new TitledBorder(new LineBorder(Color.black, 2),"장비 목록"));
		itemList.addListSelectionListener(new itemListSelectionListener());
		
		iteminfoPanel = new JPanel(new BorderLayout());
		iteminfo = new TextArea();

		iteminfo.setEditable(false);
		iteminfo.setFont(ft);
		
		iteminfoPanel.add(iteminfo,BorderLayout.CENTER);
		iteminfoPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 2),"장비 설명"));
		
		this.addGrid(grid, gridCon, roominfoPanel, 0, 0, 1, 2, 1, 0);
		this.addGrid(grid, gridCon, new JScrollPane(itemList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), 2, 0, 1, 1, 2, 2);
		this.addGrid(grid, gridCon, iteminfoPanel, 2, 1, 1, 1, 2, 1);
		
		this.setVisible(true);
	}
	
	class itemListSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub
			if(!arg0.getValueIsAdjusting() && !isChanging) {
				int index = itemList.getSelectedIndex();
				Item item = items.get(index);
				//String str = item.getDescription();
				
				iteminfo.setText(item.getDescription());
				
			}
		}
		
	}
	private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c, 
			int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty) {
      gbc.gridx = gridx;
      gbc.gridy = gridy;
      gbc.gridwidth = gridwidth;
      gbc.gridheight = gridheight;
      gbc.weightx = weightx;
      gbc.weighty = weighty;
      gbl.setConstraints(c, gbc);
      
      add(c);
	}

	public void updatelist(String roomName) {
		Database DB = new Database();
		DB.open();
		
		Room room = null; 
		try {
			isChanging = true;
			items = DB.getRoomItems(roomName);
			Object[] panels = new Object[items.size()];
			for(int i = 0 ; i < items.size(); i++) {
				Item item = items.get(i);
				BufferedImage img = ImageIO.read(new File(item.getImage()));
				Image dimg = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
				/*
				JLabel imgLabel = new JLabel(item.getiName(),new ImageIcon(dimg),JLabel.LEFT);
				*/
				JLabel imgLabel = new JLabel(new ImageIcon(dimg));
				imgLabel.setText("<html><span style='font-size:20px'>"+item.getiName()+"</span></html>");
				imgLabel.setHorizontalTextPosition(JLabel.CENTER);
				imgLabel.setVerticalTextPosition(JLabel.BOTTOM);
				
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				
				panel.add(imgLabel);
				
				panels[i] = panel;
			}
			itemList.setListData(panels);
			
			room = DB.findRoomByName(roomName);
			String desc = room.getDescription();
			roominfo.setText(desc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		DB.close();
		isChanging = false;
	}

}

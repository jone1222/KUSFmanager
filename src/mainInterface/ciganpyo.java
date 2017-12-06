package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import unitClass.Reservation;
import unitDatabase.Database;

public class ciganpyo extends JPanel {
	
	timePanel show_schedule;
	JTable table;
	
	String []a = {"09:00 ~ 10:00", "10:00 ~ 11:00", "11:00 ~ 12:00", "12:00 ~ 13:00",
			"13:00 ~ 14:00", "14:00 ~ 15:00", "15:00 ~ 16:00", "16:00 ~ 17:00"};
	String [][]b = {
			{"예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능"}};
	
	String today;
	
	calendar cal;
	
	int time;
	
	ArrayList<Integer> reserved_cols;
	ArrayList<Integer> hovered_cols;
	ArrayList<Integer> selected_cols;
	
	String selectedRoom;
	public MainProgram main;
	
	public ciganpyo(calendar cal,MainProgram main) {
		this.cal = cal;
		today = cal.getDate();
		time = cal.getUserTime();
		
		reserved_cols = new ArrayList<>();
		hovered_cols = new ArrayList<>();
		selected_cols = new ArrayList<>();
		
		this.main =main;
		this.selectedRoom = main.getSelectedRoom();
		init_Schedule(today);
	}
	public timePanel init_Schedule(String date) {
		//1. 모델과 데이터를 연결
		
		
		DefaultTableModel model = new DefaultTableModel(b,a);
		table = new JTable(model);
		table.setRowHeight(80);
		table.setName("timetable");
		
		if(selectedRoom != null) {
			loadData(this.selectedRoom,this.table);
		}
		
		// 결과적으로는 Jscrollpane 추가한다
		JScrollPane sc = new JScrollPane(table);

		/*
		for(int i = 0; i < 8; i++) { // 예약 안된 부분에 버튼 추가
			String str = (String) model.getValueAt(0, i);
			if(str.equals("예약 가능")) {
				table.getColumnModel().getColumn(i).setCellRenderer(new TableCell(time));
				table.getColumnModel().getColumn(i).setCellEditor(new TableCell(time));
			}
		}
		*/


		show_schedule = new timePanel(this,date,table);
		show_schedule.setLayout(new BorderLayout());
		show_schedule.add(sc, BorderLayout.CENTER);
		show_schedule.setVisible(true);

		for(int i = 0 ; i < table.getColumnCount(); i++) {
			TableColumn col = table.getColumnModel().getColumn(i);
			DefaultTableModel model3 = (DefaultTableModel)table.getModel();
			col.setCellRenderer(new CustomRenderer());
		}
		
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(hovered_cols.size() > 0) {
					selected_cols.clear();
					for(int i = 0 ; i < hovered_cols.size(); i++) {
						selected_cols.add(hovered_cols.get(i));
					}
					hovered_cols.clear();
					getSelectedTime();
					main.updateIsAllText();
					table.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				//super.mouseEntered(e);
				int row = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());
		        hovered_cols = new ArrayList<>();
		        if (row >= 0 && col >= 0) {
        			if(table.getValueAt(row, col) instanceof String) {
        				for(int i = col; i < col + cal.getUserTime(); i++) {
        					if(i < table.getColumnCount() && ((String)table.getValueAt(row,i)).equals("예약 가능")) {
        						hovered_cols.add(i);
        					}
        					else {
        						hovered_cols.clear();
        						break;
        					}
    					}
        			}
        			table.repaint();	
		        }
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				hovered_cols.clear();
				table.repaint();
			}
			
		});


		
		table.updateUI();
		
		return show_schedule;
	}
	
	
	public JPanel get_schedule() {
		return show_schedule;
	}
	public calendar get_cal() {
		return this.cal;
	}

	public void loadData(String room) {
		loadData(room,this.table);
	}
	public void loadData(String room,JTable table) {
		this.selectedRoom = room;
		
		this.selected_cols.clear();
		this.hovered_cols.clear();
		
		Database DB = new Database();
		DB.open();
		try {
			ArrayList<Reservation> reserve_list = DB.getReserveByDate(room, cal.getDate());
			reserved_cols.clear();
			for(Reservation resv : reserve_list) {
				int sCol = TimetoColumn(Reservation.SDF_TIME.format(resv.getsTime()));
				int eCol = TimetoColumn(Reservation.SDF_TIME.format(resv.geteTime()));
				if(sCol != -1 && eCol != -1) {
					for(int i = sCol; i < eCol; i++) {
						reserved_cols.add(i);
						table.getModel().setValueAt("예약 완료", 0, i);
					}
				}
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		DB.close();
	}
	public int TimetoColumn(String time) {
		switch(time) {
		case "09:00":
			return 0;
		case "10:00":
			return 1;
		case "11:00":
			return 2;
		case "12:00":
			return 3;
		case "13:00":
			return 4;
		case "14:00":
			return 5;
		case "15:00":
			return 6;
		case "16:00":
			return 7;
		}
		return -1;
	}
	
	public String ColumntoTime(int col) {
		switch(col) {
		case 0:
			return "09:00";
		case 1:
			return "10:00";
		case 2:
			return "11:00";
		case 3:
			return "12:00";
		case 4:
			return "13:00";
		case 5:
			return "14:00";
		case 6:
			return "15:00";
		case 7:
			return "16:00";
		case 8:
			return "17:00";
		}
		return "";
	}
	public void getSelectedTime() {
		if(this.selected_cols.size() > 0) {
			String sTime = ColumntoTime(selected_cols.get(0));
		
			String eTime = ColumntoTime(selected_cols.get(selected_cols.size() - 1 ) + 1);
			main.setTime(sTime, eTime);
		}
	}
	
	public class CustomRenderer extends DefaultTableCellRenderer
	{
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	       
	        if(value instanceof String) {
	        	if(((String)value).equals("예약 가능")) {
	        		setBackground(new Color(200,200,200));
	        		for(int i = 0 ; i < selected_cols.size(); i++) {
	        			if(column == selected_cols.get(i)) {
	        				setBackground(Color.GRAY);
	        				setForeground(Color.BLACK);
	        			}
	        		}
	        		for(int i = 0 ; i < hovered_cols.size(); i++) {
	        			if(column == hovered_cols.get(i)) {
	        				setBackground(Color.YELLOW);
	        			}
	        		}
	        	}
	        	else {
	        		setBackground(Color.BLACK);
	        		setForeground(Color.white);
	        	}
	        }
	        this.setHorizontalAlignment(SwingConstants.CENTER);
	        return c;
	    }
	}
	
	class timePanel extends JPanel{
		
		ciganpyo cig;
		String today;
		JTable table;
		
		public timePanel(ciganpyo cig,String date,JTable table) {
			this.cig = cig;
			today = date;
			this.table = table;
		}
		
		public void updateTimeTable(int userTime, String[][] data) {
			data = new String[][] {{"예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능","예약 가능"}};
			DefaultTableModel tModel = new DefaultTableModel(data,a);
			table.setModel(tModel);
			
			if(cig.main.getSelectedRoom() != null) {
				cig.selectedRoom = cig.main.getSelectedRoom();
				cig.loadData(cig.selectedRoom,table);
			}
			
			for(int i = 0 ; i < table.getColumnCount(); i++) {
				TableColumn col = table.getColumnModel().getColumn(i);
				DefaultTableModel model3 = (DefaultTableModel)table.getModel();
				col.setCellRenderer(new CustomRenderer());
			}
			
			/*
			for(int i = 0; i < 8; i++) { // 예약 안된 부분에 버튼 추가
				String str = (String) tModel.getValueAt(0, i);
				if(str.equals("예약 가능")) {
					table.getColumnModel().getColumn(i).setCellRenderer(new TableCell(userTime));
					table.getColumnModel().getColumn(i).setCellEditor(new TableCell(userTime));
				}
			}
			*/
			table.setColumnSelectionAllowed(true);
			table.setRowSelectionAllowed(true);

			
			table.updateUI();
		}
		
	}
}


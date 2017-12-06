package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import unitClass.Reservation;
import unitDatabase.Database;

public class ReservePanel extends JPanel{
	
	BorderLayout Border;
	
	JPanel userInfo, reserveInfo, reserveDetail;
	
	JTextField sidField, nameField;
	
	String sid,name;
	
	JTextArea detailText;
	
	JTable reserveTable;
	DefaultTableModel tableModel;
	
	ArrayList<Reservation> reserve_list;
	
	
	public ReservePanel() {
		super();
		init();
	}
	void init() {
		Border = new BorderLayout(0,10);
		
		this.setLayout(Border);
		
		initUserInfo();
		
		initReserveInfo();
		
		initReserveDetail();
		
		this.add(userInfo, BorderLayout.NORTH);
		this.add(reserveInfo, BorderLayout.CENTER);
		this.add(reserveDetail, BorderLayout.SOUTH);
	}
	void initUserInfo() {
		userInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		userInfo.setBackground(Color.RED);
		
		JLabel idLabel = new JLabel("아이디 : ");
		sidField = new JTextField(15);
		sidField.setText(sid);
		sidField.setEnabled(false);
		
		JLabel nameLabel = new JLabel("학번 : ");
		nameField = new JTextField(15);
		nameField.setText(name);
		nameField.setEnabled(false);
		
		userInfo.add(idLabel);
		userInfo.add(sidField);
		userInfo.add(nameLabel);
		userInfo.add(nameField);
	}
	void initReserveInfo() {
		reserveInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		reserveInfo.setBackground(Color.GREEN);
		
		tableModel = new DefaultTableModel(new String[] { "대여 공간명", "예약 날짜", "시작 시간", "종료 시간" },0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		reserveTable = new JTable(tableModel);
		reserveTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		reserveTable.setBackground(Color.WHITE);
		reserveTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		TableColumnModel columnModel = reserveTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(500);
		columnModel.getColumn(1).setPreferredWidth(1000);
		columnModel.getColumn(2).setPreferredWidth(1000);
		columnModel.getColumn(3).setPreferredWidth(1000);
		JScrollPane scrollTable = new JScrollPane(reserveTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollTable.setSize(1000, 300);
		reserveInfo.add(scrollTable);
	}
	void initReserveDetail() {
		reserveDetail = new JPanel();
		reserveDetail.setBackground(Color.BLUE);
		
		detailText = new JTextArea();
		
		reserveDetail.add(detailText);
		reserveDetail.setBorder(new TitledBorder(new LineBorder(Color.BLACK,2),"예약 정보"));
	}
	void updateTable(String sid) {
		
		reserve_list = new ArrayList<>();
		
		Database DB = new Database();
		DB.open();

		try {
			reserve_list = DB.getReserveByUser(DB.findUserById(sid));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0 ; i < reserve_list.size(); i++) {
			Reservation target = reserve_list.get(i);
			tableModel.addRow(new String[] { target.getRoom().getrName(), Reservation.SDF_DATE.format(target.getDate()), Reservation.SDF_TIME.format(target.getsTime()), Reservation.SDF_TIME.format(target.geteTime()) });
		}
		
		DB.close();
	}
}

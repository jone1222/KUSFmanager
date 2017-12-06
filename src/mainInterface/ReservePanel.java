package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import unitClass.Reservation;
import unitDatabase.Database;

public class ReservePanel extends JPanel{
	
	BorderLayout Border;
	
	JPanel userInfo, reserveInfo, reserveDetail, reserveTables;
	
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
		this.setBorder(BorderFactory.createEmptyBorder(110, 150, 180, 150)); // 패널 내 상좌하우 순으로 여백
		
		initUserInfo();
		
		initReserveInfo();
		
		this.add(userInfo, BorderLayout.NORTH);
		this.add(reserveInfo, BorderLayout.CENTER);
		//this.add(reserveDetail, BorderLayout.SOUTH);
	}
	void initUserInfo() {
		userInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//userInfo.setBackground(Color.RED);
		
		JLabel idLabel = new JLabel("학번 : ");
		sidField = new JTextField(15);
		sidField.setText(sid);
		sidField.setEnabled(false);
		
		JLabel nameLabel = new JLabel("이름 : ");
		nameField = new JTextField(15);
		nameField.setText(name);
		nameField.setEnabled(false);
		
		userInfo.add(idLabel);
		userInfo.add(sidField);
		userInfo.add(nameLabel);
		userInfo.add(nameField);
	}
	void initReserveInfo() {
		reserveInfo = new JPanel(new GridLayout(1,2));
		//reserveInfo.setBackground(Color.GREEN);
		
		reserveTables = new JPanel(new GridLayout(2,1));
		
		reserveDetail = new JPanel(new BorderLayout());
		//reserveDetail.setBackground(Color.BLUE);
		detailText = new JTextArea();
		reserveDetail.add(detailText);
		
		
		tableModel = new DefaultTableModel(new String[] { "대여 공간명", "예약 날짜", "시작 시간", "종료 시간" },0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		reserveTable = new JTable(tableModel);
		reserveTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		reserveTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		reserveTable.setRowHeight(52); reserveTable.setGridColor(Color.BLACK);
		reserveTable.addMouseListener(new MyMouseListener());
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
	
		TableColumnModel columnModel = reserveTable.getColumnModel();
		columnModel.getColumn(0).setCellRenderer(dtcr);
		columnModel.getColumn(1).setCellRenderer(dtcr);
		columnModel.getColumn(2).setCellRenderer(dtcr);
		columnModel.getColumn(3).setCellRenderer(dtcr);
		
		JPanel reserveLabel = new JPanel(new FlowLayout());
		JLabel label1 = new JLabel("손예은님의 예약 내역은 총 3건 입니다.");
		reserveLabel.setBackground(Color.WHITE);
		reserveLabel.add(label1);
		reserveLabel.setBorder(new LineBorder(Color.BLACK));
		
		reserveTables.add(reserveLabel);
		reserveTables.add(reserveTable);
		reserveTables.setBorder(new TitledBorder(new LineBorder(Color.BLACK,2),"예약 정보"));
		reserveDetail.setBorder(new TitledBorder(new LineBorder(Color.BLACK,2),"상세 정보"));
		
		//JScrollPane scrollTables = new JScrollPane(reserveTables,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scrollDetail =new JScrollPane(reserveDetail,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		reserveInfo.add(reserveTables);
		reserveInfo.add(scrollDetail);
		
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
	
	class MyMouseListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2 ) { // 더블클ㄹ릭
				int row = reserveTable.getSelectedRow();
				
				if(row == 0) {
					detailText.setText("1번째 예약");
				} else if(row == 1) {
					detailText.setText("2번째 예약");
				} else if(row == 2) {
					detailText.setText("3번째 예약");
				}
			}
		}
	}
}

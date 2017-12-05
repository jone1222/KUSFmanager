package mainInterface;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class RoomItemCustomRenderer extends JLabel implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(value instanceof JPanel) {
			Component component = (Component)value;
			component.setForeground(Color.white);
			component.setBackground(isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
			return component;
		}
		
		return new JLabel("???");
	}
	
}

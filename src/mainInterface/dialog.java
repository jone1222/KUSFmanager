package mainInterface;

import java.awt.*;
import java.awt.event.*;

public class dialog {
	public dialog(String str, String str2, String str3) {
		Frame f = new Frame();
		f.setSize(300, 200);

		// parent Frame�� f�� �ϰ�, modal�� true�� �ؼ� �ʼ����� Dialog�� ��.
		final Dialog info = new Dialog(f, str, true);
		info.setSize(140, 90);
		info.setLocation(50, 50);
		info.setLayout(new FlowLayout());

		Label msg = new Label(str2, Label.CENTER);
		Button ok = new Button(str3);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
			}
		});
		info.add(msg);
		info.add(ok);
		f.setVisible(true);
		info.setVisible(true);
	}
}
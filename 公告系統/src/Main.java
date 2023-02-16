
/*姓名:黃昭穎 學號:107403512 系級:資管2A*/
//帳號: JavaMaster 密碼: ilovejava
import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;
//判斷是不是編輯者，從而建立主畫面
public class Main extends JFrame {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int result = JOptionPane.showConfirmDialog(null, "是否為發布者？", "登入", JOptionPane.YES_NO_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			String password = getPassword();
			if (password.equals("JavaMaster ilovejava")) {
				Annoucement frame = new Annoucement(Annoucement.USER_EDITOR);
			} else {
				JOptionPane.showMessageDialog(null, "帳號或密碼錯誤，請自己重新按Ctrl+F11");//助教辛苦了
			}
		} else if (result == JOptionPane.NO_OPTION) {
			Annoucement frame = new Annoucement(Annoucement.USER_VIEWER);
		}
	}
//帳號密碼，在最上面
	public static String getPassword() {
		JPasswordField jpf = new JPasswordField(null);
		JPasswordField jpf2 = new JPasswordField(null);
		JLabel accountJLabel = new JLabel("帳號: ");
		JLabel passwordJLabel = new JLabel("密碼: ");
		jpf.setEchoChar((char) 0);
		Box box = Box.createVerticalBox();
		box.add(accountJLabel);
		box.add(jpf);
		box.add(passwordJLabel);
		box.add(jpf2);
		int x = JOptionPane.showConfirmDialog(null, box, "Account", JOptionPane.OK_CANCEL_OPTION);

		if (x == JOptionPane.OK_OPTION) {
			return jpf.getText() + " " + jpf2.getText();
		}
		return null;
	}
}

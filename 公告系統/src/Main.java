
/*�m�W:���L�o �Ǹ�:107403512 �t��:���2A*/
//�b��: JavaMaster �K�X: ilovejava
import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;
//�P�_�O���O�s��̡A�q�ӫإߥD�e��
public class Main extends JFrame {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int result = JOptionPane.showConfirmDialog(null, "�O�_���o���̡H", "�n�J", JOptionPane.YES_NO_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			String password = getPassword();
			if (password.equals("JavaMaster ilovejava")) {
				Annoucement frame = new Annoucement(Annoucement.USER_EDITOR);
			} else {
				JOptionPane.showMessageDialog(null, "�b���αK�X���~�A�Цۤv���s��Ctrl+F11");//�U�Ш��W�F
			}
		} else if (result == JOptionPane.NO_OPTION) {
			Annoucement frame = new Annoucement(Annoucement.USER_VIEWER);
		}
	}
//�b���K�X�A�b�̤W��
	public static String getPassword() {
		JPasswordField jpf = new JPasswordField(null);
		JPasswordField jpf2 = new JPasswordField(null);
		JLabel accountJLabel = new JLabel("�b��: ");
		JLabel passwordJLabel = new JLabel("�K�X: ");
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

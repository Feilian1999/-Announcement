import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

public class Annoucement extends JFrame implements ActionListener {
	private JButton editButton;//�s��
	private JButton articleButton;//���s�K��
	private JButton likeButton;//�R��
	private final int likeWidth = 32;//�R�ߪ����e
	private JTextPane announceTextPane;//�������e��
	private JLabel title;//���i���D
	private boolean booleanChange = false;//�ΨӤ���heart and dieheart

	public static final int USER_EDITOR = 0;//�i�s��Ҧ�
	public static final int USER_VIEWER = 1;//���[�̼Ҧ�
	private ImageIcon heart;
	private ImageIcon dieHeart;//���ICON
	private ImageIcon titlebackgroundIcon;//���D���I��

	private PostSerializable currentPost;//�ǦC�ƪ��D�����@

//�N��ӵe���]�m�n�A�åB�A����ɶ}��
	public Annoucement(int userType) {
		super("�����j�p��");
		
		titlebackgroundIcon = new ImageIcon(getClass().getResource("images.jpg"));
		ImagePanel topPanel = new ImagePanel(new IconToImage(titlebackgroundIcon).getImage());

		JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Box bh = Box.createHorizontalBox();
		Date date = new Date();

		heart = new ImageIcon(getClass().getResource("like.png"));
		dieHeart = new ImageIcon(getClass().getResource("unlike.png"));
		String time = date.toString();
		Font font = new Font("�L�n������",0,16);
		
		announceTextPane = new JTextPane();
		announceTextPane.setEditable(false);
		announceTextPane.setFont(font);
		title = new JLabel("<html><body><font size=\"7\">��{���i<br/><font size=\"4\">" + time + "<body><html>");
		editButton = new JButton("�s��");
		articleButton = new JButton("���s�K��");
		likeButton = new JButton();
		likeButton.setBounds(0, 0, 32, 32);
		likeButton.setIcon(resizeIcon(dieHeart, likeButton.getWidth(), likeButton.getHeight()));
		likeButton.setFocusPainted(false);
		likeButton.setBorderPainted(false);
		likeButton.setBackground(Color.orange);

		likeButton.addActionListener(this);
		editButton.addActionListener(this);
		articleButton.addActionListener(this);
		setInvisible(userType);

		topPanel.setBounds(0, 0, 300, 150);
		topPanel.add(title);
		middlePanel.add(announceTextPane);
		middlePanel.setBackground(Color.white);
		bh.add(likeButton);
		bh.add(Box.createHorizontalGlue());
		bh.add(editButton);
		bh.add(Box.createRigidArea(new Dimension(10, 0)));
		bh.add(articleButton);

		add(topPanel, BorderLayout.NORTH);
		add(bh, BorderLayout.SOUTH);
		add(middlePanel);

		initialize();
	}
//�}�ҵe������k
	public void initialize() {
		readSequentialProcess();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
//�U�ӫ��s���\��
	public void actionPerformed(ActionEvent e) {
		heart = new ImageIcon(getClass().getResource("like.png"));
		dieHeart = new ImageIcon(getClass().getResource("unlike.png"));
		if (e.getSource() == likeButton) {
			likeButton.setIcon(resizeIcon(booleanChange ? dieHeart : heart, likeWidth, likeWidth));
			booleanChange = !booleanChange;
			createSequentialProcess();
		} else if (e.getSource() == editButton) {
			this.dispose();
			new Editor();
		} else if (e.getSource() == articleButton) {
			this.dispose();
			Editor editor = new Editor();
			editor.forTextArea.setText("");
		}
	}
//�]�wicon���j�p
	private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}
//�ϥΪ̸�s��̤��������P
	private void setInvisible(int userType) {
		if (userType == USER_EDITOR) {
			likeButton.setEnabled(false);
		} else if (userType == USER_VIEWER) {
			editButton.setVisible(false);
			articleButton.setVisible(false);
		}
	}
//Ū��
	private void readSequentialProcess() {
		ReadSequentialFile.openFile();
		ReadSequentialFile.readRecords();
		ReadSequentialFile.closeFile();
		currentPost = ReadSequentialFile.getPost();
		announceTextPane.setText(currentPost.getContent());
		title.setText("<html><body><font size=\"7\">��{���i<br/><font size=\"4\">" + currentPost.getEditTime()
				+ "<body><html>");
		likeButton.setIcon(resizeIcon(currentPost.getIsLike() ? heart : dieHeart, likeWidth, likeWidth));
		booleanChange = currentPost.getIsLike();
	}
//�g�ɡA�R�ߪ�����
	private void createSequentialProcess() {
		if (currentPost != null)
			currentPost.setIsLike(booleanChange);
		CreateSequentialFile.openFile();
		CreateSequentialFile.addRecords(currentPost);
		CreateSequentialFile.closeFile();
	}
}

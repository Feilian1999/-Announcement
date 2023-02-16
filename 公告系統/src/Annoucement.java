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
	private JButton editButton;//編輯
	private JButton articleButton;//全新貼文
	private JButton likeButton;//愛心
	private final int likeWidth = 32;//愛心的長寬
	private JTextPane announceTextPane;//中間的畫面
	private JLabel title;//公告標題
	private boolean booleanChange = false;//用來切換heart and dieheart

	public static final int USER_EDITOR = 0;//可編輯模式
	public static final int USER_VIEWER = 1;//旁觀者模式
	private ImageIcon heart;
	private ImageIcon dieHeart;//兩個ICON
	private ImageIcon titlebackgroundIcon;//標題的背景

	private PostSerializable currentPost;//序列化的主角之一

//將整個畫面設置好，並且再執行時開啟
	public Annoucement(int userType) {
		super("中央大小事");
		
		titlebackgroundIcon = new ImageIcon(getClass().getResource("images.jpg"));
		ImagePanel topPanel = new ImagePanel(new IconToImage(titlebackgroundIcon).getImage());

		JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Box bh = Box.createHorizontalBox();
		Date date = new Date();

		heart = new ImageIcon(getClass().getResource("like.png"));
		dieHeart = new ImageIcon(getClass().getResource("unlike.png"));
		String time = date.toString();
		Font font = new Font("微軟正黑體",0,16);
		
		announceTextPane = new JTextPane();
		announceTextPane.setEditable(false);
		announceTextPane.setFont(font);
		title = new JLabel("<html><body><font size=\"7\">放閃公告<br/><font size=\"4\">" + time + "<body><html>");
		editButton = new JButton("編輯");
		articleButton = new JButton("全新貼文");
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
//開啟畫面的方法
	public void initialize() {
		readSequentialProcess();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
//各個按鈕的功能
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
//設定icon的大小
	private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}
//使用者跟編輯者介面的不同
	private void setInvisible(int userType) {
		if (userType == USER_EDITOR) {
			likeButton.setEnabled(false);
		} else if (userType == USER_VIEWER) {
			editButton.setVisible(false);
			articleButton.setVisible(false);
		}
	}
//讀檔
	private void readSequentialProcess() {
		ReadSequentialFile.openFile();
		ReadSequentialFile.readRecords();
		ReadSequentialFile.closeFile();
		currentPost = ReadSequentialFile.getPost();
		announceTextPane.setText(currentPost.getContent());
		title.setText("<html><body><font size=\"7\">放閃公告<br/><font size=\"4\">" + currentPost.getEditTime()
				+ "<body><html>");
		likeButton.setIcon(resizeIcon(currentPost.getIsLike() ? heart : dieHeart, likeWidth, likeWidth));
		booleanChange = currentPost.getIsLike();
	}
//寫檔，愛心的部分
	private void createSequentialProcess() {
		if (currentPost != null)
			currentPost.setIsLike(booleanChange);
		CreateSequentialFile.openFile();
		CreateSequentialFile.addRecords(currentPost);
		CreateSequentialFile.closeFile();
	}
}

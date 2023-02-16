import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Editor extends JFrame implements ActionListener {
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton importButton;
	private JButton cancelButton;
	private JLabel titleLabel;
	public JTextArea forTextArea;
	private boolean change = false;
	private ImageIcon titlebackgroundIcon;
	
	private PostSerializable currentPost;
//按下編輯and全新貼文的畫面，唯一的差別是全新貼文會把textarea清除
	public Editor() {
		super("中央大小事");
		
		titlebackgroundIcon = new ImageIcon(getClass().getResource("images.jpg"));
		ImagePanel topPanel = new ImagePanel(new IconToImage(titlebackgroundIcon).getImage());
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Box bh = Box.createHorizontalBox();
		Date date = new Date();
		Font font = new Font("微軟正黑體",0,16);
		String time = date.toString();

		forTextArea = new JTextArea("請在此編輯");
		forTextArea.setFont(font);
		titleLabel = new JLabel(
				"<html><body><p align=\"east\"><font size=\"7\">放閃公告<br/><font size=\"4\">" + time + "</body></html>");

		saveAsButton = new JButton("另存內容");
		saveButton = new JButton("儲存");
		importButton = new JButton("匯入");
		cancelButton = new JButton("取消");

		topPanel.add(titleLabel);
		topPanel.setBackground(Color.GREEN);
		textPanel.add(forTextArea);
		textPanel.setBackground(Color.white);
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAsButton);
		buttonPanel.add(importButton);
		buttonPanel.add(cancelButton);

		add(topPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		add(textPanel);

		cancelButton.addActionListener(this);
		saveAsButton.addActionListener(this);
		saveButton.addActionListener(this);
		importButton.addActionListener(this);

		initialize();
	}
//建立畫面的方法
	public void initialize() {
		readSequentialProcess();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
//讀檔
	private void readSequentialProcess() {
		ReadSequentialFile.openFile();
		ReadSequentialFile.readRecords();
		ReadSequentialFile.closeFile();
		currentPost = ReadSequentialFile.getPost();
		forTextArea.setText(currentPost.getContent());
		titleLabel.setText("<html><body><font size=\"7\">放閃公告<br/><font size=\"4\">" + currentPost.getEditTime()
				+ "<body><html>");
	}
//寫檔
	private void createSequentialProcess() {
		CreateSequentialFile.openFile();
		CreateSequentialFile.addRecords(currentPost);
		CreateSequentialFile.closeFile();
	}
//另存
	private void saveAsTextPost() {
		PostSerializable tempPost = new PostSerializable(forTextArea.getText(), currentPost.getIsLike(), new Date());
		try {
			CreateTextFile.openFile(chooseFile(JFileChooser.SAVE_DIALOG));
			CreateTextFile.addRecords(tempPost);
			CreateTextFile.closeFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
//匯入
	private void importTextPost() {
		try {
			ReadTextFile.openFile(chooseFile(JFileChooser.OPEN_DIALOG));
			ReadTextFile.readRecords();
			ReadTextFile.closeFile();
			currentPost = ReadTextFile.getPost();
			forTextArea.setText(currentPost.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//選擇檔案功能
	private String chooseFile(int mode) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		int result = (mode == JFileChooser.OPEN_DIALOG) ? fileChooser.showOpenDialog(this)
				: fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().getAbsolutePath();
			if (!fileName.endsWith(".txt"))
				fileName += ".txt";
			System.out.println("Absolute Path: " + fileName);
			return fileName;
		} else {
			System.out.println("No file choosen!");
			return null;
		}
	}

//按鈕當然要做事阿
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cancelButton) {
			this.dispose();
			Annoucement annoucement = new Annoucement(Annoucement.USER_EDITOR);
		} else if (e.getSource() == saveButton) {
			currentPost.setEditTime(new Date());
			currentPost.setContent(forTextArea.getText());
			createSequentialProcess();
			dispose();
			new Annoucement(Annoucement.USER_EDITOR);
		} else if (e.getSource() == saveAsButton) {
			saveAsTextPost();
		}
		else if(e.getSource() == importButton) {
			importTextPost();
		}
	}
}

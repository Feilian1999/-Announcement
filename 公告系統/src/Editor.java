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
//���U�s��and���s�K�媺�e���A�ߤ@���t�O�O���s�K��|��textarea�M��
	public Editor() {
		super("�����j�p��");
		
		titlebackgroundIcon = new ImageIcon(getClass().getResource("images.jpg"));
		ImagePanel topPanel = new ImagePanel(new IconToImage(titlebackgroundIcon).getImage());
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Box bh = Box.createHorizontalBox();
		Date date = new Date();
		Font font = new Font("�L�n������",0,16);
		String time = date.toString();

		forTextArea = new JTextArea("�Цb���s��");
		forTextArea.setFont(font);
		titleLabel = new JLabel(
				"<html><body><p align=\"east\"><font size=\"7\">��{���i<br/><font size=\"4\">" + time + "</body></html>");

		saveAsButton = new JButton("�t�s���e");
		saveButton = new JButton("�x�s");
		importButton = new JButton("�פJ");
		cancelButton = new JButton("����");

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
//�إߵe������k
	public void initialize() {
		readSequentialProcess();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
//Ū��
	private void readSequentialProcess() {
		ReadSequentialFile.openFile();
		ReadSequentialFile.readRecords();
		ReadSequentialFile.closeFile();
		currentPost = ReadSequentialFile.getPost();
		forTextArea.setText(currentPost.getContent());
		titleLabel.setText("<html><body><font size=\"7\">��{���i<br/><font size=\"4\">" + currentPost.getEditTime()
				+ "<body><html>");
	}
//�g��
	private void createSequentialProcess() {
		CreateSequentialFile.openFile();
		CreateSequentialFile.addRecords(currentPost);
		CreateSequentialFile.closeFile();
	}
//�t�s
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
//�פJ
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
//����ɮץ\��
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

//���s��M�n���ƪ�
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

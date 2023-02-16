import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
//配合icontoimage使用，用來設置圖片的，誰叫JFrame不能設圖(可能是我見識太淺)
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image = null;
	private int iWidth2;
	private int iHeight2;

	public ImagePanel(Image image) {
		this.image = image;
		this.iWidth2 = image.getWidth(this) / 2;
		this.iHeight2 = image.getHeight(this) / 2;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			int x = this.getParent().getWidth() / 2 - iWidth2;
			int y = this.getParent().getHeight() / 2 - iHeight2;
			g.drawImage(image, x, y, this);
		}
	}
}
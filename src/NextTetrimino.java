import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class NextTetrimino extends JPanel{

	public static final int WIDTH = 144;
	public static final int HEIGHT = 478;

	private Tetrimino nextTetrimino;
	private Image tetriminoImage;

	public NextTetrimino() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void paintComponent(Graphics gra) {
		gra.setColor(Color.BLACK);
		gra.fillRect(0, 0, WIDTH, HEIGHT);

		if(nextTetrimino != null) {
			nextTetrimino.drawNext(gra, tetriminoImage);
		}
	}

	public void setNext(Tetrimino tetrimino, Image image) {
		nextTetrimino = tetrimino;
		tetriminoImage = image;
		repaint();
	}

}

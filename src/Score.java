import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

// スコアの表示
public class Score extends JPanel{

	public static final int WIDTH = 144;
	public static final int HEIGHT = 48;

	private int score;

	public Score() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		// スコア
		graphics.setColor(Color.orange);
		DecimalFormat decimalFormat = new DecimalFormat("0000000");
		// x = 24, y = 48の場所に描画
		graphics.drawString("現在のスコア", 12, 28);
		graphics.drawString(decimalFormat.format(score), 12, 48);
	}

	// setter
	public void setScore(int score) {
		this.score = score;
		repaint();
	}

	// getter
	public int getScore() {
		return score;
	}

	// スコア計算
	public void addScore(int addScore) {
		score += addScore;
		repaint();
	}

}

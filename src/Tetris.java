import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JFrame{

	public Tetris() {
		setTitle("DarkSouls Tetris");
		setResizable(false);

		Container contentPane = getContentPane();

		// テトリス外の右側の表示
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		// スコア表示
		Score score = new Score();

		// 次のミノ
		NextTetrimino nextTetrimino = new NextTetrimino();

		// 右側はこれでおｋ
		rightPanel.add(score, BorderLayout.NORTH);
		rightPanel.add(nextTetrimino, BorderLayout.CENTER);

		// 左側（メインのテトリスの方）
		Panel panel = new Panel(score, nextTetrimino);

		// 引っ付けてこれでおｋ
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(rightPanel, BorderLayout.EAST);

		// 自動で調整
		pack();
	}

	public static void main(String[] args) {
		// メインよりコンストラクタを起動して、全ての初期化を行う
		Tetris tetris = new Tetris();
		// 画面の基本設定
		tetris.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tetris.setVisible(true);
	}

}

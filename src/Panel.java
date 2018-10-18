import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener, Runnable{
	// 24 * 12, 24 * 22 のウィンドウ画面
	public static final int WIDTH = 288;
	public static final int HEIGHT = 526;

	// スコア計算
	private static final int ONE_LINE = 100;
	private static final int TWO_LINE = 300;
	private static final int THREE_LINE = 800;
	private static final int MORE = 2000;

	// 落ちてくるスピード
	private static int speed = 400;

	// 初期化
	private Framework framework;
	private Tetrimino tetrimino;
	private Tetrimino nextTetrimino;
	private Image tetriminoImage;

	// ゲームループ
	private Thread gameLoop;

	private Random randomChoice;

	private Score score;

	private NextTetrimino showNextTetrimino;

	private AudioClip seCollision;
	private AudioClip seErase;
	private AudioClip seMove;
	private AudioClip backgroundBGM;

	// コンストラクタ
	public Panel(Score score, NextTetrimino showNextTetrimino) {
		// 任意の画面サイズを設定
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);

		// スコア
		this.score = score;
		this.showNextTetrimino = showNextTetrimino;

		// 画像の読み込み
		loadImage("image/DkStetrimino.png");

		// ブロックが下まで落ちた時の効果音
		seCollision = Applet.newAudioClip(getClass().getResource("seCollision.wav"));
		// ブロックを消した時の効果音
		seErase = Applet.newAudioClip(getClass().getResource("seErase.wav"));
		// ブロックを移動させたときの効果音
		seMove = Applet.newAudioClip(getClass().getResource("seMove.wav"));
		// BGMのセット
		backgroundBGM = Applet.newAudioClip(getClass().getResource("TaurusDemon.wav"));
		backgroundBGM.loop();

		randomChoice = new Random();
		// 乱数をパターンではなく完全にランダムにする
		// これが無いと一部のブロックしか降ってこなかったりする
		randomChoice.setSeed(System.currentTimeMillis());

		// インスタンス
		framework = new Framework();
		tetrimino = createTetrimino(framework);
		// 次の表示
		nextTetrimino = createTetrimino(framework);
		showNextTetrimino.setNext(nextTetrimino, tetriminoImage);
		// リスナーのセット
		addKeyListener(this);
		// ゲームのループを開始する
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	// Runnableを使ってゲームループ内の作業を書く
	public void run() {
		while(true) {
			// 下へ動かす
			// 下まで行ったかを判定する
			boolean isFixed = tetrimino.moveBlock(Tetrimino.DOWN);
			// 下まで行ったなら、表示されている次のやつをセットする
			if(isFixed) {
				tetrimino = nextTetrimino;
				// セットした後に新しく作って表示
				nextTetrimino = createTetrimino(framework);
				showNextTetrimino.setNext(nextTetrimino, tetriminoImage);
				// 効果音を鳴らす
				seCollision.play();
			}

			// ブロックが揃ったらその列を消すメソッド
			int howManyLines = framework.deleteBlock();

			if(howManyLines == 1) {
				score.addScore(ONE_LINE);
				seErase.play();
			} else if(howManyLines == 2) {
				score.addScore(TWO_LINE);
				seErase.play();
			} else if(howManyLines == 3) {
				score.addScore(THREE_LINE);
				seErase.play();
			} else if(howManyLines == 4) {
				score.addScore(MORE);
				seErase.play();
			}

			// スコアに応じて落ちるスピード（難易度）が上がる
			if(score.getScore() >= 8000) {
				speed = 100;
			} else if(score.getScore() >= 5000) {
				speed = 150;
			} else if(score.getScore() >= 3000) {
				speed = 200;
			} else if(score.getScore() >= 2000) {
				speed = 250;
			} else if(score.getScore() >= 1000) {
				speed = 300;
			} else if(score.getScore() >= 500) {
				speed = 350;
			}

			// ゲームオーバー判定
			if(framework.gameOver()) {
				// 全て初期化
				score.setScore(0);
				framework = new Framework();
				tetrimino = createTetrimino(framework);
				// セットした後に新しく作って表示
				nextTetrimino = createTetrimino(framework);
				showNextTetrimino.setNext(nextTetrimino, tetriminoImage);
				// BGMの初期化
				backgroundBGM.loop();
			}

			repaint();

			// 一定間隔でブロックを落とす
			// この引数が小さいほど落ちてくるスピードが速くなり、難易度が上がる
			try {
				Thread.sleep(speed);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		// 描画
		framework.draw(graphics, tetriminoImage);
		tetrimino.draw(graphics, tetriminoImage);
	}

	// 特定のキーが押された時のメソッド
	public void keyPressed(KeyEvent key) {
		int pressedKey = key.getKeyCode();

		// 左キー入力で左へ移動
		if(pressedKey == KeyEvent.VK_LEFT) {
			tetrimino.moveBlock(Tetrimino.LEFT);
			seMove.play();
		// 右キー入力で右へ移動
		} else if(pressedKey == KeyEvent.VK_RIGHT) {
			tetrimino.moveBlock(Tetrimino.RIGHT);
			seMove.play();
		// 下キー入力で下へ移動
		} else if(pressedKey == KeyEvent.VK_DOWN) {
			tetrimino.moveBlock(Tetrimino.DOWN);
			seMove.play();
		// スペースで回転
		} else if(pressedKey == KeyEvent.VK_UP) {
			tetrimino.turnBlock();
			seMove.play();
		}

		repaint();
	}

	// abstract
	public void keyReleased(KeyEvent key) {

	}

	// abstract
	public void keyTyped(KeyEvent key) {

	}

	// ランダムにブロックを作る
	protected Tetrimino createTetrimino(Framework framework) {
		int randomMino = randomChoice.nextInt(7);

		switch(randomMino) {

		    case Tetrimino.I:
		    	return new TemplateOfI(framework);
		    case Tetrimino.J:
		    	return new TemplateOfJ(framework);
		    case Tetrimino.L:
		    	return new TemplateOfL(framework);
		    case Tetrimino.O:
		    	return new TemplateOfO(framework);
		    case Tetrimino.S:
		    	return new TemplateOfS(framework);
		    case Tetrimino.T:
		    	return new TemplateOfT(framework);
		    case Tetrimino.Z:
		    	return new TemplateOfZ(framework);
		}
		return null;
	}
	private void loadImage(String file) {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(file));
		tetriminoImage = imageIcon.getImage();
	}
}

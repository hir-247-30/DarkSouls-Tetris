// 土台を作成するクラス
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Framework {
	// 12*26のテトリス画面を作成
	// 横12マス、縦26マス
	public static final int COL = 12;
	public static final int ROW = 22;

	// マスのサイズを決める変数
	public static final int SIZE = 24;

	// 初期化
	private int[][] framework;
	private int[][] frameworkImage;

	// 色の作成
	Color innerColor = new Color(20, 20, 20);

	// コンストラクタ
	public Framework() {
		framework = new int[ROW][COL];
		frameworkImage = new int[ROW] [COL];
		// 初期化
		init();
	}

	// 画面の初期化メソッド
	public void init() {
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {

				// 周りの壁を作る、壁を作る場所に1を代入
				// xの場合 … 一番最初の左端と、最後の右端に壁
				if(x == 0 || x == COL -1) {
					framework[y][x] = 1;
					frameworkImage[y][x] = Tetrimino.WALL;

				// yの場合 … 一番下に壁
				} else if (y == ROW - 1) {
					framework[y][x] = 1;
					frameworkImage[y][x] = Tetrimino.WALL;

				// 他は壁以外なので0を代入
				} else {
					framework[y][x] = 0;
				}
			}
		}
	}

	// 画面に描画するメソッド
	public void draw(Graphics graphics, Image tetriminoImage) {
		// 内部の色の設置
		graphics.setColor(innerColor);
		// fillRectにより、大きな四角形を作成
		graphics.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
		// 外枠
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				if(framework[y][x] == 1) {
					// イメージを描画する場所を選び、その後テトリミノ画像の切り取り
					graphics.drawImage(tetriminoImage, x * SIZE, y * SIZE, x
							* SIZE + SIZE, y * SIZE + SIZE,
							// ここから画像の切り取り
							// 数値で言うと (x, y = 112, 0) から (x, y = 128, 16)
							frameworkImage[y][x] * SIZE, 0, frameworkImage[y][x]
									* SIZE + SIZE, SIZE, null);
				}
			}
		}
	}

	// 接触判定
	public boolean isMovable(Point afterPoint, int[][] block) {
        for (int y = 0; y < Tetrimino.ROW; y++) {
            for (int x = 0; x < Tetrimino.COL; x++) {
            	// 判定が1（ブロック判定あり）のマスを調べる
                if (block[y][x] == 1) {
                	// 一番上以外
                    if (afterPoint.y + y < 0) {
                        // 左端または右端ならば移動不可
                        if (afterPoint.x + x <= 0 || afterPoint.x + x >= COL-1) {
                            return false;
                        }
                    // 移動先に既にブロックがある場合も移動不可
                    } else if (framework[afterPoint.y + y][afterPoint.x + x] == 1) {
                        return false;
                    }
                }
            }
        }
        // 判定でNG出なかったら移動または回転OK
        return true;
    }

	// 詰みあがったブロックを固定するメソッド
	public void fixBlock(Point position, int[][]block, int imageNumber) {
		for(int y = 0; y < Tetrimino.ROW; y++) {
			for(int x = 0; x < Tetrimino.COL; x++) {
				// ブロックの判定
				if(block[y][x] == 1) {
					// ブロックで固定
					if(position.y + y < 0) continue;
					framework[position.y + y][position.x + x] = 1;
					// 画像も固定
					frameworkImage[position.y + y][position.x + x] = imageNumber;
				}
			}
		}
	}

	public int deleteBlock() {
		// 一度に消した行の数
		int lineCounter = 0;

		for(int y = 0; y < ROW - 1; y++) {
			// 縦1列ずつ調べる
			int count = 0;
			for(int x = 1; x < COL - 1; x++) {
				// ブロックがあればカウンター足す
				if(framework[y][x] == 1) {
					count++;
				}
			}
			// 全て埋まっていた場合（枠以外が満たされていた場合）
			if(count == Framework.COL - 2) {
				lineCounter++;
				// 列を全て消す
				for(int x = 1; x < COL - 1; x++) {
					framework[y][x] = 0;
				}
				// 詰みあがっているブロックを一つ下に落とす
				for(int yy = y; yy > 0; yy--) {
					for(int xx = 1; xx < COL - 1; xx++) {
						// y軸だけ1つ下げる
						framework[yy][xx] = framework[yy - 1][xx];
						// 画像も落とす
						frameworkImage[yy][xx] = frameworkImage[yy - 1][xx];
					}
				}
			}
		}
		return lineCounter;
	}

	// ゲームオーバー判定
	// y軸を見て、一番上までブロックがあったらtrue ゲームオーバー
	public boolean gameOver() {
		for(int x = 1; x < COL - 1; x++) {
			if(framework[0][x] == 1) {
				return true;
			}
		}
		return false;
	}
}

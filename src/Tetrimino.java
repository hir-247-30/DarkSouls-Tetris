import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Tetrimino {
	// ブロック描画のサイズ
	public static final int ROW = 4;
	public static final int COL = 4;

	// 回転させるときの変数
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;

	// ミノの種類
	public static final int I = 0;
	public static final int J = 1;
	public static final int L = 2;
	public static final int O = 3;
	public static final int S = 4;
	public static final int T = 5;
	public static final int Z = 6;
	public static final int WALL = 7;

	//public static int times = 0;

	// 16
	private static final int SIZE = Framework.SIZE;

	protected int[][] block = new int[ROW][COL];

	public int imageNumber;

	private Point position;
	private Framework framework;

	// コンストラクタ
	public Tetrimino(Framework framework) {
		this.framework = framework;

		init();

		position = new Point(4, -4);
	}

	// ブロックの初期化
	public void init() {
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				block[y][x] = 0;
				// □□□□
		        // □□□□
		        // □□□□
		        // □□□□
			}
		}
	}

	// 描画
	public void draw(Graphics graphics, Image tetriminoImage) {
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				// 1の所にのみ描画してブロックを作る
				if(block[y][x] == 1){
                    // まずブロックを描画
					// その後対応させる画像を切り取る
                    graphics.drawImage(tetriminoImage, (position.x + x) * SIZE,
                            (position.y + y) * SIZE, (position.x + x) * SIZE
                                    + SIZE, (position.y + y) * SIZE+ SIZE,
                                    // ここから画像のブロックを切り取る
                                    // 例えばimageNumberが1の場合、赤色のブロックの座標のみを切り取る
                                    // imageNumber = 1 ... (x, y = 16, 0) から (x, y = 32, 16)
                                    imageNumber * SIZE, 0,
                                    imageNumber * SIZE+ SIZE, SIZE, null);
                }
			}
		}
	}

	// ブロックを左右または下に動かすメソッド
	public boolean moveBlock(int follow) {
		switch(follow) {
		    // 左へ移動
		    case LEFT :
		    	Point movedPosition = new Point(position.x - 1, position.y);
		    	if (framework.isMovable(movedPosition, block)) {
		    	    position = movedPosition;
		    	}
		    	break;
		    // 右へ移動
		    case RIGHT :
		    	movedPosition = new Point(position.x + 1, position.y);
		    	if(framework.isMovable(movedPosition, block)) {
		    	    position = movedPosition;
		    	}
		    	break;
		    // 下へ一つ移動
		    case DOWN :
		    	movedPosition = new Point(position.x, position.y + 1);
		    	if(framework.isMovable(movedPosition, block)) {
		    	    position = movedPosition;
		    	// 下まで行ったらブロックを固定
		    	} else {
		    		framework.fixBlock(position, block, imageNumber);
		    		// 固定されたらtrueを返す
		    		return true;
		    	}
		    	break;
		}
		// ブロックを動かせる内はfalse
		return false;
	}

	// ブロックの回転メソッド
	public void turnBlock() {
		int[][] turnedBlock = new int[ROW][COL];

		for(int y =0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				// これで右回転
				turnedBlock[x][ROW - 1 - y] = block[y][x];
			}
		}
		// 回転が可能なら反映させる
		if(framework.isMovable(position, turnedBlock)) {
		    block = turnedBlock;
		}
	}

	// 次のブロックを描画する
	public void drawNext(Graphics graphics, Image image) {
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				if(block[y][x] == 1) {
					graphics.drawImage(image, (x + 1) * SIZE, (y + 3) * SIZE,
                            (x + 1) * SIZE + SIZE, (y + 3) * SIZE + SIZE,
                            // ここから画像
                            imageNumber * SIZE, 0, imageNumber * SIZE + SIZE, SIZE, null);
				}
			}
		}
		graphics.setColor(Color.orange);
		graphics.drawString("NEXT", 12, 60);
	}
}

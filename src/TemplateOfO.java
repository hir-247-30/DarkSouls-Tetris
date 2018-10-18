
public class TemplateOfO extends Tetrimino{
	public TemplateOfO(Framework framework) {
		super(framework);

		imageNumber = Tetrimino.O;

		block[1][1] = 1;
		block[1][2] = 1;
		block[2][1] = 1;
		block[2][2] = 1;

		// スクエア型のテトリミノ
		// □□□□
        // □■■□
        // □■■□
        // □□□□

	}
}

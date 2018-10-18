
public class TemplateOfS extends Tetrimino{
	public TemplateOfS(Framework framework) {
		super(framework);

		imageNumber = Tetrimino.S;

		block[1][1] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		block[3][2] = 1;

		// S字型のテトリミノ
		// □□□□
        // □■□□
        // □■■□
        // □□■□

	}

}

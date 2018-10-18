
public class TemplateOfZ extends Tetrimino{
	public TemplateOfZ(Framework framework) {
		super(framework);

		imageNumber = Tetrimino.Z;

		block[1][2] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		block[3][1] = 1;

		// Z字型のテトリミノ
		// □□□□
        // □□■□
        // □■■□
        // □■□□
	}

}

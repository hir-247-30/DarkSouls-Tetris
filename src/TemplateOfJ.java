
public class TemplateOfJ extends Tetrimino{
	public TemplateOfJ(Framework framework) {
		super(framework);

		imageNumber = Tetrimino.J;

		block[1][1] = 1;
		block[1][2] = 1;
		block[2][1] = 1;
		block[3][1] = 1;

		// J字型テトリミノ
		// □□□□
        // □■■□
        // □■□□
        // □■□□
	}

}

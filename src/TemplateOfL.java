
public class TemplateOfL extends Tetrimino{
	public TemplateOfL(Framework framework) {
		super(framework);
		
		imageNumber = Tetrimino.L;

		block[1][1] = 1;
		block[1][2] = 1;
		block[2][2] = 1;
		block[3][2] = 1;

		// L字型テトリミノ
		// □□□□
        // □■■□
        // □□■□
        // □□■□
	}

}

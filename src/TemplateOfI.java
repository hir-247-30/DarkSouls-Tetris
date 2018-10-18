
public class TemplateOfI extends Tetrimino{
	public TemplateOfI(Framework framework) {
		super(framework);
		
		imageNumber = Tetrimino.I;

		block[0][1] = 1;
		block[1][1] = 1;
		block[2][1] = 1;
		block[3][1] = 1;

		// こういうテトリミノ
		// □■□□
        // □■□□
        // □■□□
        // □■□□

	}
}

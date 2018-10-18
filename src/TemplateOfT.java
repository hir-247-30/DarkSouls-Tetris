
public class TemplateOfT extends Tetrimino{
	public TemplateOfT(Framework framework){
		super(framework);

		imageNumber = Tetrimino.T;

		block[1][1] = 1;
		block[2][1] = 1;
		block[2][2] = 1;
		block[3][1] = 1;

		// T字型のテトリミノ
		// □□□□
        // □■□□
        // □■■□
        // □■□□

	}
}

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;


public class Chess extends JFrame{

	private static final long serialVersionUID = 3781719824055422555L;


	public Chess()
	{
		super();
		setBounds(100, 100, 800, 800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chess");
		setFocusable(false);
	}
	
	public void newGame()
	{
		ChessBoard board = new ChessBoard(this);
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new GridLayout(8, 8, 1, 1));
		for(int i = 1; i<=8; i++)
		{
			for(int j = 1; j<=8; j++)
			{
				add(board.getPanel(i, j));
			}
		}
		setJMenuBar(new ChessMenu(this));
		setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		Chess chess = new Chess();
		chess.newGame();
	}
}

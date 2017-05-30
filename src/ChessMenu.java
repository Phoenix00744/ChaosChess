import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class ChessMenu extends JMenuBar
{

	private static final long serialVersionUID = 8267754800349901010L;
	private Chess chessGame;
	
	public ChessMenu(Chess game)
	{
		super();
		chessGame = game;
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new NewGameListener());
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitListener());
		file.add(newGame);
		file.add(exit);
		this.add(file);
	}
	
	private class NewGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			chessGame.newGame();
		}
	}
	
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
		
}

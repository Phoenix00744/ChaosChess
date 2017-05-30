import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public abstract class ChessPiece {

	private Color color;
	private Position pos;
	private ChessSquarePanel panel;
	private ChessBoard board;
	
	public ChessPiece(Color c, Position p, ChessBoard b)
	{
		color = c;
		pos = p;
		board = b;
		panel = board.getPanel(p.getRow(), p.getCol());
	}
	
	public abstract boolean canMakeMoveTo(Position p);
	public boolean canMoveTo(Position p)
	{
		ChessSquarePanel pan = board.getPanel(p.getRow(), p.getCol());
		ChessPiece pieceTaken = null;
		Position beforePos = pos;
		ChessSquarePanel beforePanel = board.getPanel(beforePos.getRow(), beforePos.getCol());
		
		if(pan.getChessPiece() != null)
		{
			pieceTaken = pan.getChessPiece();
			pan.getChessPiece().removeSelf();
		}
		
		panel.setChessPiece(null);
		pos = p;
		panel = pan;
		panel.setChessPiece(this);
		
		boolean result;
		board.setCheckingPieces();
		if(board.getCheckingPieces().size() !=  0)//Illegal Move!
		{
			result = false;
		}
		else
			result = true;
		
		pos = beforePos;
		panel = beforePanel;
		beforePanel.setChessPiece(this);
		if(pieceTaken != null)
			pieceTaken.addSelf(board, p);
		pan.setChessPiece(pieceTaken);
		return result;
	}
	
	public void moveTo(Position p)
	{
		if(this instanceof Rook)
			((Rook)this).setCanCastle(false);
		else if(this instanceof King)
			((King)this).setCanCastle(false);
		
		if(this instanceof King && Math.abs(p.getCol() - this.getPos().getCol()) == 2)
		{
			if(p.getCol() - this.getPos().getCol() == 2)
			{
				board.getPanel(pos.getRow(), 8).getChessPiece().removeSelf();
				Rook rook = new Rook(getColor(), new Position(pos.getRow(), p.getCol() - 1), board);
				rook.addSelf(board, new Position(pos.getRow(), p.getCol() - 1));
				rook.setCanCastle(false);
			}
			else
			{
				board.getPanel(pos.getRow(), 1).getChessPiece().removeSelf();
				Rook rook = new Rook(getColor(), new Position(pos.getRow(), p.getCol() + 1), board);
				rook.addSelf(board, new Position(pos.getRow(), p.getCol() + 1));
				rook.setCanCastle(false);
			}
		}
		
		ChessSquarePanel pan = board.getPanel(p.getRow(), p.getCol());
		
		if(pan.getChessPiece() != null)
		{
			pan.getChessPiece().removeSelf();
		}
		
		panel.setChessPiece(null);
		pos = p;
		panel = pan;
		panel.setChessPiece(this);
		
		if(this instanceof Pawn && p.getRow() == 1)
		{
			transformPawn(Color.WHITE);
			return;
		}
		else if(this instanceof Pawn && p.getRow() == 8)
		{
			transformPawn(Color.BLACK);
			return;
		}
		
		if(board.getColorTurn() == Color.WHITE)
			board.setColorTurn(Color.BLACK);
		else
			board.setColorTurn(Color.WHITE);
		
		board.setCheckingPieces();
		
		panel.repaint();
		
		if(board.getColorTurn() == Color.WHITE && board.blackHasWon())
			board.gameOver("Congratulations!  Black Wins!  ");
		else if(board.getColorTurn() == Color.BLACK && board.whiteHasWon())
			board.gameOver("Congratulations!  White Wins!  ");
		else if(board.staleMate())
			board.gameOver("The game has ended in a stalemate!  ");
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Position getPos()
	{
		return pos;
	}
	
	public ChessSquarePanel getPanel()
	{
		return panel;
	}
	
	public ChessBoard getBoard()
	{
		return board;
	}
	
	public void addSelf(ChessBoard b, Position p)
	{
		b.getPanel(p.getRow(), p.getCol()).setChessPiece(this);
		panel = b.getPanel(p.getRow(), p.getCol());
		pos = p;
		board = b;
		panel.repaint();
	}
	public void removeSelf()
	{
		panel.setChessPiece(null);
		panel.repaint();
		panel = null;
		pos = null;
		board = null;
	}
	
	public ArrayList<Position> canMoveToPositions()
	{
		ArrayList<Position> canMoveToPositions = new ArrayList<Position>();
		for(int i = 1; i < 9; i++)
		{
			for(int j = 1; j < 9; j++)
			{
				if(canMoveTo(new Position(i, j)))
					canMoveToPositions.add(new Position(i, j));
					
			}
		}
		
		return canMoveToPositions;
	}
	
	private void transformPawn(Color color)
	{
		JFrame transformingWindow = new JFrame("Promote your pawn to a...");
		transformingWindow.setBounds(100, 100, 800, 800);
		transformingWindow.setResizable(true);
		transformingWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container c = transformingWindow.getContentPane(); 
	    GridLayout layout = new GridLayout(1, 4);
	    c.setLayout(layout);
	    JButton queenButton = new JButton();
	    JButton knightButton = new JButton();
	    JButton rookButton = new JButton();
	    JButton bishopButton = new JButton();
	    
	    if(color == Color.WHITE)
	    {
	    	queenButton.setIcon(new ImageIcon("WhiteQueen.png"));
	    	knightButton.setIcon(new ImageIcon("WhiteKnight.png"));
	    	rookButton.setIcon(new ImageIcon("WhiteRook.png"));
	    	bishopButton.setIcon(new ImageIcon("WhiteBishop.png"));
	    }
	    else
	    {
	    	queenButton.setIcon(new ImageIcon("BlackQueen.png"));
	    	knightButton.setIcon(new ImageIcon("BlackKnight.png"));
	    	rookButton.setIcon(new ImageIcon("BlackRook.png"));
	    	bishopButton.setIcon(new ImageIcon("BlackBishop.png"));
	    }
	    
	    queenButton.addActionListener(new QueenButtonListener(this, transformingWindow));
	    knightButton.addActionListener(new KnightButtonListener(this,transformingWindow));
	    rookButton.addActionListener(new RookButtonListener(this,transformingWindow));
	    bishopButton.addActionListener(new BishopButtonListener(this, transformingWindow));
	    c.add(queenButton);
	    c.add(knightButton);
	    c.add(rookButton);
	    c.add(bishopButton);
	    
		transformingWindow.setVisible(true);
	    transformingWindow.setAlwaysOnTop(true);
	}
	
	private class QueenButtonListener implements ActionListener
	{
		private ChessPiece transformingPawn;
		private JFrame displayedWindow;
		
		public QueenButtonListener(ChessPiece transPawn, JFrame window)
		{
			transformingPawn = transPawn;
			displayedWindow = window;
		}
		public void actionPerformed(ActionEvent e) 
		{
			Queen newQueen = new Queen(transformingPawn.getColor(), new Position(4, 4), board);
			newQueen.moveTo(transformingPawn.getPos());
			displayedWindow.dispose();
		}
		
	}
	
	
	
	private class KnightButtonListener implements ActionListener
	{
		private ChessPiece transformingPawn;
		private JFrame displayedWindow;

		public KnightButtonListener(ChessPiece transPawn, JFrame window)
		{
			transformingPawn = transPawn;
			displayedWindow = window;
		}
		public void actionPerformed(ActionEvent e) 
		{
			Knight newKnight = new Knight(transformingPawn.getColor(), new Position(4, 4), board);
			newKnight.moveTo(transformingPawn.getPos());
			displayedWindow.dispose();

		}
		
	}
	
	private class RookButtonListener implements ActionListener
	{
		private ChessPiece transformingPawn;
		private JFrame displayedWindow;

		public RookButtonListener(ChessPiece transPawn, JFrame window)
		{
			transformingPawn = transPawn;
			displayedWindow = window;

		}
		public void actionPerformed(ActionEvent e) 
		{
			Rook newRook = new Rook(transformingPawn.getColor(), new Position(4, 4), board);
			newRook.moveTo(transformingPawn.getPos());
			displayedWindow.dispose();

		}
		
	}
	
	private class BishopButtonListener implements ActionListener
	{
		private ChessPiece transformingPawn;
		private JFrame displayedWindow;

		public BishopButtonListener(ChessPiece transPawn, JFrame window)
		{
			transformingPawn = transPawn;
			displayedWindow = window;

		}
		public void actionPerformed(ActionEvent e) 
		{
			Bishop newBishop = new Bishop(transformingPawn.getColor(), new Position(4, 4), board);
			newBishop.moveTo(transformingPawn.getPos());
			displayedWindow.dispose();
		}
		
	}
}
